package formatStrategy;

import models.Film;

/**
 * Functionality for converting specific Format to singular Film POJO
 */
public interface Strategy {
	public Film convertToPOJO(String film, Boolean newFilm);
}
