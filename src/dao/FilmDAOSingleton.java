package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import interfaces.ISQLOperations;
import models.Film;

public class FilmDAOSingleton {

	private static FilmDAOSingleton filmDAO;

	public static synchronized FilmDAOSingleton getFilmDAO() {
		if (filmDAO == null)
			filmDAO = new FilmDAOSingleton();
		return filmDAO;
	}

	public ArrayList<Film> getAllFilms() {
		try {
			String SQL = "SELECT * FROM films";

			java.sql.ResultSet results = ISQLOperations.sqlSelect(ISQLOperations.loadDriver(), SQL, null);
			return resultsToList(results);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Film> getFilmByTitle(String title) {
		String SQL = "SELECT * FROM films WHERE title LIKE ?";

		try {
			ArrayList<Object> paramVals = new ArrayList<>();
			paramVals.add("%" + title + "%");

			java.sql.ResultSet results = ISQLOperations.sqlSelect(ISQLOperations.loadDriver(), SQL, paramVals);

			return resultsToList(results);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Film getFilmByID(int id) {
		String SQL = "SELECT * FROM films WHERE id = ? LIMIT 1";

		try {
			ArrayList<Object> paramVals = new ArrayList<>();
			paramVals.add(id);

			java.sql.ResultSet result = ISQLOperations.sqlSelect(ISQLOperations.loadDriver(), SQL, paramVals);

			result.next();

			return new Film.Builder(null).id(result.getInt("id")).title(result.getString("title"))
					.year(result.getInt("year")).director(result.getString("director")).stars(result.getString("stars"))
					.review(result.getString("review")).build();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int insertFilm(Film film) {
		String SQL = "INSERT INTO films VALUES (?, ?, ?, ?, ?, ?)";

		try {
			ArrayList<Object> paramVals = filmAttributesToParamList(film);

			return ISQLOperations.sqlManipulate(ISQLOperations.loadDriver(), SQL, paramVals);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int updateFilm(Film film) {		
		String SQL = "UPDATE films SET id = ?, title = ?, year = ?, "
				+ "director = ?, stars = ?, review = ? WHERE id = ?";

		try {
			ArrayList<Object> paramVals = filmAttributesToParamList(film);

			paramVals.add(film.getId());
			
			return ISQLOperations.sqlManipulate(ISQLOperations.loadDriver(), SQL, paramVals);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public int deleteFilm(int id) {
		String SQL = "DELETE FROM films WHERE id = ?";

		try {
			ArrayList<Object> paramVals = new ArrayList<>();
			paramVals.add(id);

			return ISQLOperations.sqlManipulate(ISQLOperations.loadDriver(), SQL, paramVals);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	private ArrayList<Film> resultsToList(java.sql.ResultSet results) {
		ArrayList<Film> films = new ArrayList<>();

		try {
			while (results.next()) {
				films.add(resultToFilm(results));
			}
			return films;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private Film resultToFilm(java.sql.ResultSet results) {

		try {
			return new Film.Builder(null).id((int) results.getObject(1)).title((String) results.getObject(2))
					.year((int) results.getObject(3)).director((String) results.getObject(4))
					.stars((String) results.getObject(5)).review((String) results.getObject(6)).build();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private ArrayList<Object> filmAttributesToParamList(Film film) {
		ArrayList<Object> paramVals = new ArrayList<>();

		paramVals.add(film.getId());
		paramVals.add(film.getTitle());
		paramVals.add(film.getYear());
		paramVals.add(film.getDirector());
		paramVals.add(film.getStars());
		paramVals.add(film.getReview());

		return paramVals;
	}
}
