#!/usr/bin/env bash
set -euo pipefail
IFS=$'\r\n'

clear
javac -d bin src/com/fractalautomatawaveband/marga/edc/ka/*.java
