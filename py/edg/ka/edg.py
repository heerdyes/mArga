#!/usr/bin/env python3
from tkinter import *
from typo.z1 import *
from sys import argv
import cfg
import os

debug = True

def d(s):
  if debug:
    print(s)


class Edg(Tk):
  def __init__(self, fn):
    Tk.__init__(self)
    self.cmap = fntmap
    self.cursor = 0
    self.buffer = []
    self.curloc = [40, 40]
    self.filename = fn
    self.ftsz = cfg.fontsize
    self.cgap = cfg.chargap
    self.lnht = cfg.lineheight
    self.nspaces4tab = cfg.tabspaces
    self.wm_title('edg')
    self.cnv = Canvas(self, bg=cfg.background, width=cfg.windowwidth, height=cfg.windowheight)
    self.bind("<Tab>", self.tabpress)
    self.bind_all("<Any-KeyPress>", self.kbpress)
    self.cnv.pack()
    self.file2buf()
    
  def file2buf(self):
    try:
      with open(self.filename, 'r') as fp:
        cdata = fp.read()
        for c in cdata:
          self.buffer.insert(self.cursor, c)
          self.cursor += 1
      self.rndrbuf()
      print('file loaded!')
    except FileNotFoundError as fnfe:
      print('file not found, creating anew')
      open(self.filename, 'a').close()
  
  def buf2file(self):
    try:
      with open(self.filename, 'w') as fp:
        fp.write(''.join(self.buffer))
      print('file written!')
    except:
      print('unable to write to file!')
  
  def rndrbuf(self):
    self.cnv.delete('all')
    dx, dy = self.curloc
    i = 0
    for c in self.buffer:
      if i == self.cursor:
        self.drawcursor([dx+2, dy-5], self.ftsz)
      if c in '\r\n':
        dy += self.lnht
        dx = self.curloc[0]
      elif c == '\t':
        dx += self.ftsz + self.cgap
      else:
        self.putch(c, [dx, dy], self.ftsz)
        dx += self.cgap
      i += 1
  
  def tabpress(self, e):
    print('Tab')
    for i in range(self.nspaces4tab):
      self.buffer.insert(self.cursor, ' ')
      self.cursor += 1
    self.rndrbuf()
  
  def invokepy(self):
    os.system("python3 " + self.filename)
  
  def kbpress(self, e):
    kc = e.keycode
    d(kc)
    if kc==9:
      print(cfg.goodbyemsg)
      raise SystemExit()
    if kc==66:
      print('caps lock')
    elif kc==68:
      print('F2: save file')
      self.buf2file()
    elif kc==71:
      print('F5: run python file')
      self.invokepy()
    elif kc==50:
      print('shift')
    elif kc==22:
      if self.cursor > 0:
        self.cursor -= 1
        self.buffer.pop(self.cursor)
        self.rndrbuf()
      else:
        print('already at beginning, cannot backspace')
    elif kc==37:
      print('Ctrl')
    elif kc==64:
      print('Alt')
    elif kc==119:
      print('Del')
      if self.cursor < len(self.buffer):
        self.buffer.pop(self.cursor)
        self.rndrbuf()
      else:
        print('already at end, cannot delete')
    elif kc==113: # arrow_left
      if self.cursor > 0:
        self.cursor -= 1
        self.rndrbuf()
    elif kc==114: # arrow_right
      if self.cursor < len(self.buffer):
        self.cursor += 1
        self.rndrbuf()
    elif kc==111: # arrow_up
      if self.cursor != 0 and len(self.buffer) > 0:
        while self.buffer[self.cursor-1] not in '\r\n' and self.cursor != 0:
          self.cursor -= 1
        self.rndrbuf()
    elif kc==116: # arrow down
      while self.buffer[self.cursor-1] not in '\r\n' and self.cursor != len(self.buffer):
        self.cursor += 1
      self.rndrbuf()
    elif e.char in '\r\n qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM,./<>?;:\'"#!*()[]{}1234567890-=_+|\\':
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
    self.cnv.create_line(*pxy, fill=cfg.foreground, width=cfg.lnwidth)
  
  def drawcursor(self, cxy, k):
    self.cnv.create_line(cxy[0], cxy[1], cxy[0], cxy[1]+k, fill=cfg.cursorcolor)
    
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
if len(argv) != 2:
  print(cfg.usagemsg)
  raise SystemExit

fn = argv[1]
print('editing file: %s'%fn)
z = Edg(fn)
z.mainloop()

