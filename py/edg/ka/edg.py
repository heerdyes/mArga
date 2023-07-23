#!/usr/bin/env python3
from tkinter import *
from typo.z1 import *


class Edg(Tk):
  def __init__(self):
    Tk.__init__(self)
    self.cmap = fntmap
    self.cursor = 0
    self.buffer = []
    self.curloc = [40, 40]
    self.cgap = 30
    self.lnht = 40
    self.ftsz = 36
    self.wm_title('edg')
    self.cnv = Canvas(self, bg='black', width=1600, height=900)
    self.bind_all("<Any-KeyPress>", self.kbpress)
    self.cnv.pack()
    
  def rndrbuf(self):
    self.cnv.delete('all')
    dx, dy = self.curloc
    for c in self.buffer:
      if c == '\r':
        dy += self.lnht
        dx = self.curloc[0]
      else:
        self.putch(c, [dx, dy], self.ftsz)
        dx += self.cgap
    
  def kbpress(self, e):
    kc = e.keycode
    print(kc)
    if kc==9:
      print('sayonara!')
      raise SystemExit()
    if kc==66:
      print('caps lock')
    elif kc==50:
      print('shift')
    elif kc==22:
      if self.cursor>0:
        self.cursor -= 1
        self.buffer.pop(self.cursor)
        self.rndrbuf()
      else:
        print('already at beginning, cannot backspace')
    elif e.char in '\r qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM,./<>?;:\'"[]{}1234567890-=_+|\\':
      self.buffer.insert(self.cursor, e.char)
      self.cursor += 1
      self.rndrbuf()
    else:
      print('cannot handle keycode: %d'%kc)
  
  def polyline(self, cxy, pts, k):
    i = 0
    pxy = []
    while i < len(pts):
      pxy.append(cxy[0] + round(pts[i] * k))
      pxy.append(cxy[1] + round(pts[i+1] * k))
      i += 2
    self.cnv.create_line(*pxy, fill='white')
    
  def putch(self, ch, cxy, k):
    if ch == ' ':
      return
    if ch not in self.cmap:
      print('char "%s" not in cmap'%ch)
      return
    plns = self.cmap[ch]
    for pln in plns:
      self.polyline(cxy, pln, k)


# flow begins
z = Edg()
z.mainloop()

