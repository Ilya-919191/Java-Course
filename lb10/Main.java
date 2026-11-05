package lb10;
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
        System.out.println("2. Завантажити із текстового файла");
        System.out.println("3. Завантажити із бінарного файла");
        System.out.println("4. Відсортувати та зберегти у бінарний файл");
        System.out.println("0. Вийти з програми");
        System.out.print("Оберіть дію (0-4): ");

        int choice = -1;
        if (scanner.hasNextInt()) {
          choice = scanner.nextInt();
        } else {
          scanner.next();
          continue;
        }
        scanner.nextLine();

        switch (choice) {
          case 1:
            manager.printAll();
            break;

          case 2: 
            System.out.print("Ім'я файлу для читання (наприклад, data.txt): ");
            manager.loadFromTextFile(scanner.nextLine()); 
            break;

          case 3: 
            System.out.print("Ім'я файлу для читання (наприклад, data.dat): ");
            manager.loadFromBinaryFile(scanner.nextLine()); 
            break;

          case 4: 
            System.out.print("Ім'я файлу для збереження (наприклад, data.dat): ");
            manager.saveToBinaryFile(scanner.nextLine()); 
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