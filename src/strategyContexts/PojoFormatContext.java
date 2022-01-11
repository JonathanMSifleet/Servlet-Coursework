package strategyContexts;

import interfaces.IPojoFormatStrategy;
import models.Film;

public class PojoFormatContext {
	private IPojoFormatStrategy strategy;

	public PojoFormatContext(IPojoFormatStrategy strategy) {
		this.strategy = strategy;
	}

	public Film convertToPOJO(String film, Boolean newFilm) {
		return strategy.convertToPOJO(film, newFilm);
	}

}
