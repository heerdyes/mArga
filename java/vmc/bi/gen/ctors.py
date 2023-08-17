#!/usr/bin/env python3
def xpandr(s,rc,n,o='n'):
  for i in range(n):
    if o=='n':
      print(s.replace(rc,str(i)))
    elif o=='r':
      print(s.replace(rc,str(n-i-1)))

#xpandr('d$=new Decoder2to4();','$',8,'r')

