package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLOperations {

	private static Connection conn;
	private static String url = "jdbc:mysql://mudfoot.doc.stu.mmu.ac.uk:6306/sifleetj";
	private static String username = "sifleetj";
	private static String password = "Joosderg6";

	private static void initFactory() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static java.sql.ResultSet sqlSelect(String SQL, ArrayList<Object> paramVals) {
		initFactory();

		try {
			PreparedStatement statement = conn.prepareStatement(SQL);
			statement = prepareStatement(statement, paramVals);

			return statement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int sqlManipulate(String SQL, ArrayList<Object> paramVals) {
		initFactory();

		try {
			PreparedStatement statement = conn.prepareStatement(SQL);
			statement = prepareStatement(statement, paramVals);

			return statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	private static PreparedStatement prepareStatement(PreparedStatement statement, ArrayList<Object> paramVals)
			throws SQLException {

		int paramIndex = 1;

		// for loop returns index out of bounds error:
		// Index: 1, Size: 1
		if (paramVals != null) {
			for (Object param : paramVals) {
				if (param instanceof String) {
					statement.setString(paramIndex, (String) param);
				} else if (param instanceof Integer) {
					statement.setInt(paramIndex, (int) param);
				}

				paramIndex++;
			}
		}

		return statement;
	}

	public static int generateNewID() {
		initFactory();

		String SQL = "SELECT(MAX(id)) FROM films";

		try {
			PreparedStatement statement = conn.prepareStatement(SQL);
			statement = prepareStatement(statement, null);

			java.sql.ResultSet result = statement.executeQuery();
			result.first();

			int largestID = result.getInt(1);
			return ++largestID;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

}
