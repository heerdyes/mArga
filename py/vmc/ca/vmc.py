#!/usr/bin/env python3
from sys import argv
from inst import *

# stack based integer type vm

T = []
PC = 0
S = []
R = 0
ZF = False

def loadtape(fn):
  global T
  nt=[]
  with open(fn) as f:
    for d in f.read():
      nt.append(ord(d))
  T=nt
  
def iadd():
  global S, ZF
  o1 = S.pop()
  o2 = S.pop()
  res = o1 + o2
  S.append(res)
  ZF = S[-1] == 0
  
def imul():
  global S, ZF
  o1 = S.pop()
  o2 = S.pop()
  res = o1 * o2
  S.append(res)
  ZF = S[-1] == 0

def iput():
  global S, T
  loc = S.pop()
  dat = S.pop()
  T[loc] = dat

def iget():
  global S, T
  loc = S.pop()
  S.append(T[loc])

def ieq():
  global S, ZF
  o1 = S.pop()
  o2 = S.pop()
  ZF = o1 == o2

def jnz():
  global PC, ZF, S
  loc = S.pop()
  if not ZF:
    PC = loc - 1

def jmp():
  global PC, S
  loc = S.pop()
  PC = loc - 1

def cycle():
  global T, PC, S, ZF, R
  c = T[PC]
  if c == IPUSH:
    S.append(T[PC + 1])
    PC += 1
  elif c == IADD:
    iadd()
  elif c == IMUL:
    imul()
  elif c == IINC:
    S.append(S.pop() + 1)
    ZF = S[-1] == 0
  elif c == IDEC:
    S.append(S.pop() - 1)
    ZF = S[-1] == 0
  elif c == IPRINT:
    print(S.pop())
  elif c == NOOP:
    pass
  elif c == ITAPEDUMP:
    print(T)
  elif c == ISTAKDUMP:
    print(S)
  elif c == IFLAGSDUMP:
    print('ZF', ZF)
  elif c == IPUT:
    iput()
  elif c == IGET:
    iget()
  elif c == IEQ:
    ieq()
  elif c == JNZ:
    jnz()
  elif c == JMP:
    jmp()
  elif c == RSAVE:
    R = S.pop()
  elif c == RLOAD:
    S.append(R)
  elif c == ZFOFF:
    ZF = False
  elif c == HALT:
    raise SystemExit

def runmc():
  global PC, T, S
  while PC < len(T) and PC >= 0:
    cycle()
    PC += 1

# flow
if __name__ == '__main__':
  if len(argv) != 2:
    print('usage: python3 vmc.py <mc.tape>')
    raise SystemExit
    
  loadtape(argv[1])
  runmc()

