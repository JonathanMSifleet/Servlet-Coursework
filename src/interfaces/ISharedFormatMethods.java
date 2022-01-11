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

	/**
	 * Format film based upon relevant format
	 *
	 * @param format          Format to convert film from
	 * @param requestBodyFilm Film retrieved from request's body
	 * @return Film POJO
	 */
	static Film formatToFilmPOJO(String format, String requestBodyFilm) {
		PojoFormatContext context;

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

}
