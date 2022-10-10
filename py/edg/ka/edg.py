import tkinter


root=tkinter.Tk()
c=tkinter.Canvas(root,bg='black',width=1280,height=720)
cmap={
  'a':[0.0,0.0,0.5,0.0,0.5,1.0,0.0,1.0,0.0,0.5,0.5,0.5],
  'b':[0.0,0.0,0.0,1.0,0.5,1.0,0.5,0.5,0.0,0.5],
  'z':[0.0,0.0,1.0,0.0,0.0,1.0,1.0,1.0]
}


# funxionality
def polyline(cxy,pts,k):
  if len(pts)<4 or len(pts)%2!=0:
    raise Exception('illegal polyline invocation!')
  p0=cxy[0]+k*pts[0],cxy[1]+k*pts[1]
  i=2
  while i<len(pts):
    p1=cxy[0]+k*pts[i],cxy[1]+k*pts[i+1]
    c.create_line(p0,p1,fill='white')
    p0=p1
    i+=2


# flow begins
polyline([100,100],cmap['a'],80)
polyline([200,100],cmap['b'],80)
polyline([300,100],cmap['z'],80)

c.pack()
root.mainloop()
