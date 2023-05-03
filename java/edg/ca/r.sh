#!/usr/bin/env bash
set -euo pipefail
IFS=$'\r\n'

java --module-path mods -m com.fractalautomatawaveband.marga.edg.ca/com.fractalautomatawaveband.marga.edg.ca.edg
