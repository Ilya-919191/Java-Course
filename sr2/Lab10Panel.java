package sr2;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Lab10Panel extends JPanel {
  private static class Language implements Serializable {
    private static final long serialVersionUID = 1L;
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

  private Language[] languages = {
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

  public Lab10Panel() {
    setLayout(new BorderLayout(10, 10));
    setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel header = new JLabel("Лабораторна робота №10: Файли");
    header.setFont(new Font("Arial", Font.BOLD, 16));
    header.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
    filePanel.setBorder(BorderFactory.createTitledBorder("Вибір файлу"));
    filePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JTextField fileField = new JTextField("data.dat", 22);
    JButton btnBrowse = new JButton("Огляд...");

    filePanel.add(new JLabel("Шлях до файлу:"));
    filePanel.add(fileField);
    filePanel.add(btnBrowse);

    JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
    actionPanel.setBorder(BorderFactory.createTitledBorder("Операції введення / виведення"));
    actionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JButton btnShow = new JButton("Вивести масив");
    JButton btnLoadTxt = new JButton("Завантажити з TXT");
    JButton btnLoadBin = new JButton("Завантажити з BIN");
    JButton btnSaveBin = new JButton("Відсортувати та зберегти у BIN");

    actionPanel.add(btnShow);
    actionPanel.add(btnLoadTxt);
    actionPanel.add(btnLoadBin);
    actionPanel.add(btnSaveBin);

    JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
    topContainer.add(header);
    topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
    topContainer.add(filePanel);
    topContainer.add(Box.createRigidArea(new Dimension(0, 5)));
    topContainer.add(actionPanel);

    resultArea = new JTextArea();
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    resultArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Результат"));

    btnBrowse.addActionListener(e -> {
      JFileChooser chooser = new JFileChooser(".");
      if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        fileField.setText(chooser.getSelectedFile().getPath());
      }
    });

    btnShow.addActionListener(e -> printAll("Поточний вміст масиву:"));

    btnLoadTxt.addActionListener(e -> {
      String filename = fileField.getText().trim();
      List<Language> list = new ArrayList<>();
      try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
          String[] p = line.split(",");
          if (p.length >= 4) {
            list.add(new Language(
              p[0].trim(),
              p[1].trim(),
              Integer.parseInt(p[2].trim()),
              Double.parseDouble(p[3].trim())
            ));
          }
        }
        languages = list.toArray(new Language[0]);
        printAll("Дані успішно завантажено з текстового файлу '" + filename + "':");
      } catch (IOException | NumberFormatException ex) {
        resultArea.setText("Помилка читання текстового файлу: " + ex.getMessage());
      }
    });

    btnLoadBin.addActionListener(e -> {
      String filename = fileField.getText().trim();
      try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
        languages = (Language[]) ois.readObject();
        printAll("Дані успішно десеріалізовано з файлу '" + filename + "':");
      } catch (IOException | ClassNotFoundException ex) {
        resultArea.setText("Помилка читання бінарного файлу: " + ex.getMessage());
      }
    });

    btnSaveBin.addActionListener(e -> {
      String filename = fileField.getText().trim();
      Arrays.sort(languages, Comparator.comparingDouble(l -> l.speakers));

      File file = new File(filename);
      File parent = file.getParentFile();
      if (parent != null && !parent.exists()) {
        parent.mkdirs();
      }

      try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
        oos.writeObject(languages);
        printAll("Масив відсортовано за зростанням носіїв та збережено у '" + filename + "':");
      } catch (IOException ex) {
        resultArea.setText("Помилка запису бінарного файлу: " + ex.getMessage());
      }
    });

    add(topContainer, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    printAll("Початковий масив у пам'яті:");
  }

  private void printAll(String title) {
    StringBuilder sb = new StringBuilder(title + "\n\n");
    for (int i = 0; i < languages.length; i++) {
      sb.append(String.format("[%d]  %s\n", i, languages[i]));
    }
    resultArea.setText(sb.toString());
  }
}