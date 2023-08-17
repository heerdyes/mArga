def gensetters(prefix,n):
  for i in range(n):
    s1=prefix.upper()+str(i)
    s0=prefix+str(i)
    print('void set%s(boolean x){%s=x;senddata();}'%(s1,s0))

gensetters('a',16)
gensetters('d',8)

