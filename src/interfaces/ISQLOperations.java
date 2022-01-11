package interfaces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import connectionPool.ConnectionPoolSingleton;
import models.Film;

import java.sql.ResultSet;

/**
 * Responsible for SQL related functionality
 */
public interface ISQLOperations {

	/**
	 * Executes a SELECT SQL Statement
	 *
	 * @param SQL       contains parameterised SQL statement
	 * @param paramVals contains parameters for SQL statement
	 *
	 * @return List of Film objects
	 */
	static ArrayList<Film> sqlSelect(String SQL, ArrayList<Object> paramVals) {
		// creating connection closes connection regardless of whether
		// it succeeds or fails, freeing up connection to be reused
		try (Connection conn = ConnectionPoolSingleton.getConnectionPool().getPool().getConnection()) {
			// convert list of MySQL query parameter's into query
			PreparedStatement statement = generatePreparedStatement(conn.prepareStatement(SQL), paramVals);

			// execute SQL query and convert to array list of films
			ArrayList<Film> results = resultsToList(statement.executeQuery());
			statement.close();
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Executes a non-SELECT SQL Statement
	 *
	 * @param SQL       contains parameterised SQL statement
	 * @param paramVals contains parameters for SQL statement
	 *
	 * @return Number of affected rows due to SQL statement
	 */
	static int sqlManipulate(String SQL, ArrayList<Object> paramVals) {
		int numResults = -1;

		// creating connection closes connection regardless of whether
		// it succeeds or fails, freeing up connection to be reused
		try (Connection conn = ConnectionPoolSingleton.getConnectionPool().getPool().getConnection()) {
			// convert list of MySQL query parameter's into query
			PreparedStatement statement = generatePreparedStatement(conn.prepareStatement(SQL), paramVals);

			// execute query that manipulates data
			numResults = statement.executeUpdate();
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return numResults;
	}

	/**
	 * Converts statement into prepared statement
	 *
	 * @param SQL       statement
	 * @param paramVals contains parameters for SQL statement
	 * @return Prepared SQL statement
	 */
	static PreparedStatement generatePreparedStatement(PreparedStatement statement, ArrayList<Object> paramVals) {
		int paramIndex = 1;

		// set statement's parameters equal to list of parameters
		// else, returns statement (required for select statements without parameters)
		if (paramVals != null) {
			for (Object param : paramVals) {
				try {
					if (param instanceof String) {
						statement.setString(paramIndex, (String) param);
					} else if (param instanceof Integer) {
						statement.setInt(paramIndex, (int) param);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				paramIndex++;
			}
		}
		return statement;
	}

	/**
	 * Converts SQL ResultSet to list of Films
	 *
	 * @param results Results from SQL query
	 * @return
	 */
	static ArrayList<Film> resultsToList(ResultSet results) {
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

	/**
	 * Converts Single result to Film object
	 *
	 * @param result SQL result
	 * @return Film object
	 */
	static Film resultToFilm(ResultSet result) {
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
}
