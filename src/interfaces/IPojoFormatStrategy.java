package interfaces;

import models.Film;

public interface IPojoFormatStrategy {
	/**
	 * Convert film data to POJO
	 *
	 * @param film    Film data to be converted to POJO
	 * @param newFilm Boolean defining whether film is a new film or film already
	 *                exists in database
	 */
	public Film convertToPOJO(String film, Boolean newFilm);
}
