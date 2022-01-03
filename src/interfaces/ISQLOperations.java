package interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import ConnectionPool.ConnectionPoolSingleton;
import models.Film;

import java.sql.ResultSet;

public interface ISQLOperations {

	static Connection loadDriver() {
		// mudfoot details
		// String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/sifleetj";
		// String username = "sifleetj";
		// String password = "passwordgoeshere";

		// for GCP SQL:
		String url = "jdbc:mysql://34.142.22.192/servletcoursework?user=root&password=wu31wMas9nclNh05";

		// initialise MySQL driver
		try {
			// for mudfoot:
			// Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			// return DriverManager.getConnection(url, username, password);

			// for GCP SQL:
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			return DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	static ArrayList<Film> sqlSelect(String SQL, ArrayList<Object> paramVals) {
		// creating connection closes connection regardless of whether
		// it succeeds or fails, freeing up connection to be reused
		try (Connection conn = ConnectionPoolSingleton.getConnectionPool().getPool().getConnection()) {
			// convert list of MySQL query parameter's into query
			PreparedStatement statement = prepareStatement(conn.prepareStatement(SQL), paramVals);

			// execute SQL query and convert to array list of films
			ArrayList<Film> results = ISQLOperations.resultsToList(statement.executeQuery());
			statement.close();
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	static int sqlManipulate(String SQL, ArrayList<Object> paramVals) {
		int numResults = -1;

		// creating connection closes connection regardless of whether
		// it succeeds or fails, freeing up connection to be reused
		try (Connection conn = ConnectionPoolSingleton.getConnectionPool().getPool().getConnection()) {
			// convert list of MySQL query parameter's into query
			PreparedStatement statement = prepareStatement(conn.prepareStatement(SQL), paramVals);

			// execute query that manipulates data
			numResults = statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return numResults;
	}

	static PreparedStatement prepareStatement(PreparedStatement statement, ArrayList<Object> paramVals) {
		int paramIndex = 1;

		// set statement's parameters equal to list of parameters
		if (paramVals != null) {
			for (Object param : paramVals) {
				try {
					if (param instanceof String) {
						statement.setString(paramIndex, (String) param);
					} else if (param instanceof Integer) {
						statement.setInt(paramIndex, (int) param);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				paramIndex++;
			}
		}
		return statement;
	}

	static int generateNewID() {
		// select the largest ID from
		String SQL = "SELECT(MAX(id)) FROM films";

		// creating connection closes connection regardless of whether
		// it succeeds or fails, freeing up connection to be reused
		try (Connection conn = ConnectionPoolSingleton.getConnectionPool().getPool().getConnection()) {
			// convert list of MySQL query parameter's into query
			PreparedStatement statement = prepareStatement(conn.prepareStatement(SQL), null);

			// execute SQL
			ResultSet result = statement.executeQuery();

			while (result.next()) {
				// return new ID of value of largest ID + 1
				return result.getInt(1) + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	static ArrayList<Film> resultsToList(ResultSet results) {
		ArrayList<Film> films = new ArrayList<>();

		try {
			// convert all results to usable Film POJOs
			while (results.next()) {
				films.add(resultToFilm(results));
			}

			// return array list of films
			return films;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	static Film resultToFilm(ResultSet result) {
		// create new film POJO from result parameter
		try {
			// return film
			return new Film.Builder(null).id((int) result.getObject(1)).title((String) result.getObject(2))
					.year((int) result.getObject(3)).director((String) result.getObject(4)).stars((String) result.getObject(5))
					.review((String) result.getObject(6)).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
