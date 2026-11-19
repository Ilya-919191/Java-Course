package sr2;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class Lab8Panel extends JPanel {
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

  public Lab8Panel() {
    setLayout(new BorderLayout(10, 10));
    setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel header = new JLabel("Лабораторна робота №8: Масиви");
    header.setFont(new Font("Arial", Font.BOLD, 16));
    header.setAlignmentX(Component.LEFT_ALIGNMENT);

    // Панель дій та фільтрації
    JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
    actionPanel.setBorder(BorderFactory.createTitledBorder("Перегляд, сортування та пошук"));
    actionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JButton btnShowAll = new JButton("Вивести масив");
    JButton btnSortAsc = new JButton("Носії за зростанням");
    JButton btnSortDesc = new JButton("Носії за спаданням");
    JTextField minSpeakersField = new JTextField("300", 6);
    JButton btnFilter = new JButton("Носіїв > X млн");

    actionPanel.add(btnShowAll);
    actionPanel.add(btnSortAsc);
    actionPanel.add(btnSortDesc);
    actionPanel.add(new JLabel("Мін. носіїв:"));
    actionPanel.add(minSpeakersField);
    actionPanel.add(btnFilter);

    // Панель модифікації елемента
    JPanel modifyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 5));
    modifyPanel.setBorder(BorderFactory.createTitledBorder("Модифікувати елемент за індексом (0–9)"));
    modifyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JTextField indexField = new JTextField("0", 3);
    JTextField nameField = new JTextField(9);
    JTextField familyField = new JTextField(10);
    JTextField vocabField = new JTextField(6);
    JTextField speakersField = new JTextField(6);
    JButton btnModify = new JButton("Змінити");

    modifyPanel.add(new JLabel("Індекс:"));
    modifyPanel.add(indexField);
    modifyPanel.add(new JLabel("Назва:"));
    modifyPanel.add(nameField);
    modifyPanel.add(new JLabel("Сім'я:"));
    modifyPanel.add(familyField);
    modifyPanel.add(new JLabel("Слів:"));
    modifyPanel.add(vocabField);
    modifyPanel.add(new JLabel("Носіїв:"));
    modifyPanel.add(speakersField);
    modifyPanel.add(btnModify);

    JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
    topContainer.add(header);
    topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
    topContainer.add(actionPanel);
    topContainer.add(Box.createRigidArea(new Dimension(0, 5)));
    topContainer.add(modifyPanel);

    resultArea = new JTextArea();
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    resultArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Результат"));

    btnShowAll.addActionListener(e -> printAll());

    btnSortAsc.addActionListener(e -> {
      Arrays.sort(languages, Comparator.comparingDouble(l -> l.speakers));
      printAll();
    });

    btnSortDesc.addActionListener(e -> {
      Arrays.sort(languages, (l1, l2) -> Double.compare(l2.speakers, l1.speakers));
      printAll();
    });

    btnFilter.addActionListener(e -> {
      try {
        double min = Double.parseDouble(minSpeakersField.getText().trim().replace(',', '.'));
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
        resultArea.setText("Помилка: введіть коректне число у поле мінімальної кількості носіїв.");
      }
    });

    btnModify.addActionListener(e -> {
      try {
        int idx = Integer.parseInt(indexField.getText().trim());
        if (idx < 0 || idx >= languages.length) {
          resultArea.setText("Помилка: індекс має бути від 0 до " + (languages.length - 1) + ".");
          return;
        }
        String name = nameField.getText().trim();
        String family = familyField.getText().trim();
        if (name.isEmpty() || family.isEmpty()) {
          resultArea.setText("Помилка: назва та мовна сім'я не можуть бути порожніми.");
          return;
        }
        int vocab = Integer.parseInt(vocabField.getText().trim());
        double speakers = Double.parseDouble(speakersField.getText().trim().replace(',', '.'));

        languages[idx].name = name;
        languages[idx].family = family;
        languages[idx].vocabSize = vocab;
        languages[idx].speakers = speakers;
        printAll();
      } catch (NumberFormatException ex) {
        resultArea.setText("Помилка: перевірте правильність числових полів (індекс, слів, носіїв).");
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