package ed;

class U{

  static void d(String msg){
    System.out.printf("[INFO] %s\n",msg);
  }
  
  static void d(String ctx,String msg){
    System.out.printf("[%s] %s\n",ctx,msg);
  }
  
  static void d(String ctx,String fmsg,String... args){
    d(ctx,sf(fmsg,args));
  }
  
  static String sf(String fmt,String... args){
    StringBuffer sb=new StringBuffer(fmt);
    for(int i=0;i<sb.length();i++){
      char x=sb.charAt(i);
      if(x=='{'){
        int j;
        for(j=i+1;j<sb.length();j++){
          char y=sb.charAt(j);
          if(y=='}'){break;}
          else if(y=='{'){
            throw new RuntimeException("parse error: expr nesting not yet supported!");
          }
        }
        if(j>=sb.length()){
          throw new RuntimeException("parse error: '{' not closed!");
        }
        String ax=sb.substring(i+1,j).trim();
        if("".equals(ax)){
          throw new RuntimeException("parse error: missing param index!");
        }
        int ix=Integer.parseInt(ax);
        sb.replace(i,j+1,args[ix]);
        i=j;
      }
    }
    return sb.toString();
  }
  
}

