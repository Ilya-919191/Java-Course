package sr2;
import javax.swing.*;
import java.awt.*;
import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Lab9Panel extends JPanel {
  private static class Language {
    String name;
    String family;
    int vocabSize;
    double speakers;

    Language(String name, String family, int vocabSize, double speakers) {
      this.name = name;
      this.family = family;
      this.vocabSize = vocabSize;
      this.speakers = speakers;
    }

    @Override
    public String toString() {
      return String.format("Мова: %-14s   Сім'я: %-16s   Слів: %-7d   Носіїв: %.1f млн",
          name, family, vocabSize, speakers);
    }
  }

  private final Language[] languages = {
    new Language("Англійська", "Індоєвропейська", 470000, 1452.0),
    new Language("Мандаринська", "Сино-тибетська", 350000, 1118.0),
    new Language("Хінді", "Індоєвропейська", 150000, 602.2),
    new Language("Іспанська", "Індоєвропейська", 100000, 548.3),
    new Language("Французька", "Індоєвропейська", 130000, 280.0),
    new Language("Арабська", "Афразійська", 400000, 274.0),
    new Language("Бенгальська", "Індоєвропейська", 110000, 272.7),
    new Language("Португальська", "Індоєвропейська", 150000, 257.7),
    new Language("Японська", "Японська", 500000, 125.4),
    new Language("Українська", "Індоєвропейська", 253000, 40.0)
  };

  private final JTextArea resultArea;

  public Lab9Panel() {
    setLayout(new BorderLayout(10, 10));
    setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel header = new JLabel("Лабораторна робота №9: Рядки");
    header.setFont(new Font("Arial", Font.BOLD, 16));
    header.setAlignmentX(Component.LEFT_ALIGNMENT);

    // Кнопки сортування та аналізу
    JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 5));
    sortPanel.setBorder(BorderFactory.createTitledBorder("Сортування та статистика"));
    sortPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JButton btnShowAll = new JButton("Усі");
    JButton btnSpeakersAsc = new JButton("Носії ↑");
    JButton btnSpeakersDesc = new JButton("Носії ↓");
    JButton btnNameAsc = new JButton("Назва А–Я");
    JButton btnNameDesc = new JButton("Назва Я–А");
    JButton btnMinMax = new JButton("Min / Max слів");

    sortPanel.add(btnShowAll);
    sortPanel.add(btnSpeakersAsc);
    sortPanel.add(btnSpeakersDesc);
    sortPanel.add(btnNameAsc);
    sortPanel.add(btnNameDesc);
    sortPanel.add(btnMinMax);

    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
    searchPanel.setBorder(BorderFactory.createTitledBorder("Пошук та фільтрація"));
    searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JTextField speakersField = new JTextField("300", 5);
    JButton btnFilterSpeakers = new JButton("Носіїв > X");
    JTextField regexField = new JTextField("ська$", 10);
    JButton btnRegex = new JButton("Пошук за Regex");

    searchPanel.add(new JLabel("Мін. носіїв:"));
    searchPanel.add(speakersField);
    searchPanel.add(btnFilterSpeakers);
    searchPanel.add(new JLabel("   Regex (назва):"));
    searchPanel.add(regexField);
    searchPanel.add(btnRegex);

    JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
    topContainer.add(header);
    topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
    topContainer.add(sortPanel);
    topContainer.add(Box.createRigidArea(new Dimension(0, 5)));
    topContainer.add(searchPanel);

    resultArea = new JTextArea();
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    resultArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Результат"));

    Collator ukCollator = Collator.getInstance(Locale.forLanguageTag("uk-UA"));

    btnShowAll.addActionListener(e -> printAll());

    btnSpeakersAsc.addActionListener(e -> {
      Arrays.sort(languages, Comparator.comparingDouble(l -> l.speakers));
      printAll();
    });

    btnSpeakersDesc.addActionListener(e -> {
      Arrays.sort(languages, (l1, l2) -> Double.compare(l2.speakers, l1.speakers));
      printAll();
    });

    btnNameAsc.addActionListener(e -> {
      Arrays.sort(languages, (l1, l2) -> ukCollator.compare(l1.name, l2.name));
      printAll();
    });

    btnNameDesc.addActionListener(e -> {
      Arrays.sort(languages, (l1, l2) -> ukCollator.compare(l2.name, l1.name));
      printAll();
    });

    btnMinMax.addActionListener(e -> {
      Language minLang = languages[0];
      Language maxLang = languages[0];
      for (Language lang : languages) {
        if (lang.vocabSize < minLang.vocabSize) minLang = lang;
        if (lang.vocabSize > maxLang.vocabSize) maxLang = lang;
      }
      resultArea.setText(String.format(
        "Найменшу кількість слів (%d) має мова: %s\n" +
        "Найбільшу кількість слів (%d) має мова: %s",
        minLang.vocabSize, minLang.name, maxLang.vocabSize, maxLang.name
      ));
    });

    btnFilterSpeakers.addActionListener(e -> {
      try {
        double min = Double.parseDouble(speakersField.getText().trim().replace(',', '.'));
        StringBuilder sb = new StringBuilder("Мови з кількістю носіїв > " + min + " млн:\n\n");
        boolean found = false;
        for (Language lang : languages) {
          if (lang.speakers > min) {
            sb.append(lang).append("\n");
            found = true;
          }
        }
        if (!found) sb.append("Таких мов не знайдено.");
        resultArea.setText(sb.toString());
      } catch (NumberFormatException ex) {
        resultArea.setText("Помилка: введіть коректне число.");
      }
    });

    btnRegex.addActionListener(e -> {
      try {
        String regex = regexField.getText();
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        StringBuilder sb = new StringBuilder("Результати пошуку за регулярним виразом '" + regex + "':\n\n");
        boolean found = false;
        for (Language lang : languages) {
          if (pattern.matcher(lang.name).find()) {
            sb.append(lang).append("\n");
            found = true;
          }
        }
        if (!found) sb.append("Не знайдено мов, що задовольняють умові регулярного виразу.");
        resultArea.setText(sb.toString());
      } catch (PatternSyntaxException ex) {
        resultArea.setText("Помилка синтаксису регулярного виразу: " + ex.getDescription());
      }
    });

    add(topContainer, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    printAll();
  }

  private void printAll() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < languages.length; i++) {
      sb.append(String.format("[%d]  %s\n", i, languages[i]));
    }
    resultArea.setText(sb.toString());
  }
}