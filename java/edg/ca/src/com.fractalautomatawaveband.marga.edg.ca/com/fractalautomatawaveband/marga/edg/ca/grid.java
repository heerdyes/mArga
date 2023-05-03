package com.fractalautomatawaveband.marga.edg.ca;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class grid extends box
{
  int rows;
  int cols;
  char[][] cgrid;
  double cgap;
  double rgap;
  int[] cursor;
  Color hg,lg;
  GraphicsContext ctx;
  Font fnt;
  boolean capslock=false;
  String mode="Normal";
  
  void initdata()
  {
    for(int i=0;i<rows;i++)
    {
      for(int j=0;j<cols;j++)
      {
        cgrid[i][j]='\0';
      }
    }
    cursor=new int[]{0,0};
  }
  
  public grid(double x,double y,double w,double h,int nr,int nc,GraphicsContext gc)
  {
    super(x,y,w,h);
    hg=Color.rgb(23,202,232);
    lg=Color.rgb(0,72,0);
    rows=nr;
    cols=nc;
    cgrid=new char[nr][nc];
    ctx=gc;
    fnt=new Font("OCRA",14);
    gc.setFont(fnt);
    initdata();
  }
  
  void rectify()
  {
    // cursor column rectification
    if(cursor[1]==cols)
    {
      cursor[1]=0;
    }
    else if(cursor[1]==-1)
    {
      cursor[1]=cols-1;
    }
    // cursor row rectification
    if(cursor[0]==rows)
    {
      cursor[0]=0;
    }
    else if(cursor[0]==-1)
    {
      cursor[0]=rows-1;
    }
  }
  
  public void handlenav(String kn)
  {
    if(mode.equals("Normal") || mode.equals("Shift"))
    {
      if(kn.equals("Right"))
      {
        cursor[1]+=1;
      }
      else if(kn.equals("Left"))
      {
        cursor[1]-=1;
      }
      else if(kn.equals("Up"))
      {
        cursor[0]-=1;
      }
      else if(kn.equals("Down"))
      {
        cursor[0]+=1;
      }
    }
    else if(mode.equals("Ctrl"))
    {
      System.out.println("[TODO] implement this!");
    }
  }
  
  public void handlespace()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]=' ';
    }
    else if(mode.equals("Shift"))
    {
      System.out.println("[TODO] implement this!");
    }
  }
  
  public void handleenter()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]='\n';
    }
    else if(mode.equals("Shift"))
    {
      System.out.println("[TODO] implement this!");
    }
  }
  
  public void handlecomma()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]=',';
    }
    else if(mode.equals("Shift"))
    {
      cgrid[cursor[0]][cursor[1]]='<';
    }
  }
  
  public void handleperiod()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]='.';
    }
    else if(mode.equals("Shift"))
    {
      cgrid[cursor[0]][cursor[1]]='>';
    }
  }
  
  public void handleslash()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]='/';
    }
    else if(mode.equals("Shift"))
    {
      cgrid[cursor[0]][cursor[1]]='?';
    }
  }
  
  public void handlebackslash()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]='\\';
    }
    else if(mode.equals("Shift"))
    {
      cgrid[cursor[0]][cursor[1]]='|';
    }
  }
  
  public void handleminus()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]='-';
    }
    else if(mode.equals("Shift"))
    {
      cgrid[cursor[0]][cursor[1]]='_';
    }
  }
  
  public void handlesemicolon()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]=';';
    }
    else if(mode.equals("Shift"))
    {
      cgrid[cursor[0]][cursor[1]]=':';
    }
  }
  
  public void handlequote()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]='\'';
    }
    else if(mode.equals("Shift"))
    {
      cgrid[cursor[0]][cursor[1]]='"';
    }
  }
  
  public void handleequals()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]='=';
    }
    else if(mode.equals("Shift"))
    {
      cgrid[cursor[0]][cursor[1]]='+';
    }
  }
  
  public void handlebackspace()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]='\0';
    }
    else if(mode.equals("Shift"))
    {
      System.out.println("[TODO] unimplemented");
    }
  }
  
  public void handleopenbracket()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]='[';
    }
    else if(mode.equals("Shift"))
    {
      cgrid[cursor[0]][cursor[1]]='{';
    }
  }
  
  public void handleclosebracket()
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]=']';
    }
    else if(mode.equals("Shift"))
    {
      cgrid[cursor[0]][cursor[1]]='}';
    }
  }
  
  public void handleletter(String z)
  {
    if(mode.equals("Shift") ^ capslock)
    {
      cgrid[cursor[0]][cursor[1]]=z.charAt(0);
    }
    else
    {
      cgrid[cursor[0]][cursor[1]]=z.toLowerCase().charAt(0);
    }
  }
  
  public void handledigit(String z)
  {
    if(mode.equals("Normal"))
    {
      cgrid[cursor[0]][cursor[1]]=z.charAt(0);
    }
    else if(mode.equals("Shift"))
    {
      char m=z.charAt(0);
      switch(m)
      {
      case '1':
        cgrid[cursor[0]][cursor[1]]='!';
        break;
      case '2':
        cgrid[cursor[0]][cursor[1]]='@';
        break;
      case '3':
        cgrid[cursor[0]][cursor[1]]='#';
        break;
      case '4':
        cgrid[cursor[0]][cursor[1]]='$';
        break;
      case '5':
        cgrid[cursor[0]][cursor[1]]='%';
        break;
      case '6':
        cgrid[cursor[0]][cursor[1]]='^';
        break;
      case '7':
        cgrid[cursor[0]][cursor[1]]='&';
        break;
      case '8':
        cgrid[cursor[0]][cursor[1]]='*';
        break;
      case '9':
        cgrid[cursor[0]][cursor[1]]='(';
        break;
      case '0':
        cgrid[cursor[0]][cursor[1]]=')';
        break;
      }
    }
  }

  public void onkeydown(String kn)
  {
    if(kn.equals("Right") || kn.equals("Left") || kn.equals("Up") || kn.equals("Down"))
    {
      handlenav(kn);
    }
    else if(kn.equals("Space"))
    {
      handlespace();
    }
    else if(kn.equals("Enter"))
    {
      handleenter();
    }
    else if(kn.equals("Shift"))
    {
      mode=mode.equals("Normal")?"Shift":"Normal";
    }
    else if(kn.equals("Caps Lock"))
    {
      capslock=!capslock; // should this be permutable with Shift?
    }
    else if(kn.equals("Comma"))
    {
      handlecomma();
    }
    else if(kn.equals("Period"))
    {
      handleperiod();
    }
    else if(kn.equals("Slash"))
    {
      handleslash();
    }
    else if(kn.equals("Back Slash"))
    {
      handlebackslash();
    }
    else if(kn.equals("Minus"))
    {
      handleminus();
    }
    else if(kn.equals("Semicolon"))
    {
      handlesemicolon();
    }
    else if(kn.equals("Quote"))
    {
      handlequote();
    }
    else if(kn.equals("Equals"))
    {
      handleequals();
    }
    else if(kn.equals("Backspace"))
    {
      handlebackspace();
    }
    else if(kn.equals("Open Bracket"))
    {
      handleopenbracket();
    }
    else if(kn.equals("Close Bracket"))
    {
      handleclosebracket();
    }
    else if(kn.length()==1)
    {
      if(u.cin(kn.charAt(0),"ABCDEFGHIJKLMNOPQRSTUVWXYZ"))
      {
        handleletter(kn);
      }
      else if(u.cin(kn.charAt(0),"0123456789"))
      {
        handledigit(kn);
      }
      else
      {
        System.out.println("[ALERT] how to handle: "+kn);
      }
    }
    else
    {
      System.out.println("[ALERT] how to handle: "+kn);
    }
    rectify();
    rndr();
  }
  
  public void rndr()
  {
    if(ctx==null)
    {
      throw new RuntimeException("[BOOM] GraphicsContext ctx was null!");
    }
    rndr(ctx);
  }
  
  public void rndrchar(GraphicsContext gc,char cc,double px,double py)
  {
    gc.setStroke(fg);
    if(cc=='\n')
    {
      gc.strokeLine(px,py,px+9,py);
      gc.strokeLine(px+9,py,px+9,py-9);
      gc.strokeLine(px,py,px+3,py-2);
      gc.strokeLine(px,py,px+3,py+2);
    }
    else if(cc==' ')
    {
      gc.strokeRect(px-3.5,py-8,15.5,5);
    }
    else
    {
      gc.setFill(fg);
      gc.fillText(""+cc,px,py);
    }
  }
  
  public void rndrcell(GraphicsContext gc,
    int i,int j,
    double px,double py,
    double pw,double ph)
  {
    if(i==cursor[0] && j==cursor[1])
    {
      gc.setStroke(hg);
    }
    else
    {
      gc.setStroke(lg);
    }
    gc.strokeRect(px,py,pw-2,ph-2);
  }
  
  public void rndr(GraphicsContext gc)
  {
    super.rndr(gc);
    gc.setFill(fg);
    
    cgap=w/(double)cols;
    rgap=h/(double)rows;
    
    for(int i=0;i<rows;i++)
    {
      for(int j=0;j<cols;j++)
      {
        rndrcell(gc,i,j,x+cgap*j,y+rgap*i,cgap,rgap);
        rndrchar(gc,cgrid[i][j],x+j*cgap+8,y+i*rgap+17);
      }
    }
  }
}

