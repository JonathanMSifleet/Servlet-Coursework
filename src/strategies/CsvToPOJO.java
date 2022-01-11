package strategies;

import dao.FilmDAOSingleton;
import interfaces.IPojoFormatStrategy;
import models.Film;

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

		// same as above jsonToFilm method except using CSV rather than JSON
		if (newFilm) {
			return new Film.Builder(null).id(FilmDAOSingleton.getFilmDAO().generateNewID()).title(filmAttributes[0])
					.year(Integer.valueOf(filmAttributes[1])).director(filmAttributes[2]).stars(filmAttributes[3])
					.review(filmAttributes[4]).build();
		} else {
			return new Film.Builder(null).id(Integer.valueOf(filmAttributes[0])).title(filmAttributes[1])
					.year(Integer.valueOf(filmAttributes[2])).director(filmAttributes[3]).stars(filmAttributes[4])
					.review(filmAttributes[5]).build();
		}

	}
}