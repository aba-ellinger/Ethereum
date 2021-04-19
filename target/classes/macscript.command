#!/bin/bash

export JAVA_HOME="/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home/"
besu --network=dev --miner-enabled --miner-coinbase=0x8b61d3ffd7ce1472cabc1e62262229eef93b2a98 --rpc-http-cors-origins="all" --host-allowlist="*" --rpc-ws-enabled --rpc-http-enabled --data-path=/tmp/tmpDatdir
