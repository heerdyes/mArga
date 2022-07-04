import os
import u
import fs

pwd=os.getcwd()

# flow
while True:
  cli=input('shc-> ')
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
  except Exception as e:
    print('[ERROR] %s'%str(e))

