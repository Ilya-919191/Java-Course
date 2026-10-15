package lb7;

public class ElectricCar extends Car implements Maintainable {
  private int batteryCapacity;

  public ElectricCar(String brand, int maxSpeed, String bodyType, int batteryCapacity) {
    super(brand, maxSpeed, bodyType, new Engine("Електричний", 300));
    this.batteryCapacity = batteryCapacity;
  }

  @Override
  public void displayInfo() {
    super.displayInfo();
    System.out.println("Ємність батареї: " + batteryCapacity + " кВт·год");
  }

  @Override
  public void stop() {
    System.out.println("Електромобіль " + brand + " зупиняється.");
  }

  @Override
  public void service() {
    System.out.println("Сервіс електромобіля " + brand + ": Діагностика батареї (" + batteryCapacity + " кВт·год).");
  }
}