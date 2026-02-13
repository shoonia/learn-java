package com.crud;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	static final String URL = "jdbc:sqlite:target/tasks.db";

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
		try (
						var connection = DriverManager.getConnection(URL);
						var stmt = connection.prepareStatement(sql)
		) {
			stmt.setString(1, title);
			stmt.setString(2, details);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}