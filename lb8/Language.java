package lb8;
import java.util.Arrays;
import java.util.Comparator;

class Language {
  private String name;
  private String languageFamily;
  private int vocabularySize;
  private double speakersMillions;

  public Language(String name, String languageFamily, int vocabularySize, double speakersMillions) {
    this.name = name;
    this.languageFamily = languageFamily;
    this.vocabularySize = vocabularySize;
    this.speakersMillions = speakersMillions;
  }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }

  public String getLanguageFamily() { return languageFamily; }
  public void setLanguageFamily(String languageFamily) { this.languageFamily = languageFamily; }

  public int getVocabularySize() { return vocabularySize; }
  public void setVocabularySize(int vocabularySize) { this.vocabularySize = vocabularySize; }

  public double getSpeakersMillions() { return speakersMillions; }
  public void setSpeakersMillions(double speakersMillions) { this.speakersMillions = speakersMillions; }

  @Override
  public String toString() {
    return String.format("Мова: %-12s | Сім'я: %-15s | Слів: %-7d | Носіїв: %.1f млн", 
      name, languageFamily, vocabularySize, speakersMillions);
  }
}