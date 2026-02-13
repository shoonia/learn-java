package com.crud;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

	public final void createTask(String title, String details) {
		var sql = "INSERT INTO tasks (title, details) VALUES (?, ?)";
		try (var stmt = prepareStatement(sql)) {
			stmt.setString(1, title);
			stmt.setString(2, details);
			stmt.executeUpdate();
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
}