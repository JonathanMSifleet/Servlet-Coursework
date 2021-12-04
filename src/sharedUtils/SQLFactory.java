package sharedUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSet;

public class SQLFactory {

	private Connection conn;
	private static String url = "jdbc:mysql://localhost:3306/eecoursework?characterEncoding=utf8";
	private static String username = "root";
	private static String password = "Phantom2011!";

	public SQLFactory() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet sqlSelect(String SQL, ArrayList<Object> paramVals) {
		try {
			PreparedStatement statement = conn.prepareStatement(SQL);
			statement = prepareStatement(statement, paramVals);

			return (ResultSet) statement.executeQuery();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int sqlInsert(String SQL, ArrayList<Object> paramVals) {
		try {
			PreparedStatement statement = conn.prepareStatement(SQL);
			statement = prepareStatement(statement, paramVals);

			return statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	private PreparedStatement prepareStatement(PreparedStatement statement, ArrayList<Object> paramVals)
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

}
