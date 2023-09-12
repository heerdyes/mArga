#!/usr/bin/env bash
set -euo pipefail

MARGA_HOME="$HOME/.mArga.d"
EDG_JAVA_NA="$MARGA_HOME/edg/java/na"
SHG_JAVA_NA="$MARGA_HOME/shg/java/na"
MARGA_GH_ROOT="../../.."
GH_JAVA_EDG_NA="$MARGA_GH_ROOT/java/edg/na"
GH_JAVA_SHG_NA="$MARGA_GH_ROOT/java/shg/na"

echo "[mArga installer script]"
if [ -d "$MARGA_HOME" ]; then
  echo "mArga seems to be already installed. exiting."
  exit
fi

echo "mArga will now be installed in $MARGA_HOME"
mkdir -v $MARGA_HOME

echo "installing edg/java/na"
mkdir -pv $EDG_JAVA_NA
pushd $GH_JAVA_EDG_NA
./c
if [ "$?" == "0" ]; then
  echo "edg/java/na compiled successfully!"
  echo "proceeding to copy classes..."
  cp -v *.class $EDG_JAVA_NA
  cp -v edg.cfg $EDG_JAVA_NA
  echo "creating .mArga dotfile for the funxions..."
  printf "edg() {\n  java -cp %s edg \$1\n}\n" $EDG_JAVA_NA > $HOME/.mArga
  echo "[action] type below command in your shell:"
  echo ". ~/.mArga"
else
  echo "edg/java/na compilation unsuccessful!"
  echo "will not install."
fi
popd

echo "installing shg/java/na"
mkdir -pv $SHG_JAVA_NA
pushd $GH_JAVA_SHG_NA
./c
if [ "$?" == "0" ]; then
  echo "shg/java/na compiled successfully!"
  echo "proceeding to copy classes..."
  cp -v *.class $SHG_JAVA_NA
  echo "creating .mArga dotfile for the funxions..."
  printf "shg() {\n  java -cp %s shg\n}\n" $SHG_JAVA_NA >> $HOME/.mArga
  echo "[action] type below command in your shell:"
  echo ". ~/.mArga"
else
  echo "shg/java/na compilation unsuccessful!"
  echo "will not install."
fi
popd
