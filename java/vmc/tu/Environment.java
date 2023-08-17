public class Environment {

  public static void main(String[] args) {
    Tape t = createTape(0, 100);
    MachineHead mh = createMachineHead(0, t);
    Processor cpu = createProcessor(0, mh);
    DeltaMachine dm = new DeltaMachine(t, mh, cpu);
  }

  private static Processor createProcessor(int n, MachineHead mh) {
    return new Processor("cpu" + n, mh);
  }

  private static MachineHead createMachineHead(int n, Tape t) {
    return new MachineHead("mh" + n, t, 0);
  }

  private static Tape createTape(int n, int size) {
    Cell[] line = new Cell[size];
    // wipe with 03A9 -> omega
    for (int i = 0; i < size; i++) {
      line[i] = new Cell((char) 0x03A9);
    }
    return new Tape("tape" + n, size, new Cell[size]);
  }

}
