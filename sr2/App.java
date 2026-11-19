package sr2;
import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
  private final JPanel mainContentPanel;
  private final CardLayout cardLayout;

  public App() {
    setTitle("Лабораторні роботи (ЛР 8–11)");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(980, 650);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());

    JPanel navPanel = new JPanel(new GridLayout(6, 1, 5, 5));
    navPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    navPanel.setBackground(new Color(245, 245, 245));

    cardLayout = new CardLayout();
    mainContentPanel = new JPanel(cardLayout);

    mainContentPanel.add(createAuthorPanel(), "Author");
    mainContentPanel.add(new Lab8Panel(), "Lab8");
    mainContentPanel.add(new Lab9Panel(), "Lab9");
    mainContentPanel.add(new Lab10Panel(), "Lab10");
    mainContentPanel.add(new Lab11Panel(), "Lab11");

    addNavButton(navPanel, "Про автора", "Author");
    addNavButton(navPanel, "Лабораторна 8", "Lab8");
    addNavButton(navPanel, "Лабораторна 9", "Lab9");
    addNavButton(navPanel, "Лабораторна 10", "Lab10");
    addNavButton(navPanel, "Лабораторна 11", "Lab11");

    add(navPanel, BorderLayout.WEST);
    add(mainContentPanel, BorderLayout.CENTER);
  }

  private void addNavButton(JPanel navPanel, String title, String cardName) {
    JButton btn = new JButton(title);
    btn.addActionListener(e -> cardLayout.show(mainContentPanel, cardName));
    navPanel.add(btn);
  }

  private JPanel createAuthorPanel() {
    JPanel panel = new JPanel(new BorderLayout(15, 15));
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JLabel titleLabel = new JLabel("Інформація про автора", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
    panel.add(titleLabel, BorderLayout.NORTH);

    JTextArea infoText = new JTextArea();
    infoText.setText(
      "ПІБ: Попель Ілля Владиславович\n" +
      "Група: КН-24002б (2п)\n" +
      "Спеціальність: Комп'ютерні науки\n" +
      "Навчальний заклад: НУБіП України\n" +
      "E-mail: kn24-i.popel@nubip.edu.ua\n\n" +
      "Графічний інтерфейс для виконання лабораторних робіт №8–11:\n" +
      "• ЛР 8 - Масиви\n" +
      "• ЛР 9 - Рядки\n" +
      "• ЛР 10 - Файли\n" +
      "• ЛР 11 - БД"
    );
    infoText.setFont(new Font("SansSerif", Font.PLAIN, 14));
    infoText.setEditable(false);
    infoText.setLineWrap(true);
    infoText.setWrapStyleWord(true);

    panel.add(new JScrollPane(infoText), BorderLayout.CENTER);
    return panel;
  }
}