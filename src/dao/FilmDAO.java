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
			return filmQueryToResults(rs);
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

			ResultSet rs = sqlFactory.sqlResult(SQL, paramVals);
			return filmQueryToResults(rs);
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

	private ArrayList<Film> filmQueryToResults(ResultSet rs) {
		ArrayList<Film> films = new ArrayList<Film>();

		try {
			while (rs.next()) {
				films.add(resultToFilm(rs));
			}
		} catch (Exception e) {
		}

		return films;
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
