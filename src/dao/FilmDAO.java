package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.ResultSet;

import java.util.HashMap;

import Utils.SQLFactory;
import models.Film;

public class FilmDAO {

	private SQLFactory sqlFactory = new SQLFactory();

	public HashMap<Integer, Film> getAllFilms() {
		try {
			String SQL = "SELECT * FROM eecoursework.films";

			ResultSet rs = sqlFactory.sqlResult(SQL, null);
			return resultsToHashMap(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public HashMap<Integer, Film> getFilm(String title) {
		String SQL = "SELECT * FROM eecoursework.films WHERE title LIKE ?";

		try {
			ArrayList<Object> paramVals = new ArrayList<Object>();
			paramVals.add("%" + title + "%");

			ResultSet results = sqlFactory.sqlResult(SQL, paramVals);
			return resultsToHashMap(results);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int insertFilm(Film film) {
		String SQL = "INSERT INTO FROM eecoursework.films VALUES (?, ?, ?, ?, ?, ?)";

		try {
			ArrayList<Object> paramVals = new ArrayList<>();
			paramVals.add(film.getId());
			paramVals.add(film.getTitle());
			paramVals.add(film.getYear());
			paramVals.add(film.getDirector());
			paramVals.add(film.getStars());
			paramVals.add(film.getReview());

			sqlFactory.sqlResult(SQL, paramVals);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private HashMap<Integer, Film> resultsToHashMap(ResultSet results) {

		HashMap<Integer, Film> filmHashMap = new HashMap<Integer, Film>();

		try {
			while (results.next()) {
				Film film = resultToFilm(results);
				filmHashMap.put(film.getId(), film);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return filmHashMap;
	}

	private Film resultToFilm(ResultSet rs) {
		Film newFilm = new Film();

		// must be wrapped in try catch
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

	public Film generateDummyFilm() {
		Film dummyFilm = new Film();
		dummyFilm.setId(42000);
		dummyFilm.setTitle("Bladerunner 2049");
		dummyFilm.setYear(2017);
		dummyFilm.setDirector("Dennis Villenvue");
		dummyFilm.setStars("RYAN GOSLING, HARRISON FORD, ANA DE ARMAS");
		dummyFilm.setReview("GOAT");
		return dummyFilm;
	}

}
