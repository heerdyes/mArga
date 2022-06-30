import java.awt.*;

public class ZBuf
{
  char[] buf;
  int rows=30,cols=80;
  int idx=0;
  int currln=0,currcol=0;
  int rowspacing=60,colspacing=30;
  int leftmargin=20,topmargin=50;
  T t;
  
  ZBuf()
  {
    buf=new char[rows*cols];
    for(int i=0;i<buf.length;i++)
    {
      buf[i]='x';
    }
    t=new T();
  }
  
  void head(int n)
  {
    for(int i=0;i<n;i++)
    {
      System.out.printf("%c",buf[i]);
    }
    System.out.println();
  }
  
  void toa()
  {
    for(int i=0;i<2;i++)
    {
      t.fd(20); t.lt(90); t.fd(10); t.lt(90);
    }
    for(int i=0;i<3;i++)
    {
      t.fd(20); t.lt(90);
    }
  }
  
  void tob()
  {
    for(int i=0;i<4;i++)
    {
      t.fd(20); t.lt(90);
    }
    t.lt(90); t.fd(40);
  }
  
  void toc()
  {
    t.fd(20); t.lt(180);
    for(int i=0;i<3;i++)
    {
      t.fd(20); t.rt(90);
    }
  }
  
  void tod()
  {
    for(int i=0;i<4;i++)
    {
      t.fd(20); t.lt(90);
    }
    t.fd(20); t.lt(90); t.fd(40);
  }
  
  void toe()
  {
    t.fd(20); t.lt(180);
    for(int i=0;i<3;i++)
    {
      t.fd(20); t.rt(90);
    }
    t.fd(10); t.rt(90); t.fd(20);
  }
  
  void tof()
  {
    t.pu(); t.fd(5); t.pd(); t.fd(5);
    t.lt(90); t.fd(20); t.rt(90);
    t.fd(5); t.bk(10); t.fd(5); t.lt(90);
    t.fd(20); t.rt(90); t.fd(5);
  }
  
  void tog()
  {
    for(int i=0;i<4;i++)
    {
      t.fd(20); t.lt(90);
    }
    t.fd(20); t.rt(90); t.fd(10); t.rt(90); t.fd(20);
  }
  
  void toh()
  {
    t.lt(90); t.fd(40); t.bk(20); t.rt(90);
    t.fd(20); t.rt(90); t.fd(20);
  }
  
  void toi()
  {
    t.pu(); t.fd(10); t.lt(90); t.pd();
    t.fd(20); t.pu(); t.fd(5); t.pd(); t.fd(5);
  }
  
  void toj()
  {
    t.pu(); t.fd(10); t.lt(90); t.bk(10); t.rt(90); t.bk(10); t.pd();
    t.fd(10); t.lt(90); t.fd(30);
    t.pu(); t.fd(3); t.pd(); t.fd(3);
  }
  
  void tok()
  {
    t.lt(90); t.fd(40); t.bk(30); t.rt(90-30);
    t.fd(20); t.bk(20); t.rt(30+30); t.fd(20);
  }
  
  void tol()
  {
    t.pu(); t.fd(10); t.lt(90); t.pd(); t.fd(40);
  }
  
  void tom()
  {
    t.lt(90);
    for(int j=0;j<2;j++)
    {
      t.fd(20); t.rt(90); t.fd(10); t.rt(90); t.fd(20); t.lt(180);
    }
  }
  
  void ton()
  {
    t.lt(90);
    for(int i=0;i<3;i++)
    {
      t.fd(20); t.rt(90);
    }
  }
  
  void too()
  {
    for(int i=0;i<4;i++)
    {
      t.fd(20); t.lt(90);
    }
  }
  
  void top()
  {
    for(int i=0;i<4;i++)
    {
      t.fd(20); t.lt(90);
    }
    t.rt(90); t.fd(10);
  }
  
  void toq()
  {
    for(int i=0;i<4;i++)
    {
      t.fd(20); t.lt(90);
    }
    t.fd(20); t.rt(90); t.fd(10);
  }
  
  void tor()
  {
    t.lt(90); t.fd(20);
    t.bk(3); t.rt(60); t.fd(5); t.rt(30); t.fd(16);
  }
  
  void tos()
  {
    t.fd(20); t.lt(90); t.fd(10); t.lt(90);
    t.fd(20); t.rt(90); t.fd(10); t.rt(90);
    t.fd(20);
  }
  
  void tot()
  {
    t.pu(); t.fd(10); t.pd(); t.fd(10); t.bk(10); t.lt(90);
    t.fd(35); t.bk(10); t.lt(90); t.fd(5); t.bk(10);
  }
  
  void tou()
  {
    t.lt(90); t.fd(20); t.rt(180);
    for(int i=0;i<3;i++)
    {
      t.fd(20); t.lt(90);
    }
  }
  
  void tov()
  {
    int _th=65,_x=23;
    t.lt(90); t.pu(); t.fd(20); t.pd(); t.rt(90+_th);
    t.fd(_x); t.lt(2*_th); t.fd(_x);
  }
  
  void tow()
  {
    t.lt(90); t.fd(20); t.rt(180);
    for(int j=0;j<2;j++)
    {
      t.fd(20); t.lt(90); t.fd(10); t.lt(90); t.fd(20); t.rt(180);
    }
  }
  
  void tox()
  {
    t.lt(45); t.fd(26); t.bk(13); t.lt(90); t.fd(13); t.bk(26);
  }
  
  void toy()
  {
    t.lt(90); t.fd(20); t.rt(180);
    for(int i=0;i<3;i++)
    {
      t.fd(20); t.lt(90);
    }
    t.lt(90); t.fd(30); t.rt(90); t.fd(20);
  }
  
  void toz()
  {
    t.fd(20); t.bk(20); t.lt(45); t.fd(28); t.rt(45); t.bk(20);
  }
  
  void sendch(int kc)
  {
    if((kc>=65 && kc<=90) || (kc>=97 && kc<=122) || kc==32)
    {
      char x=(char)kc;
      buf[idx++]=x;
      currcol++;
    }
    else if(kc==10)
    {
      buf[idx++]='\n';
      currln++;
      currcol=0;
    }
    else
    {
      d("charcode outside scope: "+kc);
    }
  }
  
  void render(Graphics2D g2)
  {
    int j=0,i=0;
    t.setg(g2);
    for(int c=0;c<buf.length;c++)
    {
      t.setxy(leftmargin+j*colspacing,topmargin+i*rowspacing);
      t.seta(0);
      switch(buf[c])
      {
        case 'A': toa(); j++; break;
        case 'B': tob(); j++; break;
        case 'C': toc(); j++; break;
        case 'D': tod(); j++; break;
        case 'E': toe(); j++; break;
        case 'F': tof(); j++; break;
        case 'G': tog(); j++; break;
        case 'H': toh(); j++; break;
        case 'I': toi(); j++; break;
        case 'J': toj(); j++; break;
        case 'K': tok(); j++; break;
        case 'L': tol(); j++; break;
        case 'M': tom(); j++; break;
        case 'N': ton(); j++; break;
        case 'O': too(); j++; break;
        case 'P': top(); j++; break;
        case 'Q': toq(); j++; break;
        case 'R': tor(); j++; break;
        case 'S': tos(); j++; break;
        case 'T': tot(); j++; break;
        case 'U': tou(); j++; break;
        case 'V': tov(); j++; break;
        case 'W': tow(); j++; break;
        case 'X': tox(); j++; break;
        case 'Y': toy(); j++; break;
        case 'Z': toz(); j++; break;
        case ' ': j++; break;
        case '\n': i++; j=0; break;
        default: j++;
      }
    }
  }
  
  void d(String x)
  {
    System.out.println("[DEBUG] "+x);
  }
}
