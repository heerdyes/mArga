public class Tape {
  private String id;
  private int size;
  private Cell[] memory;
  
  public Tape(String id, int size, Cell[] memory) {
    super();
    this.id = id;
    this.size = size;
    this.memory = memory;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }

  public Cell[] getMemory() {
    return memory;
  }

  public void setMemory(Cell[] memory) {
    this.memory = memory;
  }
  
  public Cell readCell(int location) {
    return memory[location];
  }
  
  public void writeCell(int location, Cell cell) {
    memory[location].setData(cell.getData());
  }
}
