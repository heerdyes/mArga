public class Processor {
  // internal register
  private String id;
  private Cell dataRegister;
  private Cell locationRegister;
  private MachineHead machineHead;

  public Processor(String id, MachineHead machineHead) {
    this.id = id;
    this.dataRegister = new Cell('\0');
    this.locationRegister = new Cell('\u0391');
    this.machineHead = machineHead;
  }

  private void fetch() {
    dataRegister.setData(machineHead.read().getData());
  }

  private void decode() {
    switch (dataRegister.getData()) {
    case '\u0391':
      // alpha
      break;
    }
  }

  private void execute() {

  }

  public void run() {
    for (;;) {
      fetch();
      decode();
      execute();
    }
  }
}
