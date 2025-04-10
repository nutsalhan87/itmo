#!/usr/bin/env bash

cd "$(dirname "$0")" || exit

export PORT=50443
export CONSUL_HOST=localhost
export CONSUL_PORT=28444
export JAXBU_URL=https://0.0.0.0:28445/api/v1

./wildfly-34.0.1.Final2/bin/standalone.sh -Djboss.ajp.port=58009 -Djboss.http.port=50001 -Djboss.https.port=$PORT -Djboss.management.http.port=59990 -Djboss.management.https.port=59993

