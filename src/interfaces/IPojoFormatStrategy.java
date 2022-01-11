package interfaces;

import models.Film;

public interface IPojoFormatStrategy {
	/**
	 * Convert film data to POJO
	 */
	public Film convertToPOJO(String film, Boolean newFilm);
}
