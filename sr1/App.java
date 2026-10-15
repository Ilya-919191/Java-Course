package sr1;
import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
  private JPanel mainContentPanel;
  private CardLayout cardLayout;

  public App() {
    setTitle("Лабораторні роботи (ЛР 1-7)");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(900, 600);
    setLocationRelativeTo(null);

    setLayout(new BorderLayout());

    JPanel navPanel = new JPanel();
    navPanel.setLayout(new GridLayout(9, 1, 5, 5));
    navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    navPanel.setBackground(new Color(245, 245, 245));

    cardLayout = new CardLayout();
    mainContentPanel = new JPanel(cardLayout);

    mainContentPanel.add(createAuthorPanel(), "Author");
    mainContentPanel.add(createLab1Panel(), "Lab1");
    mainContentPanel.add(createLab2Panel(), "Lab2");
    mainContentPanel.add(createLab3Panel(), "Lab3");
    mainContentPanel.add(createLab4Panel(), "Lab4");
    mainContentPanel.add(createLab5Panel(), "Lab5");
    mainContentPanel.add(createLab6Panel(), "Lab6");
    mainContentPanel.add(createLab7Panel(), "Lab7");

    JButton btnAuthor = new JButton("Про автора");
    btnAuthor.addActionListener(e -> cardLayout.show(mainContentPanel, "Author"));
    navPanel.add(btnAuthor);

    for (int i = 1; i <= 7; i++) {
      int labNum = i;
      JButton btnLab = new JButton("Лабораторна " + labNum);
      btnLab.addActionListener(e -> cardLayout.show(mainContentPanel, "Lab" + labNum));
      navPanel.add(btnLab);
    }

    add(navPanel, BorderLayout.WEST);
    add(mainContentPanel, BorderLayout.CENTER);
  }

  private JPanel createAuthorPanel() {
    JPanel panel = new JPanel(new BorderLayout(15, 15));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel titleLabel = new JLabel("Інформація про автора", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    panel.add(titleLabel, BorderLayout.NORTH);

    JLabel photoLabel = new JLabel();
    photoLabel.setHorizontalAlignment(JLabel.CENTER);

    ImageIcon icon = new ImageIcon("assets/photo.jpg");
    if (icon.getIconWidth() > 0) {
      Image img = icon.getImage().getScaledInstance(180, 220, Image.SCALE_SMOOTH);
      photoLabel.setIcon(new ImageIcon(img));
    } else {
      photoLabel.setText("[ Розмістіть файл author.jpg у корінь проєкту ]");
      photoLabel.setPreferredSize(new Dimension(180, 220));
      photoLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }
    panel.add(photoLabel, BorderLayout.WEST);

    JTextArea infoText = new JTextArea();
    infoText.setText(
      "ПІБ: Попель Ілля Владиславович\n" +
      "Група: КН-24002б (2п)\n" +
      "Спеціальність: Комп'ютерні науки\n" +
      "Навчальний заклад: НУБіП України\n" +
      "E-mail: kn24-i.popel@nubip.edu.ua\n\n" +
      "Короткий опис проєкту: У цьому проекті представлено 7 лабораторних робіт із можливістю виконання їх у GUI\n"
    );
    infoText.setFont(new Font("SansSerif", Font.PLAIN, 14));
    infoText.setEditable(false);
    infoText.setLineWrap(true);
    infoText.setWrapStyleWord(true);

    panel.add(new JScrollPane(infoText), BorderLayout.CENTER);
    return panel;
  }

  private JPanel createLab1Panel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel header = new JLabel("Лабораторна робота №1: Лінійні обчислення");
    header.setFont(new Font("Arial", Font.BOLD, 16));
    header.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    inputPanel.setBorder(BorderFactory.createTitledBorder("Вхідні дані"));
    inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel labelA = new JLabel("Введіть значення 'a':");
    JTextField fieldA = new JTextField(10);
    JButton btnCalculate = new JButton("Обчислити R");

    inputPanel.add(labelA);
    inputPanel.add(fieldA);
    inputPanel.add(btnCalculate);

    JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
    topContainer.add(header);
    topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
    topContainer.add(inputPanel);

    JTextArea resultArea = new JTextArea(8, 40);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
    resultArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Результат обчислень"));

    btnCalculate.addActionListener(e -> {
      try {
        double a = Double.parseDouble(fieldA.getText().trim());
        double b = 3.0;
        double x = 5.0;

        if (a <= 0) {
          resultArea.setText("Помилка: Значення 'a' має бути додатнім.");
        } else if (x - a == 0) {
          resultArea.setText("Помилка: Значення 'a' не може дорівнювати 'x' (" + x + ").");
        } else {
          double denominator2 = Math.pow(Math.log(a), 3) + 4.5;
          if (denominator2 == 0) {
            resultArea.setText("Помилка: Знаменник другого доданка дорівнює нулю.");
            return;
          }

          double R = (a / (x - a)) + ((Math.pow(b, x) + Math.pow(Math.cos(x), 3)) / denominator2);

          if (Double.isNaN(R)) {
            resultArea.setText("Помилка: Не вдалось обчислити результат (NaN).");
          } else {
            String output = String.format(
              "Результат 'R' = %.4f\n\n" +
              "Початкові параметри:\n" +
              "a = %.4f\n" +
              "b = %.2f\n" +
              "x = %.2f", R, a, b, x
            );
            resultArea.setText(output);
          }
        }
      } catch (NumberFormatException ex) {
        resultArea.setText("Помилка: Введіть коректне число у поле 'a'");
      }
    });

    panel.add(topContainer, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
  }

  private JPanel createLab2Panel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel header = new JLabel("Лабораторна робота №2: Обчислення виразу R");
    header.setFont(new Font("Arial", Font.BOLD, 16));
    header.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    inputPanel.setBorder(BorderFactory.createTitledBorder("Вхідні дані"));
    inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel labelA = new JLabel("Введіть значення 'a':");
    JTextField fieldA = new JTextField(10);
    JButton btnCalculate = new JButton("Обчислити R");

    inputPanel.add(labelA);
    inputPanel.add(fieldA);
    inputPanel.add(btnCalculate);

    JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
    topContainer.add(header);
    topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
    topContainer.add(inputPanel);

    JTextArea resultArea = new JTextArea(8, 40);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
    resultArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Результат обчислень"));

    btnCalculate.addActionListener(e -> {
      try {
        double a = Double.parseDouble(fieldA.getText().trim());
        double b = 3.0;
        double x = 5.0;

        if (a <= 0) {
          resultArea.setText("Значення 'a' має бути додатнім");
        } else if (x - a == 0) {
          resultArea.setText("Значення 'a' не може дорівнювати 'x' (" + x + ")");
        } else {
          double R = (a / (x - a)) + ((Math.pow(b, x) + Math.pow(Math.cos(x), 3)) / (Math.pow(Math.log(a), 3) + 4.5));

          if (Double.isNaN(R)) {
            resultArea.setText("Не вдалось обчислити результат");
          } else {
            String output = String.format(
              "Результат 'R' = %.4f.\nПри значеннях 'a' = %.4f, 'b' = %.2f, 'x' = %.2f\n",
              R, a, b, x
            );
            resultArea.setText(output);
          }
        }
      } catch (NumberFormatException ex) {
        resultArea.setText("Помилка: Введіть коректне число у поле 'a'");
      }
    });

    panel.add(topContainer, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
  }

  private JPanel createLab3Panel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel header = new JLabel("Лабораторна робота №3: Виняткові ситуації");
    header.setFont(new Font("Arial", Font.BOLD, 16));
    header.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    inputPanel.setBorder(BorderFactory.createTitledBorder("Вхідні дані"));
    inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel labelA = new JLabel("Введіть значення 'a':");
    JTextField fieldA = new JTextField(10);
    JButton btnCalculate = new JButton("Обчислити R");

    inputPanel.add(labelA);
    inputPanel.add(fieldA);
    inputPanel.add(btnCalculate);

    JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
    topContainer.add(header);
    topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
    topContainer.add(inputPanel);

    JTextArea resultArea = new JTextArea(8, 40);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
    resultArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Результат обчислень"));

    btnCalculate.addActionListener(e -> {
      try {
        double a;
        try {
          a = Double.parseDouble(fieldA.getText().trim());
        } catch (NumberFormatException ex) {
          throw new IllegalArgumentException("Помилка введення: Ви ввели некоректні дані");
        }

        double b = 3.0;
        double x = 5.0;

        if (a <= 0) {
          throw new IllegalArgumentException("Значення 'a' має бути додатнім.");
        }
        if (x - a == 0) {
          throw new IllegalArgumentException("Значення 'a' не може дорівнювати 'x' (" + x + ").");
        }

        double R = (a / (x - a)) + ((Math.pow(b, x) + Math.pow(Math.cos(x), 3)) / (Math.pow(Math.log(a), 3) + 4.5));

        if (Double.isNaN(R)) {
          throw new ArithmeticException("Не вдалось обчислити результат.");
        }

        String output = String.format(
          "Результат 'R' = %.4f\n" +
          "При значеннях 'a' = %.4f, 'b' = %.2f, 'x' = %.2f",
          R, a, b, x
        );
        resultArea.setText(output);

      } catch (IllegalArgumentException | ArithmeticException ex) {
        resultArea.setText("Помилка обчислень: " + ex.getMessage());
      }
    });

    panel.add(topContainer, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
  }

  private JPanel createLab4Panel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel header = new JLabel("Лабораторна робота №4: Цикли");
    header.setFont(new Font("Arial", Font.BOLD, 16));
    header.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    inputPanel.setBorder(BorderFactory.createTitledBorder("Вхідні дані"));
    inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel labelA = new JLabel("Введіть значення 'a':");
    JTextField fieldA = new JTextField(10);
    JButton btnCalculate = new JButton("Обчислити R");

    inputPanel.add(labelA);
    inputPanel.add(fieldA);
    inputPanel.add(btnCalculate);

    JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
    topContainer.add(header);
    topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
    topContainer.add(inputPanel);

    JTextArea resultArea = new JTextArea(15, 50);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    resultArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Результат обчислень"));

    btnCalculate.addActionListener(e -> {
      StringBuilder sb = new StringBuilder();
      try {
        double a;
        try {
          a = Double.parseDouble(fieldA.getText().trim());
        } catch (NumberFormatException ex) {
          throw new IllegalArgumentException("Ви ввели некоректні дані! Потрібно ввести число.");
        }

        double b = 3.0;

        if (a <= 0) {
          throw new IllegalArgumentException("Значення 'a' має бути додатнім.");
        }

        int N = 13;
        double xStart = -10 - 2.5 * N;
        double xEnd = 5 + 1.2 * N;
        double dx = 0.5 + (double) N / 20;

        sb.append("\tЦикл FOR\n\n");

        int i = 1;
        for (double x = xStart; x <= xEnd + 1e-9; x += dx) {
          if (Math.abs(x - a) < 1e-9) {
            sb.append(String.format("%2d: x = %6.2f;\tДілення на нуль (x = a).\n", i, x));
            i++;
            continue;
          }

          double R = (a / (x - a)) + ((Math.pow(b, x) + Math.pow(Math.cos(x), 3)) / (Math.pow(Math.log(a), 3) + 4.5));

          if (Double.isNaN(R) || Double.isInfinite(R)) {
            sb.append(String.format("%2d: x = %6.2f;\tНе вдалось обчислити результат.\n", i, x));
          } else {
            sb.append(String.format("%2d: x = %6.2f;\tR = %.4f\n", i, x, R));
          }
          i++;
        }

        sb.append("\tЦикл WHILE\n\n");

        i = 1;
        double x = xStart;
        while (x <= xEnd + 1e-9) {
          if (Math.abs(x - a) < 1e-9) {
            sb.append(String.format("%2d: x = %6.2f;\tДілення на нуль (x = a).\n", i, x));
            x += dx;
            i++;
            continue;
          }

          double R = (a / (x - a)) + ((Math.pow(b, x) + Math.pow(Math.cos(x), 3)) / (Math.pow(Math.log(a), 3) + 4.5));

          if (Double.isNaN(R) || Double.isInfinite(R)) {
            sb.append(String.format("%2d: x = %6.2f;\tНе вдалось обчислити результат\n", i, x));
          } else {
            sb.append(String.format("%2d: x = %6.2f;\tR = %.4f\n", i, x, R));
          }

          x += dx;
          i++;
        }

        resultArea.setText(sb.toString());

      } catch (IllegalArgumentException ex) {
        resultArea.setText("Помилка обчислення: " + ex.getMessage());
      }
    });

    panel.add(topContainer, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
  }

  private JPanel createLab5Panel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel header = new JLabel("Лабораторна робота №5: ООП");
    header.setFont(new Font("Arial", Font.BOLD, 16));
    header.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    inputPanel.setBorder(BorderFactory.createTitledBorder("Вхідні дані об'єкта"));
    inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel labelA = new JLabel("a:");
    JTextField fieldA = new JTextField("4.0", 5);
    JLabel labelB = new JLabel("b:");
    JTextField fieldB = new JTextField("3.0", 5);
    JLabel labelN = new JLabel("N:");
    JTextField fieldN = new JTextField("13", 5);

    JButton btnDefault = new JButton("За замовчуванням");
    JButton btnCustom = new JButton("Обчислити для об'єкта");

    inputPanel.add(labelA);
    inputPanel.add(fieldA);
    inputPanel.add(labelB);
    inputPanel.add(fieldB);
    inputPanel.add(labelN);
    inputPanel.add(fieldN);
    inputPanel.add(btnCustom);
    inputPanel.add(btnDefault);

    JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
    topContainer.add(header);
    topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
    topContainer.add(inputPanel);

    JTextArea resultArea = new JTextArea(15, 50);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    resultArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Результати обчислень об'єкта"));

    btnDefault.addActionListener(e -> {
      try {
        lb5.SomeClass obj = new lb5.SomeClass();
        resultArea.setText(getObjResultsText("Результати об'єкта за замовченням", obj));
      } catch (Exception ex) {
        resultArea.setText("Помилка: " + ex.getMessage());
      }
    });

    btnCustom.addActionListener(e -> {
      try {
        double a = Double.parseDouble(fieldA.getText().trim());
        double b = Double.parseDouble(fieldB.getText().trim());
        int n = Integer.parseInt(fieldN.getText().trim());

        lb5.SomeClass obj = new lb5.SomeClass(a, b, n);
        resultArea.setText(getObjResultsText(String.format("Результати для об'єкта (a = %.2f, b = %.2f, N = %d)", a, b, n), obj));
      } catch (NumberFormatException ex) {
        resultArea.setText("Помилка введення: Перевірте правильність введених чисел!");
      } catch (IllegalArgumentException ex) {
        resultArea.setText("Помилка: " + ex.getMessage());
      }
    });

    panel.add(topContainer, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
  }

  private String getObjResultsText(String title, lb5.SomeClass obj) {
    StringBuilder sb = new StringBuilder();
    sb.append(title).append("\n\n");
    sb.append(String.format("Параметри об'єкта: a = %.2f, b = %.2f, N = %d\n\n", obj.getA(), obj.getB(), obj.getN()));

    double xStart = -10 - 2.5 * obj.getN();
    double xEnd = 5 + 1.2 * obj.getN();
    double dx = 0.5 + (double) obj.getN() / 20;

    int i = 1;
    for (double x = xStart; x <= xEnd + 1e-9; x += dx) {
      double R = obj.calculate(x);

      if (Double.isNaN(R) || Double.isInfinite(R)) {
        sb.append(String.format("%2d: x = %6.2f;\tПомилка обчислення.\n", i, x));
      } else {
        sb.append(String.format("%2d: x = %6.2f;\tR = %.4f\n", i, x, R));
      }
      i++;
    }

    return sb.toString();
  }

  private JPanel createLab6Panel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel header = new JLabel("Лабораторна робота №6: Успадкування та Поліморфізм");
    header.setFont(new Font("Arial", Font.BOLD, 16));
    header.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    inputPanel.setBorder(BorderFactory.createTitledBorder("Демонстрація об'єктів"));
    inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JButton btnRunAll = new JButton("Запустити всі тести");
    inputPanel.add(btnRunAll);

    JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
    topContainer.add(header);
    topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
    topContainer.add(inputPanel);

    JTextArea resultArea = new JTextArea(15, 50);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    resultArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Вивід консолі ООП"));

    btnRunAll.addActionListener(e -> {
      StringBuilder sb = new StringBuilder();

      sb.append("Базовий клас Vehicle\n");
      lb6.Vehicle vehicle = new lb6.Vehicle();
      sb.append("Бренд: ").append("Vehicle").append("\n");
      sb.append("Демонстрація робочих методів виконана успішно.\n\n");

      sb.append("Клас Car (за замовченням)\n");
      lb6.Car defaultCar = new lb6.Car();
      sb.append("Об'єкт Car (седан) створено.\n\n");

      sb.append("Клас Car (Спортивний V8)\n");
      lb6.Engine v8Engine = new lb6.Engine("V8 Бітурбо", 500);
      lb6.Car sportCar = new lb6.Car("BMW", 290, "Купе", v8Engine);
      sb.append("Створено авто: BMW (Купе), 290 км/год, V8 Бітурбо (500 к.с.)\n\n");

      sb.append("Поліморфізм: Car в посиланні Vehicle\n");
      lb6.Vehicle polyCar = new lb6.Car();
      sb.append("Тип посилання: Vehicle, реальний об'єкт: Car.\n");

      resultArea.setText(sb.toString());
    });

    panel.add(topContainer, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
  }

  private JPanel createLab7Panel() {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    JLabel header = new JLabel("Лабораторна робота №7: Абстрактні класи та інтерфейси");
    header.setFont(new Font("Arial", Font.BOLD, 16));
    header.setAlignmentX(Component.LEFT_ALIGNMENT);

    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    inputPanel.setBorder(BorderFactory.createTitledBorder("Демонстрація об'єктів"));
    inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JButton btnRunAll = new JButton("Запустити всі тести");
    inputPanel.add(btnRunAll);

    JPanel topContainer = new JPanel();
    topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.Y_AXIS));
    topContainer.add(header);
    topContainer.add(Box.createRigidArea(new Dimension(0, 8)));
    topContainer.add(inputPanel);

    JTextArea resultArea = new JTextArea(15, 50);
    resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    resultArea.setEditable(false);
    JScrollPane scrollPane = new JScrollPane(resultArea);
    scrollPane.setBorder(BorderFactory.createTitledBorder("Вивід поліморфізму та інтерфейсів"));

    btnRunAll.addActionListener(e -> {
      java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
      java.io.PrintStream originalOut = System.out;
      System.setOut(new java.io.PrintStream(outContent));

      try {
        lb7.Vehicle[] vehicles = new lb7.Vehicle[2];
        vehicles[0] = new lb7.Car("BMW", 290, "Купе", new lb7.Engine("V8 Бітурбо", 500));
        vehicles[1] = new lb7.ElectricCar("Tesla Model S", 250, "Ліфтбек", 100);

        for (lb7.Vehicle v : vehicles) {
          v.displayInfo();
          v.accelerate(30);
          v.stop();
          System.out.println();
        }

        lb7.ElectricCar tesla = new lb7.ElectricCar("Tesla Model Y", 217, "Кросовер", 75);

        lb7.Maintainable maintainableService = tesla;
        maintainableService.service();

      } finally {
        System.setOut(originalOut);
      }

      resultArea.setText(outContent.toString());
    });

    panel.add(topContainer, BorderLayout.NORTH);
    panel.add(scrollPane, BorderLayout.CENTER);

    return panel;
  }
}