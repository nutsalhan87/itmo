#!/usr/bin/env bash

cd "$(dirname "$0")" || exit

export CONSUL_HOST=localhost
export CONSUL_PORT=28444
export HAPROXY_BIN="$(pwd)/build/usr/local/sbin/haproxy"
export HAPROXY_CONFIG="$(pwd)/haproxy.cfg"
export CERT_PATH="$(pwd)/../c/cert.pem"
export HAPROXY_PORT=28445
export HAPROXY_PORT2=28446
export MULE_HOST=0.0.0.0
export MULE_PORT=30081
export GODBLESS_HOST=0.0.0.0
export GODBLESS_PORTS="40443,50443"

python "$(pwd)"/haproxy.py
