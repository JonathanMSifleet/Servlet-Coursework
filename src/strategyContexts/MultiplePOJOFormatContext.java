package strategyContexts;

import java.util.ArrayList;

import interfaces.IMultiplePOJOFormatStrategy;
import models.Film;

/**
 * Context for converting multiple film POJOs to specified format
 */
public class MultiplePOJOFormatContext {
	private IMultiplePOJOFormatStrategy strategy;

	/**
	 * Instantiates Strategy context for converting
	 *
	 * @param strategy Strategy to use for converting film POJOs to specified format
	 */
	public MultiplePOJOFormatContext(IMultiplePOJOFormatStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Convert ArrayList of POJOs to specified format
	 *
	 * @param films ArrayList of films to be converted
	 * @return Converted films
	 */
	public String convertArrayToFormat(ArrayList<Film> films) {
		return strategy.convertArrayToFormat(films);
	}

}
