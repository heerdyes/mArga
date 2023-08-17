import java.io.*;
public class mem2num {
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      throw new Exception("[FATAL] command line args not properly specified!");
    }
    System.out.println("mem2num!");
    File memfile = new File(args[0]);
    File numfile = new File(args[1]);
    if (!memfile.exists()) {
      throw new Exception("[FATAL] memfile does not exist!");
    }
    translate_mem2num(memfile, numfile);
  }
  
  static void translate_mem2num(File memfile, File numfile) throws Exception {
    // decode numfile from the memfile
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(memfile));
    int[] memory = (int[]) ois.readObject();
    ois.close();
    if(memory == null) {
      throw new Exception("null memory. memfile possibly corrupted!");
    }
    // if all goes well...
    PrintWriter pw = new PrintWriter(new FileOutputStream(numfile));
    pw.println(memory.length);
    for(int i=0;i<memory.length;i++) {
      pw.printf("%3d:%d\n",i,memory[i]);
    }
    pw.flush();
    pw.close();
  }
}
