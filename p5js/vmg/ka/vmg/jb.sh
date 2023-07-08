#!/usr/bin/env bash
set -euo pipefail
IFS=$'\r\n'

js-beautify -r tape.js
js-beautify -r sketch.js
js-beautify -r index.html

