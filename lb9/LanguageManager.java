package lb9;
import java.util.Arrays;
import java.util.Comparator;
import java.text.Collator;
import java.util.Locale;
import java.util.regex.Pattern;

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

  public void sortAscendingByName() {
    Collator ukrainianCollator = Collator.getInstance(new Locale("uk", "UA"));
    Arrays.sort(languages, (l1, l2) -> ukrainianCollator.compare(l1.getName(), l2.getName()));
  }

  public void sortDescendingByName() {
    Collator ukrainianCollator = Collator.getInstance(new Locale("uk", "UA"));
    Arrays.sort(languages, (l1, l2) -> ukrainianCollator.compare(l2.getName(), l1.getName()));
  }

  public void printLanguagesByRegex(String regex) {
    Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
    boolean found = false;

    System.out.println("Результати пошуку за регулярним виразом:");
    for (Language lang : languages) {
      if (pattern.matcher(lang.getName()).find()) {
        System.out.println(lang);
        found = true;
      }
    }
    if (!found) {
      System.out.println("Не знайдено мов, що задовольняють умові регулярного виразу.");
    }
    System.out.println();
  }

  public void printMinMaxVocabularyInfo() {
    if (languages.length == 0) return;

    Language minLang = languages[0];
    Language maxLang = languages[0];

    for (Language lang : languages) {
      if (lang.getVocabularySize() < minLang.getVocabularySize()) {
        minLang = lang;
      }
      if (lang.getVocabularySize() > maxLang.getVocabularySize()) {
        maxLang = lang;
      }
    }
    System.out.printf("Найменшу кількість слів %d має мова %s\n", minLang.getVocabularySize(), minLang.getName());
    System.out.printf("Найбільшу кількість слів %d має мова %s\n\n", maxLang.getVocabularySize(), maxLang.getName());
  }
}