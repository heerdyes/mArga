def s2a(n,p,v,y):
  for i in range(n):
    print("%s%s%d=%s.charAt(%d)=='0'?false:true;"%(p,v,n-i-1,y,i))

def l2out(n,p):
  for i in range(n):
    print('(%s%d?"1":"0")+'%(p,n-i-1),end='')

#l2out(8,'l')
#s2a(16,'    ','a','addr')
s2a(8,'    ','d','dat')

