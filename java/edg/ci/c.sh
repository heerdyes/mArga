#!/usr/bin/env bash
set -euo pipefail
IFS=$'\r\n'

rm -rf mods/com.fractalautomatawaveband.marga.edg.ci
javac -d mods/com.fractalautomatawaveband.marga.edg.ci \
  src/com.fractalautomatawaveband.marga.edg.ci/module-info.java \
  src/com.fractalautomatawaveband.marga.edg.ci/com/fractalautomatawaveband/marga/edg/ci/*.java
