package lb11;
import java.util.List;
import java.util.Scanner;

public class Main {
private static final Scanner scanner = new Scanner(System.in);

  public static void main(String[] args) {
    DatabaseManager db = new DatabaseManager();
    boolean running = true;

    try {
			while (running) {
        System.out.println("\n1. Створити БД та таблицю за схемою");
        System.out.println("2. Переглянути всі записи БД");
        System.out.println("3. Додати новий запис");
        System.out.println("4. Вибірка мов з кількістю носіїв більшою за X млн");
        System.out.println("5. Редагувати запис за ID");
        System.out.println("6. Видалити запис за ID");
        System.out.println("0. Вийти з програми");

        int choice = readInt("Оберіть дію (0-6): ");
        System.out.println();

        switch (choice) {
          case 1 -> db.initializeDatabase();
          case 2 -> printLanguages(db.getAllLanguages());
          case 3 -> handleAdd(db);
          case 4 -> handleFilter(db);
          case 5 -> handleUpdate(db);
          case 6 -> handleDelete(db);
          case 0 -> running = false;
          default -> System.out.println("Невірний вибір. Введіть число від 0 до 6.");
        }
      }
    } finally {
      scanner.close();
    }
  }

  private static void printLanguages(List<Language> list) {
    if (list.isEmpty()) {
      System.out.println("Записів не знайдено.");
      return;
    }
    for (Language lang : list) {
      System.out.println(lang);
    }
  }

  private static void handleAdd(DatabaseManager db) {
    Language lang = readLanguage(0);
    if (db.addLanguage(lang)) {
      System.out.println("Запис успішно додано.");
    }
  }

  private static void handleFilter(DatabaseManager db) {
    double minSpeakers = readDouble("Мінімальна кількість носіїв (млн, наприклад 300): ");
    List<Language> filtered = db.getLanguagesByMinSpeakers(minSpeakers);
    System.out.println("Мови, у яких кількість носіїв > " + minSpeakers + " млн:");
    printLanguages(filtered);
  }

  private static void handleUpdate(DatabaseManager db) {
    int id = readInt("Введіть ID запису для редагування: ");
    Language existing = db.getLanguageById(id);
    if (existing == null) {
      System.out.println("Запис із ID = " + id + " не знайдено.");
      return;
    }

    System.out.println("Поточні дані: " + existing);
    Language updated = readLanguage(id);
    if (db.updateLanguage(updated)) {
      System.out.println("Запис успішно оновлено.");
    }
  }

  private static void handleDelete(DatabaseManager db) {
    int id = readInt("Введіть ID запису для видалення: ");
    if (db.deleteLanguage(id)) {
      System.out.println("Запис із ID = " + id + " видалено.");
    } else {
      System.out.println("Запис із ID = " + id + " не знайдено.");
    }
  }

  private static Language readLanguage(int id) {
    String name = readString("Назва мови: ");
    String family = readString("Мовна сім'я: ");
    int vocab = readInt("Словниковий запас (кількість слів > 0): ");
    double speakers = readDouble("Кількість носіїв (млн >= 0): ");
    return new Language(id, name, family, vocab, speakers);
  }

  private static String readString(String prompt) {
    while (true) {
      System.out.print(prompt);
      String input = scanner.nextLine().trim();
      if (!input.isEmpty()) return input;
      System.out.println("Поле не може бути порожнім.");
    }
  }

  private static int readInt(String prompt) {
    while (true) {
      System.out.print(prompt);
      try {
        return Integer.parseInt(scanner.nextLine().trim());
      } catch (NumberFormatException e) {
        System.out.println("Помилка: введіть ціле число.");
      }
    }
  }

  private static double readDouble(String prompt) {
    while (true) {
      System.out.print(prompt);
      try {
        return Double.parseDouble(scanner.nextLine().trim().replace(',', '.'));
      } catch (NumberFormatException e) {
        System.out.println("Помилка: введіть число.");
      }
    }
  }
}