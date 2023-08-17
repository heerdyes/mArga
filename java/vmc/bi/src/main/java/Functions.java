public class Functions{
  
  static String bitseq(boolean[] x){
    StringBuffer sb=new StringBuffer();
    for(int i=0;i<x.length;i++){
      sb.append(x[i]?"1":"0");
    }
    return sb.toString();
  }
  
}
