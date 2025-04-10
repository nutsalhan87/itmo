#!/usr/bin/env bash

cd "$(dirname "$0")"

keytool -genkeypair -alias localhost -keyalg RSA -keysize 2048 -validity 365 -keystore keystore.jks -dname "CN=localhost" -keypass secret -storepass secret
keytool -importkeystore -srckeystore keystore.jks -destkeystore keystore.p12 -srcalias localhost \
    -srcstoretype jks -deststoretype pkcs12 -srcstorepass secret -deststorepass secret -srckeypass secret -destkeypass secret
openssl pkcs12 -in keystore.p12 -nocerts -nodes -out cert.pem.key -passin pass:secret
openssl pkcs12 -in keystore.p12 -clcerts -nokeys -out cert.pem -passin pass:secret
