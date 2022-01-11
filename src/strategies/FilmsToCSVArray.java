package strategies;

import java.util.ArrayList;

import interfaces.IMultiplePOJOFormatStrategy;
import models.Film;

public class FilmsToCSVArray implements IMultiplePOJOFormatStrategy {
	/**
	 * Convert list of Film POJOs to CSV
	 *
	 * @param data List of Film POJOs
	 * @return List of Film POJOs as CSV
	 */
	@Override
	public String convertArrayToFormat(ArrayList<Film> data) {
		String films = "";

		// use film model's toString function to convert
		// film's attributes to CSV string
		for (Film film : data) {
			films += film.toString() + "\n";
		}

		// remove additional line terminator, and return CSV film array
		return films.substring(0, films.length() - 2);
	}

}
