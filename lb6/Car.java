package lb6;

class Car extends Vehicle {
  private String bodyType;
  private Engine engine;

  public Car() {
    super();
    this.bodyType = "Седан";
    this.engine = new Engine();
  }

  public Car(String brand, int maxSpeed, String bodyType, Engine engine) {
    super(brand, maxSpeed);
    this.bodyType = bodyType;
    this.engine = engine;
  }

  @Override
  public void displayInfo() {
    System.out.println("Автомобіль: " + brand + " (" + bodyType + "), Макс. швидкість: " + maxSpeed + " км/год");
    System.out.println("Характеристики двигуна: " + engine.getType() + ", " + engine.getHorsepower() + " к.с.");
  }

  @Override
  public void accelerate(int speedIncrement) {
    System.out.println("Автомобіль прискорюється на " + speedIncrement + " км/год.");
  }

  public void drive() {
    engine.start();
    System.out.println("Автомобіль " + brand + " вирушив у дорогу.");
  }
}