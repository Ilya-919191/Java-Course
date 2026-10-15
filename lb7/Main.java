package lb7;

public class Main {
  public static void main(String[] args) {
    Vehicle[] vehicles = new Vehicle[2];
    vehicles[0] = new Car("BMW", 290, "Купе", new Engine("V8 Бітурбо", 500));
    vehicles[1] = new ElectricCar("Tesla Model S", 250, "Ліфтбек", 100);

    for (Vehicle v : vehicles) {
      v.displayInfo();
      v.accelerate(30);
      v.stop();
      System.out.println();
    }

    ElectricCar tesla = new ElectricCar("Tesla Model Y", 217, "Кросовер", 75);
    
    Maintainable maintainableService = tesla;
    maintainableService.service();
  }
}