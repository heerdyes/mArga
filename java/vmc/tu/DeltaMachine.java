public class DeltaMachine {
  private Tape tape;
  private MachineHead machineHead;
  private Processor processor;

  public DeltaMachine(Tape tape, MachineHead machineHead, Processor processor) {
    super();
    this.tape = tape;
    this.machineHead = machineHead;
    this.processor = processor;
  }

  public Tape getTape() {
    return tape;
  }

  public void setTape(Tape tape) {
    this.tape = tape;
  }

  public MachineHead getMachineHead() {
    return machineHead;
  }

  public void setMachineHead(MachineHead machineHead) {
    this.machineHead = machineHead;
  }

  public Processor getProcessor() {
    return processor;
  }

  public void setProcessor(Processor processor) {
    this.processor = processor;
  }

  public void start() {
    for (;;) {
      processor.run();
    }
  }

}
