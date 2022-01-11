package pojoFormatStrategy;

import models.Film;

public class PojoFormatContext {
	private PojoFormatStrategy strategy;

	public PojoFormatContext(PojoFormatStrategy strategy) {
		this.strategy = strategy;
	}

	public Film convertToPOJO(String film, Boolean newFilm) {
		return strategy.convertToPOJO(film, newFilm);
	}

}
