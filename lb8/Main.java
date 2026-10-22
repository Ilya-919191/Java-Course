package lb8;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
  public static void main(String[] args) {
    Language[] initialLanguages = {
      new Language("Англійська", "Індоєвропейська", 470000, 1452.0),
      new Language("Мандаринська", "Сино-тибетська", 350000, 1118.0),
      new Language("Хінді", "Індоєвропейська", 150000, 602.2),
      new Language("Іспанська", "Індоєвропейська", 100000, 548.3),
      new Language("Французька", "Індоєвропейська", 130000, 280.0),
      new Language("Арабська", "Афразійська", 400000, 274.0),
      new Language("Бенгальська", "Індоєвропейська", 110000, 272.7),
      new Language("Португальська","Індоєвропейська", 150000, 257.7),
      new Language("Японська", "Японська", 500000, 125.4),
      new Language("Українська", "Індоєвропейська", 253000, 40.0)
    };

    LanguageManager manager = new LanguageManager(initialLanguages);
    Scanner scanner = new Scanner(System.in);
    boolean running = true;

    try {
      while (running) {
        System.out.println("1. Вивести масив на екран");
        System.out.println("2. Модифікувати елемент масиву");
        System.out.println("3. Знайти мови за кількістю носіїв");
        System.out.println("4. Сортувати за зростанням кількості носіїв");
        System.out.println("5. Сортувати за спаданням кількості носіїв");
        System.out.println("0. Вийти з програми");
        System.out.print("Оберіть дію (0-5): ");

        int choice = -1;
        if (scanner.hasNextInt()) {
          choice = scanner.nextInt();
        } else {
          scanner.next();
        }

        switch (choice) {
          case 1:
            manager.printAll();
            break;

          case 2:
            System.out.print("Введіть індекс мови для зміни (0-9): ");
            int index = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Введіть нову назву: ");
            String name = scanner.nextLine();
            
            System.out.print("Введіть нову мовну сім'ю: ");
            String family = scanner.nextLine();
            
            System.out.print("Введіть кількість слів: ");
            int vocab = scanner.nextInt();
            
            System.out.print("Введіть кількість носіїв (млн): ");
            double speakers = scanner.nextDouble();
            
            manager.modifyLanguage(index, name, family, vocab, speakers);
            break;

          case 3:
            System.out.print("Введіть мінімальну кількість носіїв (млн): ");
            double minSpeakers = scanner.nextDouble();
            manager.printLanguagesWithSpeakersMoreThan(minSpeakers);
            break;

          case 4:
            manager.sortAscendingBySpeakers();
            manager.printAll();
            break;

          case 5:
            manager.sortDescendingBySpeakers();
            manager.printAll();
            break;

          case 0:
            running = false;
            break;

          default:
            System.out.println("Помилка: Невірний вибір. Спробуйте ще раз.\n");
        }
      }
    } catch (InputMismatchException e) {
      System.out.println("Помилка: потрібно вводити числовий формат.");
    } finally {
      scanner.close();
    }
  }
}