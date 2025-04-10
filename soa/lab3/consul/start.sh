#!/usr/bin/env bash

cd "$(dirname "$0")"

./consul agent -config-dir="$(pwd)"/config -data-dir="$(pwd)"/data -advertise "127.0.0.1" 
