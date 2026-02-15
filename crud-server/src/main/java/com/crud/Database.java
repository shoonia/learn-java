package com.crud;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

import com.crud.Models.Task;

public record Database(String URL) {

  private PreparedStatement prepareStatement(String sql) throws SQLException {
    return DriverManager.getConnection(URL).prepareStatement(sql);
  }

  private Task mapResultSetToTask(ResultSet rs) throws SQLException {
    return new Task(
        rs.getInt("id"),
        rs.getString("title"),
        rs.getString("details"),
        rs.getString("date_created"),
        rs.getString("date_updated")
      );
  }

  public void init() {
    try (
        var connection = DriverManager.getConnection(URL);
        var stmt = connection.createStatement()
    ) {
      var sql = """
          CREATE TABLE IF NOT EXISTS tasks (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            title VARCHAR(255) NOT NULL,
            details VARCHAR(255) NOT NULL,
            date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
            date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
          );
          """;
      var triggerSql = """
          CREATE TRIGGER IF NOT EXISTS update_tasks_timestamp
          AFTER UPDATE OF title, details ON tasks
          BEGIN
            UPDATE tasks SET date_updated = CURRENT_TIMESTAMP
            WHERE id = OLD.id;
          END;
      """;

      stmt.execute(sql);
      stmt.execute(triggerSql);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Optional<Task> createTask(String title, String details) {
    var sql = """
      INSERT INTO tasks (title, details)
      VALUES (?, ?)
      RETURNING *;
      """;

    try (var stmt = prepareStatement(sql)) {
      stmt.setString(1, title);
      stmt.setString(2, details);

      var rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(mapResultSetToTask(rs));
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Optional<Task> getTask(int id) {
    var sql = "SELECT * FROM tasks WHERE id = ?";
    try (var stmt = prepareStatement(sql)) {
      stmt.setInt(1, id);
      var rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(mapResultSetToTask(rs));
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public void deleteTask(int id) {
    var sql = "DELETE FROM tasks WHERE id = ?";
    try (var stmt = prepareStatement(sql)) {
      stmt.setInt(1, id);
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public Optional<Task> updateTask(int id, String title, String details) {
    var sql = """
        UPDATE tasks
        SET title = ?, details = ?
        WHERE id = ?
        RETURNING *;
        """;

    try (var stmt = prepareStatement(sql)) {
      stmt.setString(1, title);
      stmt.setString(2, details);
      stmt.setInt(3, id);

      var rs = stmt.executeQuery();
      if (rs.next()) {
        return Optional.of(mapResultSetToTask(rs));
      } else {
        return Optional.empty();
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public ArrayList<Task> queryTasks(int limit, int offset) {
    var sql = "SELECT * FROM tasks LIMIT ? OFFSET ?;";
    try (var stmt = prepareStatement(sql)) {
      stmt.setInt(1, limit);
      stmt.setInt(2, offset);

      var rs = stmt.executeQuery();
      var tasks = new ArrayList<Task>();
      while (rs.next()) {
        tasks.add(mapResultSetToTask(rs));
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
