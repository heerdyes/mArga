import os
import u

def ls(args,pwd):
  tpath=pwd
  if len(args)!=0:
    arg1=u.cadr(args)[0]
    tpath='%s%s%s'%(pwd,os.sep,arg1)
  subdirs=os.listdir(tpath)
  for sd in subdirs:
    print('%s %s'%('D' if os.path.isdir(sd) else ' ', sd))

def cat(args,pwd):
  if len(args)==0:
    raise Exception('cat needs atleast one argument')
  arg1=u.cadr(args)[0]
  fpath='%s%s%s'%(pwd,os.sep,arg1)
  with open(fpath) as f:
    for ln in f.readlines():
      print(ln,end='')

