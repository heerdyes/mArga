#!/usr/bin/env bash
set -euo pipefail
IFS=$'\r\n'

rm -rf mods/com.fractalautomatawaveband.marga.edg.ca
javac -d mods/com.fractalautomatawaveband.marga.edg.ca \
  src/com.fractalautomatawaveband.marga.edg.ca/module-info.java \
  src/com.fractalautomatawaveband.marga.edg.ca/com/fractalautomatawaveband/marga/edg/ca/*.java
