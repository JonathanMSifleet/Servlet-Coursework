package formatStrategy;

import models.Film;

public class Context {
	private Strategy strategy;

	public Context(Strategy strategy) {
		this.strategy = strategy;
	}

	public Film convertToPOJO(String film, Boolean newFilm) {
		return strategy.convertToPOJO(film, newFilm);
	}

}
