import os
import u
import fs
import sys

termmode='TERM' in os.environ
pwd=os.getcwd()
if not termmode:
  print('[shc] you are not a terminal!', file=sys.stderr)

# flow
while True:
  if termmode:
    cli=input('shc-> ')
  else:
    cli=input()
  if cli=='q':
    break
  cmd,args=u.cadr(cli)
  try:
    if cmd=='pwd':
      print(pwd)
    elif cmd=='ls':
      fs.ls(args,pwd)
    elif cmd=='cat':
      fs.cat(args,pwd)
    else:
      print('unknown cmd: ' + cmd, file=sys.stderr)
  except Exception as e:
    print('[ERROR] %s'%str(e))

