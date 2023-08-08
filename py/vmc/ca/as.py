#!/usr/bin/env python3
from sys import argv
from inst import *

symtab = {}
dbgf = False

def eattillspace(s):
  sp=0
  for c in s:
    if c == ' ':
      break
    sp += 1
  return s[:sp], '' if sp==len(s) else s[sp+1:]

def prochdr(hdln):
  cmd, sz = hdln.split()
  if cmd != 'mem':
    raise Exception('illegal header directive: '+cmd)
  memsz = int(sz)
  il = [0] * memsz
  return il

def assembly(srclns):
  global symtab
  il = prochdr(srclns[0])
  ptr = 0
  for sl in srclns[1:]:
    car, cdr = eattillspace(sl)
    if car == 'push':
      il[ptr] = IPUSH
      il[ptr + 1] = int(cdr)
      ptr += 2 - 1
    elif car == 'add':
      il[ptr] = IADD
    elif car == 'inc':
      il[ptr] = IINC
    elif car == 'dec':
      il[ptr] = IDEC
    elif car == 'print':
      il[ptr] = IPRINT
    elif car == 'tdump':
      il[ptr] = ITAPEDUMP
    elif car == 'sdump':
      il[ptr] = ISTAKDUMP
    elif car == 'put':
      il[ptr] = IPUT
    elif car == 'get':
      il[ptr] = IGET
    elif car == 'halt':
      il[ptr] = HALT
    elif car == 'mul':
      il[ptr] = IMUL
    elif car == 'mod':
      il[ptr] = IMOD
    elif car == 'quo':
      il[ptr] = IQUO
    elif car == 'eq':
      il[ptr] = IEQ
    elif car == 'jnz':
      if cdr not in symtab:
        raise Exception('no such label: '+cdr)
      loc = symtab[cdr]
      il[ptr] = IPUSH
      il[ptr + 1] = loc
      il[ptr + 2] = JNZ
      ptr += 3 - 1
    elif car == 'jmp':
      if cdr not in symtab:
        raise Exception('no such label: '+cdr)
      loc = symtab[cdr]
      il[ptr] = IPUSH
      il[ptr + 1] = loc
      il[ptr + 2] = JMP
      ptr += 3 - 1
    elif car == '@':
      symtab[cdr] = ptr
    elif car == 'save':
      il[ptr] = RSAVE
    elif car == 'load':
      il[ptr] = RLOAD
    elif car == 'zfoff':
      il[ptr] = ZFOFF
    elif car == 'fdump':
      il[ptr] = IFLAGSDUMP
    else:
      raise Exception('unknown command: '+car)
    ptr += 1
  return il

def savetape(T, fn):
  if dbgf:
    print('--- tape ---')
    print(T)
    print('--- symtab ---')
    print(symtab)
  s=''
  for c in T:
    s = s + chr(c)
  with open(fn, 'w') as f:
    f.write(s)

def rmcomments(lns):
  z=[]
  for ln in lns:
    if ln.startswith(';'):
      continue
    z.append(ln)
  return z

# flow
if __name__ == '__main__':
  if len(argv) not in [3, 4]:
    print('usage: python3 as.py [--debug] <source.as> <output.vmc>')
    raise SystemExit
  
  # handle cli args
  opt = ''
  if len(argv) == 3:  
    src, dst = argv[1], argv[2]
  elif len(argv) == 4:
    opt, src, dst = argv[1], argv[2], argv[3]
  dbgf = opt == '--debug'
  
  # translate to vmc native
  with open(src) as fs:
    lns = [ln.strip() for ln in fs.readlines()]
  savetape(assembly(rmcomments(lns)), dst)

