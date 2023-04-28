#!/usr/bin/env bash
set -euo pipefail
IFS=$'\r\n'

java --module-path mods -m com.fractalautomatawaveband.marga.edg.ci/com.fractalautomatawaveband.marga.edg.ci.edg
