package strategies;

import dao.FilmDAOSingleton;
import interfaces.IPojoFormatStrategy;
import models.Film;
import models.Film.Builder;

public class CsvToPOJO implements IPojoFormatStrategy {
	/**
	 * Convert CSV to Film POJO
	 *
	 * @param csvFilm Film as CSV
	 * @param newFilm Boolean specifying whether new ID for film should be generated
	 * @return Film POJO
	 */
	@Override
	public Film convertToPOJO(String csvFilm, Boolean newFilm) {
		String[] filmAttributes = csvFilm.split(",,");

		// remove quotations from all attributes
		for (String attribute : filmAttributes) {
			attribute = attribute.replace("\"", "");
		}

		// create film object from csv values
		Builder film = new Film.Builder(null);
		if (newFilm) {
			film.id(FilmDAOSingleton.getFilmDAO().generateNewID()).title(filmAttributes[0])
					.year(Integer.valueOf(filmAttributes[1])).director(filmAttributes[2]).stars(filmAttributes[3])
					.review(filmAttributes[4]);
		} else {
			film.id(Integer.valueOf(filmAttributes[0])).title(filmAttributes[1]).year(Integer.valueOf(filmAttributes[2]))
					.director(filmAttributes[3]).stars(filmAttributes[4]).review(filmAttributes[5]);
		}
		return film.build();
	}
}
