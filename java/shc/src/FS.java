import java.io.*;


class FS {

  static String cat(File f) {
    StringBuffer sb=new StringBuffer();
    try {
      BufferedReader br=new BufferedReader(new FileReader(f));
      String tmp;
      while ((tmp=br.readLine())!=null) {
        sb.append(String.format("%s\n", tmp));
      }
    }
    catch (IOException ioe) {
      sb.append(String.format("[ERROR] %s\n", ioe.getMessage()));
    }
    finally {
      return sb.toString();
    }
  }

  static String ls(File f) {
    File[] subnodes=f.listFiles();
    StringBuffer sb=new StringBuffer();
    for (File sd:subnodes) {
      sb.append(String.format("%s  %s\n", sd.isDirectory()?"D":"-", sd.getName()));
    }
    return sb.toString();
  }

  static String touch(File f) {
    StringBuffer sb=new StringBuffer();
    try {
      f.createNewFile();
    }
    catch (IOException ioe) {
      sb.append(String.format("[ERROR] %s\n", ioe.getMessage()));
    }
    finally {
      return sb.toString();
    }
  }
}
