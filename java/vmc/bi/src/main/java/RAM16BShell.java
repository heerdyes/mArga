import java.io.*;

public class RAM16BShell implements ControlPanel{
  RAM16x8 ram;
  BufferedReader br;

  RAM16BShell(){
    ram=new RAM16x8();
    InputStreamReader isr=new InputStreamReader(System.in);
    br=new BufferedReader(isr);
  }
  
  public void feeddata(String addr,String w,String di){
    ram.feed(addr,w,di);
  }
  
  public String collectdata(){
    return (ram.do7?"1":"0")+(ram.do6?"1":"0")+(ram.do5?"1":"0")+(ram.do4?"1":"0")
          +(ram.do3?"1":"0")+(ram.do2?"1":"0")+(ram.do1?"1":"0")+(ram.do0?"1":"0");
  }
  
  void repl() {
    try{
      for(;;){
        System.out.print("[RAM_16B]> ");
        String instr=br.readLine();
        String[] parts=instr.split(" ");
        String cmd=parts[0];
        if(cmd.equalsIgnoreCase("exit") || cmd.equalsIgnoreCase("quit") || cmd.equalsIgnoreCase("x") || cmd.equalsIgnoreCase("q")){
          break;
        }
        if(cmd.equalsIgnoreCase("r")){
          String addr=parts[1];
          feeddata(addr,"0","00000000");
          System.out.println(collectdata());
        }else if(cmd.equalsIgnoreCase("w")){
          String addr=parts[1];
          String dat=parts[2];
          feeddata(addr,"1",dat);
          System.out.println(collectdata());
        }else if(cmd.equalsIgnoreCase("d")){
          ram.display();
        }else{
          System.out.println("[ERR] unknown command!");
        }
      }
    }catch(IOException ioe){
      System.err.println("IO exception: "+ioe.getMessage());
    }
  }
  
  public static void main(String[] args){
    RAM16BShell ramsh=new RAM16BShell();
    ramsh.repl();
  }
}

