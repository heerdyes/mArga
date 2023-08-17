def gendat(n,pref):
  for i in range(n):
    print("%sa%d=dat.charAt(%d)=='0'?false:true;"%(pref,n-i-1,i))

gendat(16,'    ')

