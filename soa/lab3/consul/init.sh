#!/usr/bin/env bash

cd "$(dirname "$0")"
CONSUL_DIR="$(pwd)"

export PATH="$PATH:$CONSUL_DIR/"
export CONSUL_DATACENTER="dc1"
export CONSUL_DOMAIN="consul"
export CONSUL_DATA_DIR="$CONSUL_DIR/data/"
export CONSUL_CONFIG_DIR="$CONSUL_DIR/config/"
export CONSUL_RETRY_JOIN="consul-server-0"
export CONSUL_HTTP_PORT=28444
export OUTPUT_FOLDER="$CONSUL_DIR/output/"

rm -rf "${OUTPUT_FOLDER}"
rm -rf "${CONSUL_DATA_DIR}"
rm -rf "${CONSUL_CONFIG_DIR}"
mkdir -p "${OUTPUT_FOLDER}"
mkdir -p "${CONSUL_DATA_DIR}"
mkdir -p "${CONSUL_CONFIG_DIR}"

"$CONSUL_DIR"/generate_consul_server_config.sh
mv "$OUTPUT_FOLDER""$CONSUL_RETRY_JOIN"/* "$CONSUL_CONFIG_DIR"
rm -rf "$OUTPUT_FOLDER"
cp ../c/cert.pem "$CONSUL_CONFIG_DIR"consul-agent-ca.pem
cp ../c/cert.pem "$CONSUL_CONFIG_DIR"consul-agent.pem
cp ../c/cert.pem.key "$CONSUL_CONFIG_DIR"consul-agent-key.pem
