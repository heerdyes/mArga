// turtle stuff
class Turtle {
  constructor(ctx,cnv) {
    this.x=cnv.width/2;
    this.y=cnv.height/2;
    this.a=0.0;
    this.g=ctx;
    this.cnv=cnv;
  }
  
  fd(r) {
    let x2=this.x + r*Math.cos(this.a);
    let y2=this.y - r*Math.sin(this.a);
    this.g.strokeStyle='black';
    this.g.beginPath();
    this.g.moveTo(this.x,this.y);
    this.g.lineTo(x2,y2);
    this.g.stroke();
    this.x=x2;
    this.y=y2;
  }
  
  bk(r) {
    this.fd(-r);
  }

  lt(a) {
    let rads=a*Math.PI/180.0;
    this.a+=rads;
  }

  rt(a) {
    this.lt(-a);
  }
  
  clrscr() {
    this.g.fillStyle='white';
    this.g.fillRect(1,1,this.cnv.width-2,this.cnv.height-2);
  }
}

// the language processor
class Shell {
  constructor(t) {
    this.t=t
  }
  
  parseExpression(s) {
    let parts=s.split(' ');
    if(parts.length===2) {
      if(s.startsWith('fd ')) {
        this.t.fd(parseFloat(parts[1]));
      }else if(s.startsWith('lt ')) {
        this.t.lt(parseFloat(parts[1]));
      }else if(s.startsWith('rt ')) {
        this.t.rt(parseFloat(parts[1]));
      }else if(s.startsWith('bk ')) {
        this.t.bk(parseFloat(parts[1]));
      }
    }
  }
  
  parseAssignment(s) {}
  
  parseCmd(s) {
    if(s=='cs'){
      console.log('clearing screen');
      this.t.clrscr();
    }
  }

  // command processing delegator
  cmdproc(s) {
    if(s.includes('=')) {
      this.parseAssignment(s);
    } else if(s.includes(' ')) {
      this.parseExpression(s);
    } else {
      this.parseCmd(s);
    }
  }
}

