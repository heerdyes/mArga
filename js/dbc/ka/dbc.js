const fs=require('fs');

let sepch='|';

function mktbl(tn,cols) {
  let s="";
  for (let i=0;i<cols.length;i++) {
    s+=cols[i]+(i==cols.length-1)?"":sepch;
  }
  s+="\n";
  fs.writeFileSync(`${tn}.tbl`, s);
}

function insrow(tn,values) {
  let s="";
  for (let i=0;i<cols.length;i++) {
    s+=cols[i]+(i==cols.length-1)?"":sepch;
  }
  s+="\n";
  fs.appendFileSync(`${tn}.tbl`, s);
}

