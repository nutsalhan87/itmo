#!/usr/bin/env bash

cd "$(dirname "$0")"

export MULE_HOME="$(pwd)"/mule-enterprise-standalone
export PATH="$PATH":"$MULE_HOME"/bin
export JAVA_HOME="$HOME"/.jdks/jdk17/lib/openjdk
HAPROXY_PORT2=28446
MULE_PORT=30081

cd mule
tee ./src/main/resources/local.properties > /dev/null << EOF
https.listener.host=0.0.0.0
https.listener.port=$MULE_PORT
cert.path=$(pwd)/../c/keystore.jks
soap.service.url=https://localhost:$HAPROXY_PORT2/godbless-soap-1.0/VehicleWebService
EOF
mvn install
cp target/soa-1.0.0-mule-application.jar ../mule-enterprise-standalone/apps/
cd ../

mule "$@"
