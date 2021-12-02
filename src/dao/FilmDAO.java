package dao;

import java.util.ArrayList;

import com.mysql.jdbc.ResultSet;

import Utils.SQLFactory;
import models.Film;

public class FilmDAO {

	private SQLFactory sqlFactory = new SQLFactory();

	public ArrayList<Film> getAllFilms() {
		try {
			String SQL = "SELECT * FROM eecoursework.films";
			ResultSet rs = sqlFactory.sqlResult(SQL, null);

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
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Film> getFilm(String title) {

		try {
			String SQL = "SELECT * FROM eecoursework.films WHERE title LIKE ?";
			title = "%" + title + "%";

			ResultSet rs = sqlFactory.sqlResult(SQL, title);
			ArrayList<Film> allFilms = new ArrayList<Film>();

			while (rs.next()) {
				allFilms.add(resultToFilm(rs));
			}
			return allFilms;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Film resultToFilm(ResultSet rs) {
		Film newFilm = new Film();

		try {
			newFilm.setId((int) rs.getObject(1));
			newFilm.setTitle((String) rs.getObject(2));
			newFilm.setYear((int) rs.getObject(3));
			newFilm.setDirector((String) rs.getObject(4));
			newFilm.setStars((String) rs.getObject(5));
			newFilm.setReview((String) rs.getObject(6));
			return newFilm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
