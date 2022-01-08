package dao;

import java.util.ArrayList;

import interfaces.ISQLOperations;
import models.Film;

/**
 * Implement Data Access Object pattern with Singleton pattern
 */
public class FilmDAOSingleton {
	private static FilmDAOSingleton filmDAO;

	/**
	 * Prevents other classes from instantiating a new film DAO singleton.
	 */
	private FilmDAOSingleton() {
	}

	/**
	 * Instantiates a new film DAO singleton if it doesn't already exist
	 *
	 * @return the film DAO
	 */
	public static synchronized FilmDAOSingleton getFilmDAO() {
		if (filmDAO == null) filmDAO = new FilmDAOSingleton();
		return filmDAO;
	}

	/**
	 * Gets all films from the database
	 *
	 * @return All films from Database as List of Film POJOs
	 */
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

	/**
	 * Gets all films from database by specific title.
	 *
	 * @param title Title to search for
	 * @return the List of Film POJOs containing specified title
	 */
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

	/**
	 * Gets film from database with specific ID
	 *
	 * @param id ID to search for
	 * @return Film POJO with specified ID
	 */
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

	/**
	 * Insert new film into database
	 *
	 * @param film Film POJO to insert
	 * @return Integer specifying number of affected row, i.e. 1 = success
	 */
	public int createFilm(Film film) {
		// create new film
		String SQL = "INSERT INTO films VALUES (?, ?, ?, ?, ?, ?)";

		try {
			// if film id = -1, i.e. the value the DAO returns
			// if an error has occurred, throw an exception
			if (film.getId() == -1) throw new Exception("Invalid film ID");

			// convert film attributes to list of parameters for insert statement
			ArrayList<Object> paramVals = film.attributesToParamList();
			// execute SQL
			return ISQLOperations.sqlManipulate(SQL, paramVals);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 * Update film in database
	 *
	 * @param film Film POJO containing updated film
	 * @return Integer specifying number of affected row, i.e. 1 = success
	 */
	public int updateFilm(Film film) {
		// front-end returns entire film so set each film attributes equal to
		// film parameter's attributes
		String SQL = "UPDATE films SET id = ?, title = ?, year = ?, " + "director = ?, stars = ?, review = ? WHERE id = ?";

		try {
			// convert film's parameters to array list
			ArrayList<Object> paramVals = film.attributesToParamList();

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

	/**
	 * Delete film from database
	 *
	 * @param id ID of film to delete
	 * @return Integer specifying number of affected row, i.e. 1 = success
	 */
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

}
