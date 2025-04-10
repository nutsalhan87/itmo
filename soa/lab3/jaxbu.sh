#!/usr/bin/env bash

cd "$(dirname "$0")"

if [[ -z "${PORT}" ]]; then
  echo "Set PORT env variable"
  exit 1
fi

export PORT=$PORT
export PG_URL=jdbc:postgresql://localhost:5432/soa
export PG_USERNAME=postgres
export PG_PASSWORD=user
export CONSUL_HOST=localhost
export CONSUL_PORT=28444

cp ./c/keystore.jks ./jaxbu/src/main/resources/
./jaxbu/gradlew -p ./jaxbu bootRun
