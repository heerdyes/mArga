import java.io.*;

// fixme: this is a monolithic piece of code
public class intmc {
  // fixme: use a class for the integer machine: using OOP techniques
  static int[] memory;
  static int INST_SIZE = 100;
  static int AC = 0;    // accumulator
  static int IR = 0;    // instruction register
  static int PC = 0;    // program counter
  static int DR = 0;    // data register
  static int LR = 0;    // location register
  static int SC = 0;    // shift counter
  static int INST = 0;
  static int DATA = 0;
  static int ZFLAG = 1;
  static volatile boolean PCUF = false;    // PC updated flag
  static volatile boolean halted = false;
  static String logFileName = "intmc.log";
  static PrintWriter d;
  
  static void err(String msg){
    System.err.println("----------------");
    System.err.println("[ERROR] "+msg);
    System.err.println("----------------");
  }
  
  static void info(String msg){
    if(d==null){
      System.err.println("log file not initialized! writing to console");
      System.out.println("[INFO] "+msg);
      return;
    }
    d.println("[INFO] "+msg);
    d.flush();
  }
  
  static void initlogging(){
    try {
      d = new PrintWriter(new FileOutputStream(new File(logFileName)));
    } catch(IOException ioe) {
      err(ioe.getMessage());
    }
    if(d==null) {
      err("filehandler not initialized. logging might not work!");
    }
  }
  
  static void endlogging(){
    if(d!=null){
      d.close();
      d=null;
    }
  }

  public static void main(String[] args) throws Exception {
    initlogging();
    info("load memory from serialized array.");
    if(args.length != 1) {
      throw new Exception("[FATAL] no memfile specified as argument.");
    }
    File memfile = new File(args[0]);
    File memfileimg = new File(memfile.getName() + "dmp");
    if (memfile.exists()) {
      load_memory(memfile);
      info("memfile loaded.");
      info("going to start machine.");
      run_machine();
      dump_memory(memfileimg);
    } else {
      bye("FATAL: memfile does not exist.");
    }
    endlogging();
  }

  // to be used inside switch case for instructions that alter PC
  static void setPC(int loc) {
    PC = loc;
    PCUF = true;
  }

  static void bye(String exc) {
    endlogging();
    throw new RuntimeException(exc);
  }

  static void run_machine() throws IOException, Exception {
    for (int i=0; !halted; i++) {
      fetch();
      decode();
      process();
      shift();
    }
  }

  static void fetch() {
    IR = memory[PC];
    SC = 1;
  }

  static void decode() {
    INST = IR % INST_SIZE;
    DATA = IR / INST_SIZE;
  }

  // fixme: possibly use a map to store <instruction, action> pair
  static void process() throws IOException, Exception {
    switch(INST) {
      case 0:
        info("EXIT");
        halted = true;
        break;
      case 10:
        info("LOAD");
        AC = DATA;
        break;
      case 11:
        info("LOADX");
        AC = memory[DATA];
        break;
      case 12:
        info("AC <- DR");
        AC = DR;
        break;
      case 13:
        info("AC <- LR");
        AC = LR;
        break;
      case 20:
        info("STORE");
        memory[DATA] = AC;
        break;
      case 21:
        info("ISTOREX");
        memory[LR] = AC;
        break;
      case 22:
        info("ISTOREDR");
        memory[LR] = DR;
        break;
      case 23:
        info("DR <- AC");
        DR = AC;
        break;
      case 24:
        info("LR <- AC");
        LR = AC;
        break;
      case 30:
        info("ADD");
        AC = AC + DATA;
        break;
      case 31:
        info("ADDX");
        AC = AC + memory[DATA];
        break;
      case 33:
        info("INC");
        AC++;
        break;
      case 34:
        info("INCX");
        memory[DATA]++;
        break;
      case 40:
        info("SUB");
        AC = AC - DATA;
        break;
      case 41:
        info("SUBI");
        AC = AC - memory[DATA];
        break;
      case 42:
        info("CMP");
        ZFLAG = AC - DATA;
        break;
      case 43:
        info("CMPX");
        ZFLAG = AC - memory[DATA];
        break;
      case 44:
        info("DEC");
        AC--;
        break;
      case 45:
        info("DECX");
        memory[DATA]--;
        break;
      case 50:
        info("MUL");
        AC = AC * DATA;
        break;
      case 51:
        info("MULX");
        AC = AC * memory[DATA];
        break;
      case 60:
        info("DIV");
        AC = AC / DATA;
        break;
      case 61:
        info("DIVX");
        AC = AC / memory[DATA];
        break;
      case 70:
        info("MOD");
        AC = AC % DATA;
        break;
      case 71:
        info("MODX");
        AC = AC % memory[DATA];
        break;
      case 9:
        info("GOTO");
        setPC(DATA);
        break;
      case 91:
        info("JZ");
        if(ZFLAG == 0) {
          setPC(DATA);
        }
        break;
      case 92:
        info("JNZ");
        if(ZFLAG != 0) {
          setPC(DATA);
        }
        break;
      case 93:
        info("JACZ");
        if(AC == 0) {
          setPC(DATA);
        }
        break;
      case 94:
        info("JACNZ");
        if(AC != 0) {
          setPC(DATA);
        }
        break;
      case 80:
        info("DISPREG");
        System.out.println(",------------------------");
        System.out.println("|  AC = " + AC);
        System.out.println("|  PC = " + PC);
        System.out.println("|  SC = " + SC);
        System.out.println("|  IR = " + IR);
        System.out.println("|  INST = " + INST);
        System.out.println("|  DATA = " + DATA);
        System.out.println("|  ZFLAG = " + ZFLAG);
        System.out.println("`------------------------");
        break;
      case 81:
        info("PRINT");
        System.out.println("["+DATA+"]: "+memory[DATA]);
        break;
      case 82:
        info("PRINTAC");
        System.out.println("[AC]: "+AC);
        break;
      default:
        info("NULL");
        break;
    }
  }

  static void shift() {
    if(PCUF) {
      PCUF = false;
    }
    else {
      PC += SC; // do not use setPC(n) here
    }
  }

  // fixme: memory warrants the need to be a separate object
  static void load_memory(File file) throws Exception {
    info("load_memory: loading memory from serialized array file.");
    ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
    memory = (int[]) ois.readObject();
    ois.close();
    if(memory == null) {
      bye("null memory exception. memfile is possibly corrupted.");
    }
  }
  
  static void dump_memory() {
    for(int i=0;i<memory.length;i++) {
      System.out.printf("%3d:%d\n",i,memory[i]);
    }
  }
  
  static void dump_memory(File file) throws Exception {
    info("dump_memory: serializing memory into array file.");
    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
    oos.writeObject(memory);
    oos.close();
  }
}

