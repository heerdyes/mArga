from tkinter import Tk, Text, Label, filedialog, Scrollbar
import cfg
from sys import argv
from os.path import exists

def savefile():
  rs = filedialog.asksaveasfile()
  if rs:
    fn = rs.name
    print('saving to %s' % fn)
    with open(fn, 'w') as f:
      f.write(text.get('1.0', 'end'))
  else:
    print('cancelled!')

def loadfile():
  rs = filedialog.askopenfile()
  if rs:
    fn = rs.name
    print('loading from %s' % fn)
    with open(fn, 'r') as f:
      text.insert('end-1c', f.read())
  else:
    print('cancelled!')

def onkey(e):
  if e.keysym == 'Escape':
    print('sayonara')
    raise SystemExit
  if e.keysym == 'F2':
    savefile()
  elif e.keysym == 'F3':
    loadfile()


# flow
root = Tk()
root.resizable(False, False)
root.title("edg")

text = Text(root, background=cfg.bg, insertbackground=cfg.cg, foreground=cfg.fg, font=cfg.fnt, height=cfg.ht)
text.bind("<Key>", onkey)
ys = Scrollbar(root, orient='vertical', command=text.yview)
text['yscrollcommand'] = ys.set
text.grid(column=0, row=0, sticky='nwes')
ys.grid(column=1, row=0, sticky='ns')

if len(argv) == 2:
  fn = argv[1]
  if exists(fn):
    with open(fn, 'r') as f:
      text.insert('end-1c', f.read())
  else:
    print('file %s nonexistent. touching it...' % fn)
    with open(fn, 'w') as f:
      pass

root.mainloop()

