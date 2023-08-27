import os
import u
import fs
import sys
import subprocess
from pathlib import Path

termmode = 'TERM' in os.environ
cwd = Path.cwd()
if not termmode:
  print('you do not seem like a terminal!', file = sys.stderr)

# flow
while True:
  if termmode:
    cli = input('shc-> ')
  else:
    cli = input()
  if cli == 'q':
    break
  cmd, args = u.cadr(cli)
  try:
    if cmd in ['cwd', 'pwd']:
      print(cwd)
    elif cmd == 'ed':
      arg1,_ = u.cadr(args)
      if 'EDITOR' not in os.environ:
        print('no default system editor defined!', file = sys.stderr)
      else:
        subprocess.Popen([os.environ['EDITOR'], arg1])
    elif cmd == 'cd':
      arg1,_ = u.cadr(args)
      if arg1 == '..':
        cwd = cwd.parent
      elif arg1 == '.':
        pass
      else:
        cwd = cwd / arg1
    elif cmd == 'ls':
      fs.ls(args, cwd)
    elif cmd == 'cat':
      fs.cat(args, cwd)
    elif cmd == 'set':
      vnm, vvl = u.cadr(args)
      os.environ[vnm] = vvl
    elif cmd == 'get':
      vnm,_ = u.cadr(args)
      if vnm in os.environ:
        print(os.environ[vnm])
      else:
        print('var %s not found!' % vnm, file = sys.stderr)
    else:
      print('unknown cmd: ' + cmd, file = sys.stderr)
  except Exception as e:
    print('[ERROR] %s' % str(e), file = sys.stderr)

