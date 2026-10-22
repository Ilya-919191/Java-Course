package lb8;
import java.util.Arrays;
import java.util.Comparator;

class LanguageManager {
  private Language[] languages;

  public LanguageManager(Language[] languages) {
    this.languages = languages;
  }

  public void printAll() {
    for (int i = 0; i < languages.length; i++) {
      System.out.println("[" + i + "] " + languages[i].toString());
    }
    System.out.println();
  }

  public void modifyLanguage(int index, String newName, String newFamily, int newVocabSize, double newSpeakers) {
    if (index >= 0 && index < languages.length) {
      languages[index].setName(newName);
      languages[index].setLanguageFamily(newFamily);
      languages[index].setVocabularySize(newVocabSize);
      languages[index].setSpeakersMillions(newSpeakers);
    } else {
      System.out.println("Помилка: Невірний індекс!\n");
    }
  }

  public void printLanguagesWithSpeakersMoreThan(double minSpeakers) {
    boolean found = false;
    for (Language lang : languages) {
      if (lang.getSpeakersMillions() > minSpeakers) {
        System.out.println(lang);
        found = true;
      }
    }
    if (!found) System.out.println("Таких мов не знайдено.");
    System.out.println();
  }

  public void sortAscendingBySpeakers() {
    Arrays.sort(languages, Comparator.comparingDouble(Language::getSpeakersMillions));
  }

  public void sortDescendingBySpeakers() {
    Arrays.sort(languages, (l1, l2) -> Double.compare(l2.getSpeakersMillions(), l1.getSpeakersMillions()));
  }
}