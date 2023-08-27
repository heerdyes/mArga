import os
import u

def ls(args, pwd):
  tpath = pwd
  if len(args) != 0:
    arg1 = u.cadr(args)[0]
    tpath = pwd / arg1
  for sd in tpath.iterdir():
    print('%s %s'%('D' if sd.is_dir() else ' ', sd.name))

def cat(args, pwd):
  if len(args) == 0:
    raise Exception('cat needs atleast one argument')
  arg1 = u.cadr(args)[0]
  fpath = pwd / arg1
  with fpath.open() as f:
    for ln in f.readlines():
      print(ln, end = '')

