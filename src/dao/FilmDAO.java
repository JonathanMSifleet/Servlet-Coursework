package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Utils.SQLFactory;
import models.Film;

public class FilmDAO {

	private SQLFactory sqlFactory = new SQLFactory();

	public ArrayList<Film> getAllFilms() {
		try {
			String SQL = "SELECT * FROM eecoursework.films";

			ResultSet rs = sqlFactory.sqlSelect(SQL, null);
			return resultsToList(rs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<Film> getFilm(String title) {
		String SQL = "SELECT * FROM eecoursework.films WHERE title LIKE ?";

		try {
			ArrayList<Object> paramVals = new ArrayList<Object>();
			paramVals.add("%" + title + "%");

			ResultSet results = sqlFactory.sqlSelect(SQL, paramVals);
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

			return sqlFactory.sqlInsert(SQL, paramVals);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	private ArrayList<Film> resultsToList(ResultSet results) {
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
			return null;
		}
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
