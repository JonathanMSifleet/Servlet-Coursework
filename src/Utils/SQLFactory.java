package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;


public class SQLFactory {

	private Connection conn = null;
	private String dbName = "";
	private String url = "jdbc:mysql://localhost:3306/eecoursework?characterEncoding=utf8" + dbName;
	private String username = "root";
	private String password = "Phantom2011!";

	public ResultSet sqlResult(String SQL, String whereVal) {
		try {
			PreparedStatement statement = conn.prepareStatement(SQL);
			statement.setString(1, whereVal + '%');
			
			return (ResultSet) statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
