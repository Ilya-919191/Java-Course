package lb11;

public class Language {
	private int id;
	private String name;
	private String languageFamily;
	private int vocabularySize;
	private double speakersMillions;

	public Language(int id, String name, String languageFamily, int vocabularySize, double speakersMillions) {
		this.id = id;
		this.name = name;
		this.languageFamily = languageFamily;
		this.vocabularySize = vocabularySize;
		this.speakersMillions = speakersMillions;
	}

	public Language(String name, String languageFamily, int vocabularySize, double speakersMillions) {
		this(0, name, languageFamily, vocabularySize, speakersMillions);
	}

	public int getId() { return id; }
	public String getName() { return name; }
	public String getLanguageFamily() { return languageFamily; }
	public int getVocabularySize() { return vocabularySize; }
	public double getSpeakersMillions() { return speakersMillions; }

	@Override
  public String toString() {
    return String.format("ID: %-3d   Мова: %-14s   Сім'я: %-16s   Слів: %-7d   Носіїв: %.1f млн",
        id, name, languageFamily, vocabularySize, speakersMillions);
  }
}
