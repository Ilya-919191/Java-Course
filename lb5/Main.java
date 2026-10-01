import lb5.SomeClass;

public class Main {
  public static void main(String[] args) {
    SomeClass obj1 = new SomeClass();
    System.out.println("\tРезультати об'єкта за замовченням\n");
    obj1.printResults();

    SomeClass obj2 = new SomeClass(4.0, 3.0, 13);
    System.out.println("\tРезультати для об'єкта із полями: a = 4, b = 3, N = 13\n");
    obj2.printResults();
  }
}