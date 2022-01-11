package pojoFormatStrategy;

import models.Film;

/**
 * Functionality for converting specific Format to singular Film POJO
 */
public interface PojoFormatStrategy {
	public Film convertToPOJO(String film, Boolean newFilm);
}
