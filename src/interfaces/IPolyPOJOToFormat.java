package interfaces;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import models.Film;

/**
 * Functionality for convert multiple POJOs to specific format
 */
public interface IPolyPOJOToFormat {

	/**
	 * Returns list of Film POJOs to JSON
	 *
	 * @param data List of Film POJOs
	 * @return Film POJOs as JSON
	 */
	static String filmsToJSONArray(ArrayList<Film> data) {
		return new Gson().toJson(data);
	}

	/**
	 * Convert list of Film POJOs to XML
	 *
	 * @param data List of Film POJOs
	 * @return Film POJOs as XML
	 */
	static String filmsToXMLArray(ArrayList<Film> data) {
		XStream xstream = new XStream();
		// change xml tag name from class name to value of
		// first function parameter
		xstream.alias("root", List.class);
		xstream.alias("film", models.Film.class);

		// convert films to XML
		String xmlString = xstream.toXML(data);

		// return XML and XML declaration
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "\n" + xmlString;
	}

	/**
	 * Convert list of Film POJOs to CSV
	 *
	 * @param data List of Film POJOs
	 * @return List of Film POJOs as CSV
	 */
	static String filmsToCSVArray(ArrayList<Film> data) {
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
