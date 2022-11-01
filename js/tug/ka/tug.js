let cli=document.getElementById('t_00');
let log=document.getElementById('transcript');
const cnv=document.getElementById('cnv');
const g=cnv.getContext('2d');

const trtl=new Turtle(g,cnv.width/2,cnv.height/2);
const sh=new Shell(trtl);

g.lineWidth=1.0;
g.strokeStyle='black';
g.strokeRect(1,1,cnv.width-2,cnv.height-2);

// event listeners
window.addEventListener("keydown", (e) => {
  console.log(`[keydown] keyCode -> ${e.keyCode}`);
  if(e.keyCode===13) {
    let cmd=cli.value;
    sh.cmdproc(cmd);
    cli.value='';
    log.value+=cmd+'\n';
  }
});

