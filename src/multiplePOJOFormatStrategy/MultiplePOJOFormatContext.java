package multiplePOJOFormatStrategy;

import java.util.ArrayList;

import models.Film;

public class MultiplePOJOFormatContext {
	private IMultiplePOJOFormatStrategy strategy;

	public MultiplePOJOFormatContext(IMultiplePOJOFormatStrategy strategy) {
		this.strategy = strategy;
	}

	public String convertArrayToFormat(ArrayList<Film> films) {
		return strategy.convertArrayToFormat(films);
	}

}
