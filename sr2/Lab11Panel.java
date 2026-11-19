package sr2;
import lb11.DatabaseManager;
import lb11.Language;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

public class Lab11Panel extends JPanel {
  private final DatabaseManager db = new DatabaseManager();
  private final JTextArea resultArea;

  public Lab11Panel() {
    setLayout(new BorderLayout(10, 10));
    setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel header = new JLabel("Лабораторна робота №11: БД");
    header.setFont(new Font("Arial", Font.BOLD, 16));
    header.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel dbActionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
    dbActionPanel.setBorder(BorderFactory.createTitledBorder("Керування БД та вибірка"));
    dbActionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JButton btnInit = new JButton("Створити БД і таблицю");
    JButton btnViewAll = new JButton("Показати всі записи");
    JTextField minSpeakersField = new JTextField("300", 5);
    JButton btnFilter = new JButton("Носіїв > X млн");

    dbActionPanel.add(btnInit);
    dbActionPanel.add(btnViewAll);
    dbActionPanel.add(new JLabel("Мін. носіїв:"));
    dbActionPanel.add(minSpeakersField);
    dbActionPanel.add(btnFilter);

    JPanel crudPanel = new JPanel(new GridLayout(2, 1, 5, 5));
    crudPanel.setBorder(BorderFactory.createTitledBorder("Додавання, редагування та видалення записів"));
    crudPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel fieldsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 3));
    JTextField idField = new JTextField(4);
    JTextField nameField = new JTextField(10);
    JTextField familyField = new JTextField(11);
    JTextField vocabField = new JTextField(7);
    JTextField speakersField = new JTextField(6);

    fieldsRow.add(new JLabel("ID:"));
    fieldsRow.add(idField);
    fieldsRow.add(new JLabel("Мова:"));
    fieldsRow.add(nameField);
    fieldsRow.add(new JLabel("Сім'я:"));
    fieldsRow.add(familyField);
    fieldsRow.add(new JLabel("Слів:"));
    fieldsRow.add(vocabField);
    fieldsRow.add(new JLabel("Носіїв:"));
    fieldsRow.add(speakersField);

    JPanel buttonsRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
    JButton btnAdd = new JButton("Додати новий");
    JButton btnLoadById = new JButton("Завантажити дані за ID");
    JButton btnUpdate = new JButton("Оновити за ID");
    JButton btnDelete = new JButton("Видалити за ID");

    buttonsRow.add(btnAdd);
    buttonsRow.add(btnLoadById);
    buttonsRow.add(btnUpdate);
    buttonsRow.add(btnDelete);

    crudPanel.add(fieldsRow);
    crudPanel.add(buttonsRow);

    JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
    topContainer.add(header);
    topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
    topContainer.add(dbActionPanel);
    topContainer.add(Box.createRigidArea(new Dimension(0, 5)));
    topContainer.add(crudPanel);

    resultArea = new JTextArea();
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    resultArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Результат роботи з БД"));

    btnInit.addActionListener(e -> {
      String log = captureConsoleOutput(db::initializeDatabase);
      resultArea.setText(log);
    });

    btnViewAll.addActionListener(e -> showLanguages("Усі записи таблиці languages:", db.getAllLanguages()));

    btnFilter.addActionListener(e -> {
      try {
        double min = Double.parseDouble(minSpeakersField.getText().trim().replace(',', '.'));
        List<Language> filtered = db.getLanguagesByMinSpeakers(min);
        showLanguages("Мови, у яких кількість носіїв > " + min + " млн:", filtered);
      } catch (NumberFormatException ex) {
        resultArea.setText("Помилка: введіть коректне число у поле фільтра.");
      }
    });

    btnAdd.addActionListener(e -> {
      try {
        String name = nameField.getText().trim();
        String family = familyField.getText().trim();
        if (name.isEmpty() || family.isEmpty()) {
          resultArea.setText("Помилка: заповніть назву мови та мовну сім'ю.");
          return;
        }
        int vocab = Integer.parseInt(vocabField.getText().trim());
        double speakers = Double.parseDouble(speakersField.getText().trim().replace(',', '.'));

        String log = captureConsoleOutput(() -> {
          if (db.addLanguage(new Language(name, family, vocab, speakers))) {
            System.out.println("Запис успішно додано.");
          }
        });
        showLanguages(log.trim(), db.getAllLanguages());
      } catch (NumberFormatException ex) {
        resultArea.setText("Помилка: перевірте числові поля (Слів та Носіїв).");
      }
    });

    btnLoadById.addActionListener(e -> {
      try {
        int id = Integer.parseInt(idField.getText().trim());
        Language lang = db.getLanguageById(id);
        if (lang == null) {
          resultArea.setText("Запис із ID = " + id + " не знайдено.");
        } else {
          nameField.setText(lang.getName());
          familyField.setText(lang.getLanguageFamily());
          vocabField.setText(String.valueOf(lang.getVocabularySize()));
          speakersField.setText(String.valueOf(lang.getSpeakersMillions()));
          resultArea.setText("Завантажено для редагування:\n\n" + lang);
        }
      } catch (NumberFormatException ex) {
        resultArea.setText("Помилка: введіть ціле число у поле ID.");
      }
    });

    btnUpdate.addActionListener(e -> {
      try {
        int id = Integer.parseInt(idField.getText().trim());
        String name = nameField.getText().trim();
        String family = familyField.getText().trim();
        if (name.isEmpty() || family.isEmpty()) {
          resultArea.setText("Помилка: заповніть назву мови та мовну сім'ю.");
          return;
        }
        int vocab = Integer.parseInt(vocabField.getText().trim());
        double speakers = Double.parseDouble(speakersField.getText().trim().replace(',', '.'));

        String log = captureConsoleOutput(() -> {
          if (db.updateLanguage(new Language(id, name, family, vocab, speakers))) {
            System.out.println("Запис із ID = " + id + " успішно оновлено.");
          } else {
            System.out.println("Не вдалося оновити запис із ID = " + id + ".");
          }
        });
        showLanguages(log.trim(), db.getAllLanguages());
      } catch (NumberFormatException ex) {
        resultArea.setText("Помилка: перевірте числові поля (ID, Слів, Носіїв).");
      }
    });

    btnDelete.addActionListener(e -> {
      try {
        int id = Integer.parseInt(idField.getText().trim());
        if (db.deleteLanguage(id)) {
          showLanguages("Запис із ID = " + id + " видалено.", db.getAllLanguages());
        } else {
          resultArea.setText("Запис із ID = " + id + " не знайдено.");
        }
      } catch (NumberFormatException ex) {
        resultArea.setText("Помилка: введіть ціле число у поле ID.");
      }
    });

    add(topContainer, BorderLayout.NORTH);
    add(scrollPane, BorderLayout.CENTER);
    resultArea.setText("Натисніть «Показати всі записи» або «Створити БД і таблицю» для початку роботи.");
  }

  private void showLanguages(String headerText, List<Language> list) {
    StringBuilder sb = new StringBuilder(headerText + "\n\n");
    if (list.isEmpty()) {
      sb.append("Записів не знайдено.");
    } else {
      for (Language lang : list) {
        sb.append(lang).append("\n");
      }
    }
    resultArea.setText(sb.toString());
  }

  private String captureConsoleOutput(Runnable action) {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(out));
    try {
      action.run();
    } finally {
      System.setOut(originalOut);
    }
    return out.toString();
  }
}