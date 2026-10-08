package lb6;

class Engine {
  private String type;
  private int horsepower;

  public Engine() {
    this("Бензиновий", 150);
  }

  public Engine(String type, int horsepower) {
    this.type = type;
    this.horsepower = horsepower;
  }

  public String getType() {
    return type;
  }

  public int getHorsepower() {
    return horsepower;
  }

  public void start() {
    System.out.println("Двигун (" + type + ", " + horsepower + " к.с.) заведено.");
  }
}