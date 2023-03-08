#!/usr/bin/env python3
import sys
import os
import subprocess

def eattillspace(s):
  n=s.find(' ')
  if n==-1:
    return s,''
  return s[0:n],s[n+1:]


class Buf:
  def __init__(self):
    self.lines=[]
    self.file=None
    self.colseq='abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'
    self.currpg=0
    self.lnsinpg=30

  def cat(self):
    print(f'    {self.colseq}')
    i=0
    for ln in self.lines:
      print('%03d %s'%(i, self.lines[i]))
      i+=1

  def pagesize(self,n):
    if n<10:
      print('page with <10 lines is unreasonable!')
      return
    if n>=len(self.lines):
      print('page size out of bounds!')
      return
    self.lnsinpg=n

  def viewpage(self):
    self.cut(self.currpg*self.lnsinpg,self.lnsinpg)

  def nextpage(self):
    if self.currpg*self.lnsinpg < len(self.lines):
      self.currpg+=1

  def prevpage(self):
    if self.currpg>0:
      self.currpg-=1

  def gotopage(self,n):
    if n*self.lnsinpg < len(self.lines):
      self.currpg=n

  def cut(self,n1,dist):
    n2=n1+dist
    if n1>=n2:
      return
    if n1<0 or n1>=len(self.lines):
      return
    if n2<0:
      return
    if n2>len(self.lines):
      n2=len(self.lines)
    print(f'    {self.colseq}')
    for i in range(n1,n2):
      print('%03d %s'%(i, self.lines[i]))

  def setfile(self,f):
    if self.file:
      print(f'file reattaching from {self.file} to {f}')
    self.file=f

  def save(self):
    if not self.file:
      print('no file attached to this buffer')
      return
    try:
      with open(self.file,'w') as fo:
        for ln in self.lines:
          print(ln,file=fo)
    except Error as e:
      print(str(e))

  def load(self):
    if not self.file:
      print('no file attached to this buffer')
      return
    try:
      with open(self.file) as fo:
        self.lines=[x.rstrip() for x in fo.readlines()]
    except FileNotFoundError as e:
      print(f'alas! {str(e)}')
    except IsADirectoryError as e:
      print('i cannot edit directory files yet!')

  def append(self,s):
    self.lines.append(s)

  def clear(self):
    self.lines=[]

  def rmln(self,n):
    if n<0 or n>=len(self.lines):
      print('target line beyond range!')
      return
    self.lines=self.lines[0:n]+self.lines[n+1:]

  def clrln(self,n):
    if n<0 or n>=len(self.lines):
      print('target line beyond range!')
      return
    self.lines[n]=''

  def insln(self,n,ln):
    if n<0 or n>=len(self.lines):
      print('target line beyond range!')
      return
    self.lines=self.lines[0:n]+[ln]+self.lines[n:]

  def appendln(self,n,ln):
    if n<0 or n>=len(self.lines):
      print('target line beyond range!')
      return
    self.lines[n]+=ln

  def prefixln(self,n,ln):
    if n<0 or n>=len(self.lines):
      print('target line beyond range!')
      return
    self.lines[n]=ln+self.lines[n]

  def inschars(self,col,row,ln):
    cl=self.colseq.find(col)
    if cl>=len(self.lines[row]):
      print('column beyond range!')
      return
    self.lines[row]=self.lines[row][0:cl]+ln+self.lines[row][cl:]

  def rmchars(self,col,row,nch):
    cl=self.colseq.find(col)
    if cl>=len(self.lines[row]):
      print('column beyond range!')
      return
    if cl+nch>=len(self.lines[row]):
      print('attempting to delete beyond range!')
      return
    self.lines[row]=self.lines[row][0:cl]+self.lines[row][cl+nch:]

  def shrun(self):
    if not self.file:
      print('no file attached!')
      return
    subprocess.run(args)


class Sh:
  def __init__(self,nm):
    self.name=nm
    self.buf=Buf()

  def loadbuf(self,f):
    self.buf.setfile(f)
    self.buf.load()

  def c_f(self,cdr):
    fn,junk=eattillspace(cdr)
    if cdr=='':
      print(self.buf.file)
    else:
      self.buf.setfile(fn)

  def c_fl(self,cdr):
    if cdr=='':
      print('fl needs filename as parameter!')
    else:
      fn,junk=eattillspace(cdr)
      self.loadbuf(fn)

  def c_a(self,cdr):
    if cdr=='':
      return
    self.buf.append(cdr)
    self.buf.cat()

  def c_ls(self):
    ds=os.listdir()
    for ln in ds:
      print(f'  {ln}')

  def c_w(self):
    self.buf.save()

  def c_l(self):
    self.buf.load()

  def c_c(self,cdr):
    self.buf.cat()

  def c_x(self,cdr):
    if cdr=='':
      print('cut requires n1, dist as parameters!')
      return
    sn1,ddr=eattillspace(cdr)
    if ddr=='':
      print('cut requires n1, dist as parameters!')
      return
    sn2,junk=eattillspace(ddr)
    n1,dx=int(sn1),int(sn2)
    self.buf.cut(n1,dx)

  def c_D(self):
    self.buf.clear()

  def c_dl(self,cdr):
    sn,junk=eattillspace(cdr)
    if sn=='':
      return
    nthln=int(sn)
    self.buf.rmln(nthln)

  def c_cl(self,cdr):
    sn,junk=eattillspace(cdr)
    if sn=='':
      return
    nthln=int(sn)
    self.buf.clrln(nthln)

  def c_il(self,cdr):
    sn,ddr=eattillspace(cdr)
    if sn=='':
      print('il requires linenum as parameter!')
      return
    nthln=int(sn)
    self.buf.insln(nthln,ddr)

  def c_al(self,cdr):
    sn,ddr=eattillspace(cdr)
    if sn=='':
      print('il requires linenum as parameter!')
      return
    nthln=int(sn)
    self.buf.appendln(nthln,ddr)

  def c_pl(self,cdr):
    sn,ddr=eattillspace(cdr)
    if sn=='':
      print('il requires linenum as parameter!')
      return
    nthln=int(sn)
    self.buf.prefixln(nthln,ddr)

  def c_i(self,cdr):
    if cdr=='':
      return
    cursor,chars=eattillspace(cdr)
    col=cursor[0]
    nln=int(cursor[1:])
    self.buf.inschars(col,nln,chars)

  def c_d(self,cdr):
    if cdr=='':
      return
    cursor,snchars=eattillspace(cdr)
    col=cursor[0]
    nln=int(cursor[1:])
    nch=int(snchars)
    self.buf.rmchars(col,nln,nch)

  def c_pg(self,cdr):
    if cdr=='':
      print('pg requires n as parameter!')
      return
    sn,_=eattillspace(cdr)
    self.buf.gotopage(int(sn))
    self.buf.viewpage()

  def c_pu(self,cdr):
    self.buf.prevpage()
    self.buf.viewpage()

  def c_pd(self,cdr):
    self.buf.nextpage()
    self.buf.viewpage()

  def c_pv(self,cdr):
    self.buf.viewpage()

  def c_ps(self,cdr):
    sn,_=eattillspace(cdr)
    self.buf.pagesize(int(sn))

  def c_rp(self,cdr):
    self.buf.shrun("python3")

  def c_rj(self,cdr):
    self.buf.shrun("javac")

  def c_h(self):
    print('''---- help ----
q                ... quit
f                ... attach file to buffer
c                ... cat buffer, optional section
x <n1> <n2>      ... cut buffer to view range n1:n2
a                ... append line to buffer
w                ... save buffer to file
wq               ... write and quit
l                ... load file into buffer
D                ... clear buffer
dl <n>           ... delete line <n>
cl <n>           ... clear line <n>
il <n> <ln>      ... insert new line at line <n>
al <n> <ln>      ... append chars <ln> to end of line <n>
pl <n> <ln>      ... prepend chars <ln> to beginning of line <n>
i <col><ln>      ... insert chars starting from <col>:<ln>
d <col><ln> <n>  ... delete <n> chars starting from <col><ln>
rp               ... run python program
rj               ... run java program
h                ... this help menu
pg [<n>]         ... goto page <n>
pd               ... page down
pu               ... page up
pv               ... page view
ps <n>           ... set page size to <n> lines
ls               ... list file in cwd''')

  def repl(self):
    while True:
      cli=input(f'{self.name} > ')
      car,cdr=eattillspace(cli)
      if car=='q':
        break
      if car=='f':
        self.c_f(cdr)
      elif car=='l':
        self.c_l()
      elif car=='fl':
        self.c_fl(cdr)
      elif car=='c':
        self.c_c(cdr)
      elif car=='a':
        self.c_a(cdr)
      elif car=='w':
        self.c_w()
      elif car=='wq':
        self.c_w()
        break
      elif car=='ls':
        self.c_ls()
      elif car=='D':
        self.c_D()
      elif car=='dl':
        self.c_dl(cdr)
      elif car=='il':
        self.c_il(cdr)
      elif car=='al':
        self.c_al(cdr)
      elif car=='pl':
        self.c_pl(cdr)
      elif car=='i':
        self.c_i(cdr)
      elif car=='d':
        self.c_d(cdr)
      elif car=='x':
        self.c_x(cdr)
      elif car=='pg':
        self.c_pg(cdr)
      elif car=='pu':
        self.c_pu(cdr)
      elif car=='pd':
        self.c_pd(cdr)
      elif car=='pv':
        self.c_pv(cdr)
      elif car=='ps':
        self.c_ps(cdr)
      elif car=='rp':
        self.c_rp(cdr)
      elif car=='rj':
        self.c_rj(cdr)
      elif car=='h':
        self.c_h()
    print('bye.')


# flow begins
s=Sh('edsh')
if len(sys.argv)==2:
  s.c_fl(sys.argv[1])
s.repl()

