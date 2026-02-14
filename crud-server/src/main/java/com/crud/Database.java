package com.crud;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import com.crud.Models.Task;

public class Database {
  static final String URL = "jdbc:sqlite:target/tasks.db";

  private PreparedStatement prepareStatement(String sql) throws SQLException {
    return DriverManager.getConnection(URL).prepareStatement(sql);
  }

  public Database() {
    try (
        var connection = DriverManager.getConnection(URL);
        var stmt = connection.createStatement()
    ) {
      var sql = """
          CREATE TABLE IF NOT EXISTS tasks (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            title VARCHAR(255) NOT NULL,
            details VARCHAR(255) NOT NULL
          );
          """;

      stmt.execute(sql);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public final Optional<Task> createTask(String title, String details) {
    var sql = """
      INSERT INTO tasks (title, details)
      VALUES (?, ?)
      RETURNING id, title, details;
      """;

    try (var stmt = prepareStatement(sql)) {
      stmt.setString(1, title);
      stmt.setString(2, details);

      var rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(new Task(
          rs.getInt("id"),
          rs.getString("title"),
          rs.getString("details")
        ));
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public final Optional<Task> getTask(int id) {
    var sql = "SELECT id, title, details FROM tasks WHERE id = ?";
    try (var stmt = prepareStatement(sql)) {
      stmt.setInt(1, id);
      var rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(
            new Task(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("details")
            )
        );
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public final void deleteTask(int id) {
    var sql = "DELETE FROM tasks WHERE id = ?";
    try (var stmt = prepareStatement(sql)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public final Optional<Task> updateTask(int id, String title, String details) {
    var sql = """
        UPDATE tasks
        SET title = ?, details = ?
        WHERE id = ?
        RETURNING id, title, details
        """;

    try (var stmt = prepareStatement(sql)) {
      stmt.setString(1, title);
      stmt.setString(2, details);
      stmt.setInt(3, id);

      var rs = stmt.executeQuery();

      if (rs.next()) {
        return Optional.of(new Task(
          rs.getInt("id"),
          rs.getString("title"),
          rs.getString("details")
        ));
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public ArrayList<Task> queryTasks(int limit, int offset) {
    var sql = "SELECT id, title, details FROM tasks LIMIT ? OFFSET ?;";
    try (var stmt = prepareStatement(sql)) {
      stmt.setInt(1, limit);
      stmt.setInt(2, offset);

      var rs = stmt.executeQuery();
      var tasks = new ArrayList<Task>();
      while (rs.next()) {
        tasks.add(new Task(
          rs.getInt("id"),
          rs.getString("title"),
          rs.getString("details")
        ));
      }

      return tasks;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public int countTasks() {
    var sql = "SELECT COUNT(*) AS count FROM tasks;";
    try (var stmt = prepareStatement(sql)) {
      var rs = stmt.executeQuery();
      if (rs.next()) {
        return rs.getInt("count");
      } else {
        return 0;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
