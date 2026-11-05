package lb10;
import java.io.*;
import java.util.*;

class LanguageManager {
  private Language[] languages;

  public LanguageManager(Language[] languages) {
    this.languages = languages;
  }

  public void printAll() {
    for (int i = 0; i < languages.length; i++) {
      System.out.println("[" + i + "] " + languages[i]);
    }
    System.out.println();
  }

  public void sortAscendingBySpeakers() {
    Arrays.sort(languages, Comparator.comparingDouble(Language::getSpeakersMillions));
  }

  public void loadFromTextFile(String filename) {
    List<Language> list = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] p = line.split(",");
        if (p.length >= 4) {
          list.add(new Language(p[0].trim(), p[1].trim(), Integer.parseInt(p[2].trim()), Double.parseDouble(p[3].trim())));
        }
      }
      languages = list.toArray(new Language[0]);
    } catch (IOException e) {
      System.out.println("Помилка читання: " + e.getMessage() + "\n");
    }
  }

  public void loadFromBinaryFile(String filename) {
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
      languages = (Language[]) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Помилка читання: " + e.getMessage() + "\n");
    }
  }

public void saveToBinaryFile(String filename) {
    sortAscendingBySpeakers();
    
    File file = new File(filename);
    File parent = file.getParentFile();
    if (parent != null && !parent.exists()) {
      parent.mkdirs();
    }

    try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
      stream.writeObject(languages);
    } catch (IOException e) {
      System.out.println("Помилка запису: " + e.getMessage() + "\n");
    }
  }
}