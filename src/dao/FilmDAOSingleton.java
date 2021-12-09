package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import models.Film;
import utils.SQLOperations;

public class FilmDAOSingleton {

	private static FilmDAOSingleton filmDAO;

	public static synchronized FilmDAOSingleton getFilmDAO() {
		if (filmDAO == null)
			filmDAO = new FilmDAOSingleton();
		return filmDAO;
	}

	public ArrayList<Film> getAllFilms() {
		try {
			String SQL = "SELECT * FROM eecoursework.films";

			java.sql.ResultSet rs = SQLOperations.sqlSelect(SQL, null);
			return resultsToList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Film> getFilmByTitle(String title) {
		String SQL = "SELECT * FROM eecoursework.films WHERE title LIKE ?";

		try {
			ArrayList<Object> paramVals = new ArrayList<Object>();
			paramVals.add("%" + title + "%");

			java.sql.ResultSet results = SQLOperations.sqlSelect(SQL, paramVals);
			return resultsToList(results);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Film> getFilmByID(int id) {
		String SQL = "SELECT * FROM eecoursework.films WHERE id = ?";

		try {
			ArrayList<Object> paramVals = new ArrayList<Object>();
			paramVals.add(id);

			java.sql.ResultSet results = SQLOperations.sqlSelect(SQL, paramVals);
			return resultsToList(results);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int insertFilm(Film film) {
		String SQL = "INSERT INTO eecoursework.films VALUES (?, ?, ?, ?, ?, ?)";

		try {
			ArrayList<Object> paramVals = new ArrayList<>();
			paramVals.add(film.getId());
			paramVals.add(film.getTitle());
			paramVals.add(film.getYear());
			paramVals.add(film.getDirector());
			paramVals.add(film.getStars());
			paramVals.add(film.getReview());

			return SQLOperations.sqlManipulate(SQL, paramVals);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int updateFilm(Film film) {
		String SQL = "UPDATE eecoursework.films SET id = ?, title = ?, year = ?, "
				+ "director = ?, stars = ?, review = ? WHERE id = ?";

		try {
			ArrayList<Object> paramVals = new ArrayList<>();
			paramVals.add(film.getId());
			paramVals.add(film.getTitle());
			paramVals.add(film.getYear());
			paramVals.add(film.getDirector());
			paramVals.add(film.getStars());
			paramVals.add(film.getReview());
			paramVals.add(film.getId());

			return SQLOperations.sqlManipulate(SQL, paramVals);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private ArrayList<Film> resultsToList(java.sql.ResultSet results) {
		ArrayList<Film> films = new ArrayList<Film>();

		try {
			while (results.next()) {
				Film film = resultToFilm(results);
				films.add(film);
			}
			return films;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Film resultToFilm(java.sql.ResultSet results) {
		Film newFilm = new Film();

		try {
			newFilm.setId((int) results.getObject(1));
			newFilm.setTitle((String) results.getObject(2));
			newFilm.setYear((int) results.getObject(3));
			newFilm.setDirector((String) results.getObject(4));
			newFilm.setStars((String) results.getObject(5));
			newFilm.setReview((String) results.getObject(6));
			return newFilm;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
