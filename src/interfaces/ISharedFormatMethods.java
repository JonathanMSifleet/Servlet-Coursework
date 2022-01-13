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
		MultiplePOJOFormatContext context;

		switch (format) {
			case "xml":
				context = new MultiplePOJOFormatContext(new FilmsToXMLArray());
				break;
			case "csv":
				context = new MultiplePOJOFormatContext(new FilmsToCSVArray());
				break;
			default:
				context = new MultiplePOJOFormatContext(new FilmsToJSONArray());
		}
		
		return context.convertArrayToFormat(films);
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
				break;
			case "csv":
				context = new PojoFormatContext(new CsvToPOJO());
				break;
			default:
				context = new PojoFormatContext(new JsonToPOJO());
		}

		return context.convertToPOJO(requestBodyFilm, true);
	}

}
