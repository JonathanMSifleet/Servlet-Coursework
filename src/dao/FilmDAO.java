package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

import models.Film;

public class FilmDAO {

	private Connection conn = null;
	private String dbName = "";
	private String url = "jdbc:mysql://localhost:3306/eecoursework?characterEncoding=utf8" + dbName;
	private String username = "root";
	private String password = "Phantom2011!";

	public ArrayList<Film> getAllFilms() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection(url, username, password);
			Statement stmt = (Statement) conn.createStatement();
			
			String SQL = "SELECT * FROM eecoursework.films";
			
			ResultSet rs = (ResultSet) stmt.executeQuery(SQL);

			ArrayList<Film> allFilms = new ArrayList<Film>();

			while (rs.next()) {

				Film newFilm = new Film();

				newFilm.setId((int) rs.getObject(1));
				newFilm.setTitle((String) rs.getObject(2));
				newFilm.setYear((int) rs.getObject(3));
				newFilm.setDirector((String) rs.getObject(4));
				newFilm.setStars((String) rs.getObject(5));
				newFilm.setReview((String) rs.getObject(6));

				allFilms.add(newFilm);
			}
			
			return allFilms;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

}
