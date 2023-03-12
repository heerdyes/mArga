#!/usr/bin/env python3
import sys
import os
import subprocess
import time

def eattillspace(s):
  n=s.find(' ')
  if n==-1:
    return s,''
  return s[0:n],s[n+1:]


def leadingspaces(s):
  nsp=0
  for c in s:
    if c==' ':
      nsp+=1
    else:
      break
  return nsp


class Buf:
  def __init__(self):
    self.lines=[]
    self.file=None
    self.colseq='a b c d e f g h i j k l m n o p q r s t u v w x y z A B C D E F G H I J K L M N O P Q R S T U V W X Y Z '
    self.currpg=0
    self.lnsinpg=30

  def cat(self):
    print(f'    {self.colseq}')
    i=0
    for ln in self.lines:
      print('%03d %s'%(i, self.lines[i]))
      i+=1

  def slocat(self,ms):
    print(f'    {self.colseq}')
    i=0
    for ln in self.lines:
      print('%03d %s'%(i, self.lines[i]))
      i+=1
      time.sleep(ms/1000)

  def head(self,n):
    if n>=len(self.lines):
      self.cat()
    else:
      self.cut(0,n)

  def tail(self,n):
    if n>=len(self.lines):
      self.cat()
    else:
      self.cut(len(self.lines)-n,len(self.lines))

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

  def focusln(self,n,nr):
    an=n
    anr=nr
    if n<0:
      an=0
    if n>=len(self.lines):
      an=len(self.lines)-1
    if nr<1:
      anr=1
    if nr>=len(self.lines):
      anr=len(self.lines)-1
    if nr%2==0:
      anr+=1
    self.cut(an-anr//2,anr)

  def cut(self,n1,dist):
    an1=n1
    an2=n1+dist
    if an1>=an2:
      return
    if an1<0:
      an1=0
    if an1>=len(self.lines):
      an1=len(self.lines)-1
    if an2>=len(self.lines):
      an2=len(self.lines)-1
    print(f'    {self.colseq}')
    for i in range(an1,an2):
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

  def rmlns(self,n1,m):
    n2=n1+m
    if n2>len(self.lines):
      self.lines=self.lines[0:n1]
    else:
      self.lines=self.lines[0:n1]+self.lines[n2+1:]

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

  def instabln(self,n,ln):
    if n==0:
      self.insln(n,ln)
      return
    nsp=leadingspaces(self.lines[n-1])
    self.insln(n,' '*nsp+ln)

  def appendtabln(self,ln):
    nsp=leadingspaces(self.lines[-1])
    self.lines.append(' '*nsp+ln)

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
    anchor=col[0]
    drift=col[1]
    cl=self.colseq.find(anchor)
    if drift=='<':
      cl-=1
    elif drift=='>':
      cl+=1
    elif drift=='.':
      cl+=0
    else:
      print(f'unknown drift command: {drift}')
      return
    if cl>=len(self.lines[row]):
      print('column beyond range!')
      return
    self.lines[row]=self.lines[row][0:cl] + ln + self.lines[row][cl:]

  def rmchars(self,col,row,nch):
    anchor=col[0]
    drift=col[1]
    cl=self.colseq.find(anchor)
    if drift=='<':
      cl-=1
    elif drift=='>':
      cl+=1
    elif drift=='.':
      cl+=0
    else:
      print(f'unknown drift command: {drift}')
      return
    cz=cl+nch
    if cl>=len(self.lines[row]):
      print('column beyond range!')
      return
    if cz>len(self.lines[row]):
      cz=len(self.lines[row])
    self.lines[row]=self.lines[row][0:cl] + self.lines[row][cz:]

  def shrun(self,lst):
    if not self.file:
      print('no file attached!')
      return
    if len(lst)==0:
      print('run args are empty!')
      return
    subprocess.run(lst)

  def grep(self,s):
    ctr=0
    matchlns=[]
    for ln in self.lines:
      x=ln.find(s)
      if x!=-1:
        print('%03d: %s'%(ctr,ln))
        matchlns.append(ctr)
      ctr+=1


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
    self.buf.tail(10)

  def c_A(self,cdr):
    self.buf.appendtabln(cdr)
    self.buf.tail(10)

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

  def c_sc(self,cdr):
    if cdr=='':
      print('sc requires gap(ms) as parameter!')
      return
    ms,_=eattillspace(cdr)
    self.buf.slocat(int(ms))

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
    self.buf.focusln(nthln-1,10)

  def c_cl(self,cdr):
    sn,junk=eattillspace(cdr)
    if sn=='':
      return
    nthln=int(sn)
    self.buf.clrln(nthln)
    self.buf.focusln(nthln,10)

  def c_il(self,cdr):
    sn,ddr=eattillspace(cdr)
    if sn=='':
      print('il requires linenum as parameter!')
      return
    nthln=int(sn)
    self.buf.insln(nthln,ddr)
    self.buf.focusln(nthln,10)

  def c_Il(self,cdr):
    if cdr=='':
      print('Il needs atleast 2 parameters!')
      return
    sn,ln=eattillspace(cdr)
    n=int(sn)
    self.buf.instabln(n,ln)
    self.buf.focusln(n,10)

  def c_al(self,cdr):
    sn,ddr=eattillspace(cdr)
    if sn=='':
      print('il requires linenum as parameter!')
      return
    nthln=int(sn)
    self.buf.appendln(nthln,ddr)
    self.buf.focusln(nthln,10)

  def c_pl(self,cdr):
    sn,ddr=eattillspace(cdr)
    if sn=='':
      print('il requires linenum as parameter!')
      return
    nthln=int(sn)
    self.buf.prefixln(nthln,ddr)
    self.buf.focusln(nln,10)

  def c_i(self,cdr):
    if cdr=='':
      return
    cursor,chars=eattillspace(cdr)
    col=cursor[0:2]
    nln=int(cursor[2:])
    self.buf.inschars(col,nln,chars)
    self.buf.focusln(nln,10)

  def c_d(self,cdr):
    if cdr=='':
      return
    cursor,snchars=eattillspace(cdr)
    col=cursor[0:2]
    nln=int(cursor[2:])
    nch=int(snchars)
    self.buf.rmchars(col,nln,nch)
    self.buf.focusln(nln,10)

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
    self.buf.shrun(['python3', self.buf.file])

  def c_py(self,cdr):
    self.buf.shrun(['python3'])

  def c_rj(self,cdr):
    fn=self.buf.file.split('.')[0]
    self.buf.shrun(['javac', self.buf.file])
    self.buf.shrun(['java', fn])

  def c_dL(self,cdr):
    if cdr=='':
      print('dL needs 2 parameters!')
      return
    sn1,_=eattillspace(cdr)
    if _=='':
      print('dL needs 2 parameters!')
      return
    sm,_=eattillspace(_)
    n1,m=int(sn1),int(sm)
    self.buf.rmlns(n1,m)
    self.buf.focusln(n1-1,10)

  def c_v(self,cdr):
    if cdr=='':
      print('v needs 2 parameter!')
      return
    sn,_=eattillspace(cdr)
    if _=='':
      print('v needs 2 parameters!')
      return
    srows,_=eattillspace(_)
    self.buf.focusln(int(sn), int(srows))

  def c_slash(self,cdr):
    if cdr=='':
      print('/ needs a target!')
      return
    self.buf.grep(cdr)

  def c_h(self):
    print('''---- help ----
q                ... quit
f                ... attach file to buffer
c                ... cat buffer, optional section
sc <ms>          ... cat buffer in slow motion with <ms> millis gap
x <n1> <n2>      ... cut buffer to view range n1:n2
a                ... append line to buffer
A                ... append indented line to buffer
w                ... save buffer to file
wq               ... write and quit
l                ... load file into buffer
D                ... clear buffer
dl <n>           ... delete line <n>
dL <n1> <m>      ... del <m> lines starting from <n1>
cl <n>           ... clear line <n>
il <n> <ln>      ... insert new line at line <n>
Il <n> <ln>      ... insert line <ln> at <n> with same indentation as line <n-1>
al <n> <ln>      ... append chars <ln> to end of line <n>
pl <n> <ln>      ... prepend chars <ln> to beginning of line <n>
i <col><ln>      ... insert chars starting from <col>:<ln>
d <col><ln> <n>  ... delete <n> chars starting from <col><ln>
rp               ... run python program
py               ... launch python shell
rj               ... run java program
h                ... this help menu
pg [<n>]         ... goto page <n>
pd               ... page down
pu               ... page up
pv               ... page view
v <ln> <h>       ... center view for line <ln> with <h> rows
/ <target>       ... search buffer for <target>
ps <n>           ... set page size to <n> lines
ls               ... list file in cwd''')

  def repl(self):
    while True:
      try:
        cli=input(f'{self.name} > ')
        car,cdr=eattillspace(cli)
        if car=='q':
          print('bye.')
          raise SystemExit
        if car=='f':
          self.c_f(cdr)
        elif car=='l':
          self.c_l()
        elif car=='fl':
          self.c_fl(cdr)
        elif car=='c':
          self.c_c(cdr)
        elif car=='sc':
          self.c_sc(cdr)
        elif car=='a':
          self.c_a(cdr)
        elif car=='A':
          self.c_A(cdr)
        elif car=='w':
          self.c_w()
        elif car=='wq':
          self.c_w()
          raise SystemExit
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
        elif car=='py':
          self.c_py(cdr)
        elif car=='rj':
          self.c_rj(cdr)
        elif car=='dL':
          self.c_dL(cdr)
        elif car=='v':
          self.c_v(cdr)
        elif car=='Il':
          self.c_Il(cdr)
        elif car=='/':
          self.c_slash(cdr)
        elif car=='h':
          self.c_h()
      except ValueError as ve:
        print(f'error: {str(ve)}')
        print('[remarks] most likely some space missing between parameters')


# flow begins
s=Sh('edsh')
if len(sys.argv)==2:
  s.c_fl(sys.argv[1])
s.repl()

