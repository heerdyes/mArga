#!/usr/bin/env bash
set -euo pipefail

MARGA_HOME="$HOME/.mArga.d"

echo "removing mArga program ecosystem..."
rm -rvf $MARGA_HOME
rm -vf $HOME/.mArga

echo "sayonara!"
