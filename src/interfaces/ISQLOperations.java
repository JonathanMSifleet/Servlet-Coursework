package interfaces;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ISQLOperations {

	static Connection loadDriver() {
		// mudfoot details
		String url = "jdbc:mysql://34.105.148.68:3306/servletcoursework?user=root";
		// String username = "sifleetj";
		// String password = "Joosderg6";

		// initialise MySQL driver
		try {
			Class.forName("com.mysql.jdbc.GoogleDriver").newInstance();
			return DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	static java.sql.ResultSet sqlSelect(Connection conn, String SQL, ArrayList<Object> paramVals) {
		try {
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

	static int sqlManipulate(Connection conn, String SQL, ArrayList<Object> paramVals) {
		try {
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
		Connection conn = loadDriver();

		// select the largest ID from
		String SQL = "SELECT(MAX(id)) FROM films";

		try {
			PreparedStatement statement = conn.prepareStatement(SQL);
			prepareStatement(statement, null);

			// execute SQL
			java.sql.ResultSet result = statement.executeQuery();

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
