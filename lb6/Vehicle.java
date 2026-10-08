package lb6;

class Vehicle {
  protected String brand;
  protected int maxSpeed;

  public Vehicle() {
    this("Невідомий бренд", 120);
  }

  public Vehicle(String brand, int maxSpeed) {
    this.brand = brand;
    this.maxSpeed = maxSpeed;
  }

  public void displayInfo() {
    System.out.println("Транспортний засіб: " + brand + ", Макс. швидкість: " + maxSpeed + " км/год");
  }

  public void accelerate(int speedIncrement) {
    System.out.println("Транспортний засіб прискорюється на " + speedIncrement + " км/год.");
  }
}