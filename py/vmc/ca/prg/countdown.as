mem 50
zfoff
push 11
push 42
put
@ L
push 42
get
dec
save
load
load
push 42
put
print
jnz L
halt
