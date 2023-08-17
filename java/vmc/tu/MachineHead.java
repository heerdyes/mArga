public class MachineHead {
  private String id;
  private Tape tape;
  private int position;
  
  public MachineHead(String id, Tape tape, int position) {
    super();
    this.id = id;
    this.tape = tape;
    this.position = position;
  }

  public Cell read() {
    // read from location on tape
    return tape.getMemory()[position];
  }
  
  public void write(Cell datacell) {
    // write to location on tape
    tape.writeCell(position, datacell);
  }

  public String getId() {
    return id;
  }

  public Tape getTape() {
    return tape;
  }

  public void setTape(Tape tape) {
    this.tape = tape;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }
  
}
