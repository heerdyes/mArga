#!/usr/bin/env bash
set -euo pipefail
IFS=$'\n\t'

while true
do
  echo -n "-> "
  read ch
  if [ $ch == "q" ]
  then
    echo "bye!"
    break
  elif [ $ch == "sys" ]
  then
    uname -a
  elif [ $ch == "net" ]
  then
    ip addr
  fi
done

