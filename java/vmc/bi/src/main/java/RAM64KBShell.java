import java.io.*;

class ParameterException extends RuntimeException{
  public ParameterException(String msg){
    super(msg);
  }
}

public class RAM64KBShell implements ControlPanel{
  RAM65536x8 ram;
  BufferedReader br;

  RAM64KBShell(){
    ram=new RAM65536x8();
    InputStreamReader isr=new InputStreamReader(System.in);
    br=new BufferedReader(isr);
  }

  public void feeddata(String addr,String w,String di){
    if(addr.length()!=16 || w.length()!=1 || di.length()!=8){
      throw new RuntimeException("[ERR] bit length not proper!");
    }
    ram.feed(addr,w,di);
  }
  
  public String collectdata(){
    return (ram.do7?"1":"0")+(ram.do6?"1":"0")+(ram.do5?"1":"0")+(ram.do4?"1":"0")
          +(ram.do3?"1":"0")+(ram.do2?"1":"0")+(ram.do1?"1":"0")+(ram.do0?"1":"0");
  }
  
  void memrd(String addr){
    try{
      feeddata(addr,"0","00000000");
      System.out.println(collectdata());
    }catch(Exception e){
      System.err.println(e.getMessage());
    }
  }
  
  void memwr(String addr,String dat){
    try{
      feeddata(addr,"1",dat);
      System.out.println(collectdata());
    }catch(Exception e){
      System.err.println(e.getMessage());
    }
  }
  
  void chkargs(String cmd,int n,int k){
    if(n!=k){
      String fmtmsg=String.format("[SYNTAX_ERROR] %s requires %d arguments, %d provided!",cmd,k,n);
      throw new ParameterException(fmtmsg);
    }
  }
  
  void repl(){
    try{
      for(;;){
        System.out.print("[RAM_64KB]> ");
        String instr=br.readLine();
        String[] parts=instr.split(" ");
        String cmd=parts[0];
        int pn=parts.length;
        try{
          if(cmd.equalsIgnoreCase("exit") || cmd.equalsIgnoreCase("quit") || cmd.equalsIgnoreCase("x") || cmd.equalsIgnoreCase("q")){
            break;
          }
          if(cmd.equalsIgnoreCase("r")){
            chkargs("r",pn,2);
            memrd(parts[1]);
          }else if(cmd.equalsIgnoreCase("w")){
            chkargs("w",pn,3);
            memwr(parts[1],parts[2]);
          }else{
            System.out.println("[ERR] unknown command!");
          }
        }catch(ParameterException pe){
          System.out.println(pe.getMessage());
          continue;
        }
      }
    }catch(IOException ioe){
      System.err.println("IO exception: "+ioe.getMessage());
    }
  }
  
  public static void main(String[] args){
    RAM64KBShell ramsh=new RAM64KBShell();
    ramsh.repl();
  }
}

