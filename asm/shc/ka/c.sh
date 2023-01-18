#!/usr/bin/env bash
set -euo pipefail

rm -f shc.o shc
nasm -f elf shc.asm
ld -m elf_i386 -s -o shc.out shc.o

