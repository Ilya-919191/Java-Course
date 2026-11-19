package lb11;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
  private static final String DB_NAME = "languages_db";
  private static final String USER = "root";
  private static final String PASSWORD = "root";
  private static final String PARAMS = "?allowPublicKeyRetrieval=true&serverTimezone=UTC&characterEncoding=UTF-8";
  private static final String SERVER_URL = "jdbc:mysql://localhost:3306/" + PARAMS;
  private static final String DB_URL = "jdbc:mysql://localhost:3306/" + DB_NAME + PARAMS;
  private static final String BASE_SELECT = "SELECT id, name, language_family, vocabulary_size, speakers_millions FROM languages";

  private Connection getConnection() throws SQLException {
    return DriverManager.getConnection(DB_URL, USER, PASSWORD);
  }

  public void initializeDatabase() {
    String createDbSql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME +
        " CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";

    String createTableSql = """
        CREATE TABLE IF NOT EXISTS languages (
          id INT AUTO_INCREMENT PRIMARY KEY,
          name VARCHAR(100) NOT NULL UNIQUE,
          language_family VARCHAR(100) NOT NULL,
          vocabulary_size INT NOT NULL CHECK (vocabulary_size > 0),
          speakers_millions DOUBLE NOT NULL CHECK (speakers_millions >= 0)
        )
        """;

    try (Connection serverConn = DriverManager.getConnection(SERVER_URL, USER, PASSWORD);
         Statement stmt = serverConn.createStatement()) {
      stmt.executeUpdate(createDbSql);
      System.out.println("Базу даних '" + DB_NAME + "' створено або вона вже існує.");
    } catch (SQLException e) {
      System.out.println("Помилка підключення до сервера БД: " + e.getMessage());
      return;
    }

    try (Connection dbConn = getConnection();
         Statement stmt = dbConn.createStatement()) {
      stmt.executeUpdate(createTableSql);
      System.out.println("Таблицю 'languages' створено.");

      if (isTableEmpty(dbConn)) {
        seedInitialData(dbConn);
        System.out.println("Таблицю наповнено початковими даними (10 записів).");
      }
    } catch (SQLException e) {
      System.out.println("Помилка створення схеми таблиці: " + e.getMessage());
    }
  }

  private boolean isTableEmpty(Connection conn) throws SQLException {
    try (Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM languages")) {
      return rs.next() && rs.getInt(1) == 0;
    }
  }

  private void seedInitialData(Connection conn) throws SQLException {
    Language[] initialLanguages = {
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

    String sql = "INSERT INTO languages (name, language_family, vocabulary_size, speakers_millions) VALUES (?, ?, ?, ?)";
    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      for (Language lang : initialLanguages) {
        ps.setString(1, lang.getName());
        ps.setString(2, lang.getLanguageFamily());
        ps.setInt(3, lang.getVocabularySize());
        ps.setDouble(4, lang.getSpeakersMillions());
        ps.addBatch();
      }
      ps.executeBatch();
    }
  }

  public List<Language> getAllLanguages() {
    List<Language> list = new ArrayList<>();
    String sql = BASE_SELECT + " ORDER BY id";

    try (Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					list.add(mapRow(rs));
				}
    } catch (SQLException e) {
      System.out.println("Помилка читання БД: " + e.getMessage());
    }
    return list;
  }

  public boolean addLanguage(Language lang) {
    String sql = "INSERT INTO languages (name, language_family, vocabulary_size, speakers_millions) VALUES (?, ?, ?, ?)";

    try (Connection conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, lang.getName());
      ps.setString(2, lang.getLanguageFamily());
      ps.setInt(3, lang.getVocabularySize());
      ps.setDouble(4, lang.getSpeakersMillions());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("Помилка додавання запису: " + e.getMessage());
      return false;
    }
  }

  public List<Language> getLanguagesByMinSpeakers(double minSpeakers) {
    List<Language> list = new ArrayList<>();
    String sql = BASE_SELECT + " WHERE speakers_millions > ? ORDER BY speakers_millions DESC";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setDouble(1, minSpeakers);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          list.add(mapRow(rs));
        }
      }
    } catch (SQLException e) {
      System.out.println("Помилка виконання вибірки: " + e.getMessage());
    }
    return list;
  }

  public Language getLanguageById(int id) {
    String sql = BASE_SELECT + " WHERE id = ?";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return mapRow(rs);
        }
      }
    } catch (SQLException e) {
      System.out.println("Помилка пошуку запису: " + e.getMessage());
    }
    return null;
  }

  public boolean updateLanguage(Language lang) {
    String sql = "UPDATE languages SET name = ?, language_family = ?, vocabulary_size = ?, speakers_millions = ? WHERE id = ?";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, lang.getName());
      ps.setString(2, lang.getLanguageFamily());
      ps.setInt(3, lang.getVocabularySize());
      ps.setDouble(4, lang.getSpeakersMillions());
      ps.setInt(5, lang.getId());
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("Помилка оновлення запису: " + e.getMessage());
      return false;
    }
  }

  public boolean deleteLanguage(int id) {
    String sql = "DELETE FROM languages WHERE id = ?";

    try (Connection conn = getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, id);
      return ps.executeUpdate() > 0;
    } catch (SQLException e) {
      System.out.println("Помилка видалення запису: " + e.getMessage());
      return false;
    }
  }

  private Language mapRow(ResultSet rs) throws SQLException {
    return new Language(
      rs.getInt("id"),
      rs.getString("name"),
      rs.getString("language_family"),
      rs.getInt("vocabulary_size"),
      rs.getDouble("speakers_millions")
    );
  }
}