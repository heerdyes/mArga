import java.io.*;
public class num2mem {
  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.out.println("usage: java num2mem <file.num> <file.mem>");
      throw new Exception("[FATAL] command line args not properly specified!");
    }
    System.out.println("num2mem!");
    File numfile = new File(args[0]);
    File memfile = new File(args[1]);
    if (!numfile.exists() && memfile.exists()) {
      throw new Exception("[FATAL] numfile does not exist whereas memfile exists.");
    }
    translate_num2mem(new File(args[0]), new File(args[1]));
  }
  
  static String pre_process(String ln){
    int pos=ln.indexOf("#");
    String rs=ln;
    if(pos!=-1){
      rs=ln.substring(0,pos);
    }
    return snip_spaces(rs);
  }

  static void translate_num2mem(File numfile, File memfile) throws Exception {
    // process the numfile
    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(numfile)));
    String line = br.readLine();
    if (line == null) {
      br.close();
      throw new Exception("[FATAL] numfile is empty.");
    }
    // here enters the memory array that shall be pickled
    int[] memory = new int[Integer.parseInt(line)];  // first line of numfile is the memory size
    System.out.println("[info] zeroing out the memory array.");
    for (int j = 0; j < memory.length; j++) {
      memory[j] = 0;
    }
    System.out.println("[info] populating the numcode listing in the memory array.");
    int i = 0;
    for (; ; i++) {
      line = br.readLine();
      if(line == null) break;
      // pre-process the line
      line = pre_process(line);
      // now split
      String[] parts = line.split(":");
      int location = Integer.parseInt(parts[0]);
      int data = Integer.parseInt(parts[1]);
      memory[location] = data;
    }
    if (i == 0) {
      br.close();
      throw new Exception("[FATAL] numfile has no number program listing.");
    }
    br.close();

    // now for the memfile
    memfile.createNewFile();
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(memfile));
    oos.writeObject(memory);
    oos.close();
  }
  
  static String snip_spaces(String line) {
    return line.replace(" ", "");
  }
}
