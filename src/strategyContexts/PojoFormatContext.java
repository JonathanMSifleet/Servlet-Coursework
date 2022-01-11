package strategyContexts;

import interfaces.IPojoFormatStrategy;
import models.Film;

/**
 * Context for converting Film data to POJO
 */
public class PojoFormatContext {
	private IPojoFormatStrategy strategy;

	/**
	 * Instantiates Strategy context for converting
	 *
	 * @param strategy Strategy to use for converting film data
	 */
	public PojoFormatContext(IPojoFormatStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Convert film data to POJO.
	 *
	 * @param film    Film data to be converted to POJO
	 * @param newFilm Boolean defining whether film is a new film or film already
	 *                exists in database
	 * @return New film POJO
	 */
	public Film convertToPOJO(String film, Boolean newFilm) {
		return strategy.convertToPOJO(film, newFilm);
	}

}
