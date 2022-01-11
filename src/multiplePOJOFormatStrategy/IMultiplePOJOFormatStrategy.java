package multiplePOJOFormatStrategy;

import java.util.ArrayList;

import models.Film;

/**
 * Functionality for converting specific Format to singular Film POJO
 */
public interface IMultiplePOJOFormatStrategy {
	public String convertArrayToFormat(ArrayList<Film> films);
}
