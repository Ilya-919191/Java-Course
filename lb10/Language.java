package lb10;
import java.io.Serializable;

class Language implements Serializable {
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
  public double getSpeakersMillions() { return speakersMillions; }

  @Override
  public String toString() {
    return String.format("Мова: %-12s | Сім'я: %-15s | Слів: %-7d | Носіїв: %.1f млн", 
      name, languageFamily, vocabularySize, speakersMillions);
  }
}