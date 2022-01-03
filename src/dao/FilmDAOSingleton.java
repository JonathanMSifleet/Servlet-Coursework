package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import interfaces.ISQLOperations;
import models.Film;

public class FilmDAOSingleton {

	private static FilmDAOSingleton filmDAO;

	public static synchronized FilmDAOSingleton getFilmDAO() {
		// instantiate new film DAO singleton if it doesn't already exist
		if (filmDAO == null) filmDAO = new FilmDAOSingleton();
		return filmDAO;
	}

	public ArrayList<Film> getAllFilms() {

		try {
			// select all fields and attributes from film table
			String SQL = "SELECT * FROM films";

			// execute sql
			ResultSet results = ISQLOperations.sqlSelect(SQL, null);
			// convert results to usable list, then return list
			return resultsToList(results);
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
			// percentage symbols acts as wild card
			// allowing for any string before and after
			// the value of title
			paramVals.add("%" + title + "%");

			// execute sql with title as SQL parameter
			ResultSet results = ISQLOperations.sqlSelect(SQL, paramVals);
			// convert results to usable list, then return list
			return resultsToList(results);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Film getFilmByID(int id) {
		// select first film (id is unique per film) all attributes
		// from film table where film's id is equal to id parameter
		String SQL = "SELECT * FROM films WHERE id = ? LIMIT 1";

		try {
			ArrayList<Object> paramVals = new ArrayList<>();
			paramVals.add(id);

			// execute sql with title as SQL parameter
			ResultSet result = ISQLOperations.sqlSelect(SQL, paramVals);
			// move result pointer to first result
			result.next();
			// convert result to usable film POJO, then return film
			return resultToFilm(result);
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
			// if an error has occurred,
			// throw an exception
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

		try {
			ArrayList<Object> paramVals = new ArrayList<>();
			paramVals.add(id);

			// execute SQL
			return ISQLOperations.sqlManipulate(SQL, paramVals);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	private ArrayList<Film> resultsToList(ResultSet results) {
		ArrayList<Film> films = new ArrayList<>();

		try {
			// convert all results to usable Film POJOs
			while (results.next()) {
				films.add(resultToFilm(results));
			}

			// return array list of films
			return films;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	private Film resultToFilm(ResultSet result) {

		// create new film POJO from result parameter
		try {
			// return film
			return new Film.Builder(null).id((int) result.getObject(1)).title((String) result.getObject(2))
					.year((int) result.getObject(3)).director((String) result.getObject(4)).stars((String) result.getObject(5))
					.review((String) result.getObject(6)).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
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
