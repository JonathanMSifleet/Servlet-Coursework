package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSet;

public class SQLFactory {

	private Connection conn;
	private static String dbName = "";
	private static String url = "jdbc:mysql://localhost:3306/eecoursework?characterEncoding=utf8" + dbName;
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

	public ResultSet sqlResult(String SQL, ArrayList<Object> paramVals) {
		try {
			PreparedStatement statement = conn.prepareStatement(SQL);
			
			System.out.println()
			
			for(int i = 0; i < paramVals.size(); i++) {
				if(paramVals.get(i) != null) {
					statement.setString(i, (String) paramVals.get(i));
				} else {
					statement.setString(i, null);
				}
			}
			return (ResultSet) statement.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
