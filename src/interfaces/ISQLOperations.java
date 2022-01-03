package interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import ConnectionPool.ConnectionPoolSingleton;

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

	static ResultSet sqlSelect(String SQL, ArrayList<Object> paramVals) {
		// creating connection closes connection regardless of whether
		// it succeeds or fails, freeing up connection to be reused
		try (Connection conn = ConnectionPoolSingleton.getConnectionPool().getPool().getConnection()) {
			
			PreparedStatement statement = conn.prepareStatement(SQL);
			// convert list of MySQL query parameter's into query
			statement = prepareStatement(statement, paramVals);

			// execute SQL query
			return statement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	static int sqlManipulate(String SQL, ArrayList<Object> paramVals) {
		// creating connection closes connection regardless of whether
		// it succeeds or fails, freeing up connection to be reused
		try (Connection conn = ConnectionPoolSingleton.getConnectionPool().getPool().getConnection()) {
			
			PreparedStatement statement = conn.prepareStatement(SQL);
			// convert list of MySQL query parameter's into query
			statement = prepareStatement(statement, paramVals);

			// execute query that manipulates data
			return statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
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
			PreparedStatement statement = conn.prepareStatement(SQL);
			prepareStatement(statement, null);

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
}
