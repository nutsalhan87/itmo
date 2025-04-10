import os
import time
import requests
import subprocess
from typing import List

class Service:
    def __init__(self, address: str, port: int):
        self.address = address
        self.port = port

    def __repr__(self):
        return f"{self.address}:{self.port}"

    def __eq__(self, other):
        if isinstance(other, Service):
            return self.address == other.address and self.port == other.port
        return False

    def __hash__(self):
        return hash((self.address, self.port))

def get_active_services(consul_host: str, consul_port: int, service_name: str) -> List[Service]:
    url = f"http://{consul_host}:{consul_port}/v1/health/service/{service_name}"
    response = requests.get(url)
    response.raise_for_status()
    services = []

    for item in response.json():
        if all(check["Status"] == "passing" for check in item.get("Checks", [])):
            service_info = item["Service"]
            services.append(Service(service_info["Address"], service_info["Port"]))

    return services

def generate_haproxy_config(services_jaxbu: List[Service], services_mule: List[Service], services_godbless: List[Service], cert_path: str, haproxy_port: int, haproxy_port2: int) -> str:
    config = f"""
global
  log stdout format raw local0

defaults
  log global
  option httplog
  option httpslog
  option dontlognull

frontend vehicles
  mode http
  bind :{haproxy_port} ssl crt {cert_path}
  use_backend mule if {{ path /api/v1/shop }} || {{ path_beg /api/v1/shop/ }}
  use_backend jaxbu if {{ path /api/v1 }} || {{ path_beg /api/v1/ }}
  default_backend not_found

frontend godbless_frontend
  mode http
  bind :{haproxy_port2} ssl crt {cert_path}
  default_backend godbless

backend jaxbu
  mode http
"""

    for i, service in enumerate(services_jaxbu, start=1):
        config += f"  server s{i} {service} ssl alpn h2,http/1.1 ca-file {cert_path}\n"

    config += "\nbackend mule\n  mode http\n"

    for i, service in enumerate(services_mule, start=1):
        config += f"  server s{i} {service} ssl alpn h2,http/1.1 ca-file {cert_path}\n"

    config += "\nbackend godbless\n  mode http\n"

    for i, service in enumerate(services_godbless, start=1):
        config += f"  server s{i} {service} ssl alpn h2,http/1.1 ca-file {cert_path}\n"

    config += "\nbackend not_found\n  mode http\n  http-request redirect location https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/404\n"

    return config

def restart_haproxy(haproxy_binary: str, haproxy_config_path: str, haproxy_process: subprocess.Popen):
    # Terminate the old HAProxy process if it exists
    if haproxy_process and haproxy_process.poll() is None:
        haproxy_process.terminate()
        haproxy_process.wait()

    # Start a new HAProxy process in the background
    try:
        haproxy_process = subprocess.Popen([haproxy_binary, "-f", haproxy_config_path, "-db"])
    except Exception as e:
        print(f"Error starting HAProxy: {e}")

    return haproxy_process

def main():
    consul_host = os.getenv("CONSUL_HOST", "localhost")
    consul_port = int(os.getenv("CONSUL_PORT", 8500))
    haproxy_binary = os.getenv("HAPROXY_BIN", "/usr/sbin/haproxy")
    haproxy_config_path = os.getenv("HAPROXY_CONFIG", "/etc/haproxy/haproxy.cfg")
    cert_path = os.getenv("CERT_PATH", "/etc/haproxy/cert.pem")
    haproxy_port = int(os.getenv("HAPROXY_PORT", 8443))
    haproxy_port2 = int(os.getenv("HAPROXY_PORT2", 8444))
    mule_host = os.getenv("MULE_HOST", "localhost")
    mule_port = int(os.getenv("MULE_PORT", 8080))

    godbless_host = os.getenv("GODBLESS_HOST", "localhost")
    godbless_ports = list(map(int, os.getenv("GODBLESS_PORTS", "8888,8889").split(",")))

    services = {
        "jaxbu": [],
        "mule": [
            Service(mule_host, mule_port)
        ],
        "godbless": [
            Service(godbless_host, port) for port in godbless_ports
        ]
    }

    haproxy_process = None

    # Initial HAProxy configuration and startup
    print("Starting HAProxy with initial configuration.")
    initial_config = generate_haproxy_config(services["jaxbu"], services["mule"], services["godbless"], cert_path, haproxy_port, haproxy_port2)
    with open(haproxy_config_path, "w") as f:
        f.write(initial_config)
    haproxy_process = restart_haproxy(haproxy_binary, haproxy_config_path, haproxy_process)

    while True:
        updated = False

        for service_name in ["jaxbu"]:  # Only check consul for "jaxbu"
            new_services = get_active_services(consul_host, consul_port, service_name)

            if set(new_services) != set(services[service_name]):
                services[service_name] = new_services
                updated = True

        if updated:
            print("Services have changed. Updating HAProxy configuration.")
            new_config = generate_haproxy_config(services["jaxbu"], services["mule"], services["godbless"], cert_path, haproxy_port, haproxy_port2)
            
            with open(haproxy_config_path, "w") as f:
                f.write(new_config)

            haproxy_process = restart_haproxy(haproxy_binary, haproxy_config_path, haproxy_process)

        time.sleep(5)

if __name__ == "__main__":
    main()
