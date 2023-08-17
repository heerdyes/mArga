#!/usr/bin/env python3

def rmxnd(m,n,o='n'):
  for i in range(m):
    print('System.out.println("| "+',end='')
    for j in range(n):
      if o=='n':
        print('(r%d.dq%d.q?"1":"0")+'%(j,i),end='')
      elif o=='r':
        print('(r%d.dq%d.q?"1":"0")+'%(n-j-1,i),end='')
    print('" |");')

#print('ram8x8')
#rmxnd(8,8,'r')
print('ram8x2')
rmxnd(8,2,'r')

