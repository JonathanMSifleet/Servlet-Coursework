package interfaces;

import models.Film;

/**
 * Functionality for converting specific Format to singular Film POJO
 */
public interface IPojoFormatStrategy {
	public Film convertToPOJO(String film, Boolean newFilm);
}
