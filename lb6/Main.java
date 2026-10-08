package lb6;

public class Main {
  public static void main(String[] args) {
    System.out.println("Базовий клас Vehicle");
    Vehicle vehicle = new Vehicle();
    vehicle.displayInfo();
    vehicle.accelerate(20);

    System.out.println("\nКлас Car (за замовченням)");
    Car defaultCar = new Car();
    defaultCar.displayInfo();
    defaultCar.drive();

    System.out.println("\nКлас Car");
    Engine v8Engine = new Engine("V8 Бітурбо", 500);
    Car sportCar = new Car("BMW", 290, "Купе", v8Engine);
    sportCar.displayInfo();
    sportCar.drive();
    sportCar.accelerate(50);

    System.out.println("\nКлас Car (побудований у Vehicle)");
    Vehicle car = new Car();
    car.displayInfo();
  }
}