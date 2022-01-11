package interfaces;

import java.util.ArrayList;

import models.Film;

/**
 * Functionality for converting specific Format to singular Film POJO
 */
public interface IMultiplePOJOFormatStrategy {
	/**
	 * Default method for converting ArrayList of POJOs
	 *
	 * @param films ArrayList of films to be converted to specified format
	 */
	public String convertArrayToFormat(ArrayList<Film> films);
}
