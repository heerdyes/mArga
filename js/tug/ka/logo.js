// turtle stuff
class Turtle {
  constructor(ctx,x,y) {
    this.x=x;
    this.y=y;
    this.a=0.0;
    this.g=ctx;
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
  
  parseCmd(s) {}

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

