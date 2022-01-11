package interfaces;

import java.util.ArrayList;

import models.Film;
import strategies.CsvToPOJO;
import strategies.FilmsToCSVArray;
import strategies.FilmsToJSONArray;
import strategies.FilmsToXMLArray;
import strategies.JsonToPOJO;
import strategies.XmlToPOJO;
import strategyContexts.MultiplePOJOFormatContext;
import strategyContexts.PojoFormatContext;

public interface ISharedFormatMethods {

	/**
	 * Format film POJO to specified format, when POJO is retrieved via getFilmById method
	 *
	 * @param format Format to convert POJO to
	 * @param film   Film POJO to be converted
	 * @return Film POJO in specified format
	 */
	static Object filmByIDToFormat(String format, Film film) {
		// format film based upon relevant format
		switch (format) {
			case "xml":
				context = new PojoFormatContext(new XmlToPOJO());
			case "csv":
				context = new PojoFormatContext(new CsvToPOJO());
			default:
				context = new PojoFormatContext(new JsonToPOJO());
		}

		return context.convertToPOJO(requestBodyFilm, true);
	}

	/**
	 * Format multiple film POJOs by appropriate format
	 *
	 * @param films    List of Film POJOs
	 * @param format   Format for Films
	 * @param response HTTP response
	 * @return List of films in specified format
	 */
	static Object filmPOJOsToFormat(ArrayList<Film> films, String format) {
		switch (format) {
			case "xml":
				return new MultiplePOJOFormatContext(new FilmsToXMLArray()).convertArrayToFormat(films);
			case "csv":
				return new MultiplePOJOFormatContext(new FilmsToCSVArray()).convertArrayToFormat(films);
			default:
				return new MultiplePOJOFormatContext(new FilmsToJSONArray()).convertArrayToFormat(films);
		}
	}

}
