package pojoFormatStrategy;

import com.google.gson.Gson;

import dao.FilmDAOSingleton;
import models.Film;
import models.Film.Builder;

public class JsonToPOJO implements PojoFormatStrategy {
	/**
	 * Convert JSON to Film POJO
	 *
	 * @param jsonString Film as JSON
	 * @param newFilm    Boolean specifying whether new ID for film should be
	 *                   generated
	 * @return Film POJO
	 */
	@Override
	public Film convertToPOJO(String jsonString, Boolean newFilm) {
		// if result is a new film then create a new film POJO
		// from JSON but generate new ID, otherwise create a
		// new film POJO from JSON using existing ID

		Builder film = new Film.Builder(new Gson().fromJson(jsonString, Film.class));

		if (newFilm) film.id(FilmDAOSingleton.getFilmDAO().generateNewID());

		return film.build();
	}

}