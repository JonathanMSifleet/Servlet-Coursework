package dao;

import java.util.ArrayList;

import interfaces.ISQLOperations;
import models.Film;

public class FilmDAOSingleton {

	private static FilmDAOSingleton filmDAO;

	// prevent other classes instantiating DAO
	private FilmDAOSingleton() {
	}

	public static synchronized FilmDAOSingleton getFilmDAO() {
		// instantiate new film DAO singleton if it doesn't already exist
		if (filmDAO == null) filmDAO = new FilmDAOSingleton();
		return filmDAO;
	}

	public ArrayList<Film> getAllFilms() {
		// select all fields and attributes from film table
		String SQL = "SELECT * FROM films";

		try {
			// execute sql, convert results to usable list, then return list
			return ISQLOperations.sqlSelect(SQL, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Film> getFilmsByTitle(String title) {
		// select all films and attributes where film's title contains
		// value of title parameter
		String SQL = "SELECT * FROM films WHERE title LIKE ?";

		try {
			ArrayList<Object> paramVals = new ArrayList<>();
			// percentage symbols acts as wild card allowing
			// for any string before and after the value of title
			paramVals.add("%" + title + "%");

			// execute sql with title as SQL parameter,
			// convert results to usable list, then return list
			return ISQLOperations.sqlSelect(SQL, paramVals);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Film getFilmByID(int id) {
		// select first film (id is unique per film) all attributes
		// from film table where film's id is equal to id parameter
		String SQL = "SELECT * FROM films WHERE id = ? LIMIT 1";
		ArrayList<Object> paramVals = new ArrayList<>();

		try {
			paramVals.add(id);

			// execute sql with title as SQL parameter
			// convert result to usable film POJO, then return film
			return ISQLOperations.sqlSelect(SQL, paramVals).get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public int insertFilm(Film film) {
		// create new film
		String SQL = "INSERT INTO films VALUES (?, ?, ?, ?, ?, ?)";

		try {
			// if film id = -1, i.e. the value the DAO returns
			// if an error has occurred, throw an exception
			if (film.getId() == -1) throw new Exception("Invalid film ID");

			// convert film attributes to list of parameters for insert statement
			ArrayList<Object> paramVals = filmAttributesToParamList(film);
			// execute SQL
			return ISQLOperations.sqlManipulate(SQL, paramVals);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int updateFilm(Film film) {
		// front-end returns entire film so set each film attributes equal to
		// film parameter's attributes
		String SQL = "UPDATE films SET id = ?, title = ?, year = ?, " + "director = ?, stars = ?, review = ? WHERE id = ?";

		try {
			// convert film's parameters to array list
			ArrayList<Object> paramVals = filmAttributesToParamList(film);

			// set last parameter in list equal to films id
			// to fulfil WHERE clause
			paramVals.add(film.getId());

			// execute SQL
			return ISQLOperations.sqlManipulate(SQL, paramVals);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	public int deleteFilm(int id) {
		// delete film from table where film's ID
		// is equal to id parameter's value
		String SQL = "DELETE FROM films WHERE id = ?";
		ArrayList<Object> paramVals = new ArrayList<>();

		try {
			paramVals.add(id);

			// execute SQL
			return ISQLOperations.sqlManipulate(SQL, paramVals);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	private ArrayList<Object> filmAttributesToParamList(Film film) {
		ArrayList<Object> paramVals = new ArrayList<>();

		// convert film attributes to list of parameterS
		// for SQL query
		try {
			paramVals.add(film.getId());
			paramVals.add(film.getTitle());
			paramVals.add(film.getYear());
			paramVals.add(film.getDirector());
			paramVals.add(film.getStars());
			paramVals.add(film.getReview());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return list of parameters
		return paramVals;
	}
}
