#!/usr/bin/env bash
set -euo pipefail
IFS=$'\n\t'

rm *.class
javac *.java
java Z
