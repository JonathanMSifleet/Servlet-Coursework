package coreservlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IRequestHelpers;
import models.Film;
import multiplePOJOFormatStrategy.FilmsToCSVArray;
import multiplePOJOFormatStrategy.FilmsToJSONArray;
import multiplePOJOFormatStrategy.FilmsToXMLArray;
import multiplePOJOFormatStrategy.MultiplePOJOFormatContext;

/**
 * GetAllFilms servlet
 */
@WebServlet("/getAllFilms")
public class GetAllFilms extends HttpServlet implements interfaces.IRequestHelpers {
	private static final long serialVersionUID = -1809220141023596490L;

	/**
	 * Get all films functionality
	 *
	 * @param request  HTTP request
	 * @param response HTTP response
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IRequestHelpers.setHeaders(response, "GET");
		
		// get format from url
		String format = IRequestHelpers.getFormat(request);
		
		// get all films from data access object
		ArrayList<Film> films = FilmDAOSingleton.getFilmDAO().getAllFilms();

		Object formattedFilms = formatFilms(format, films, response);

		// send response containing formatted list of all films
		IRequestHelpers.sendResponse(response, formattedFilms);
	}
	
	private Object formatFilms(String format, ArrayList<Film> films, HttpServletResponse response) {
		// convert film array list to relevant data type
		// and set appropriate header for HTTP response
		switch (format) {
			case "xml":
				response.setContentType("text/xml");
				return new MultiplePOJOFormatContext(new FilmsToXMLArray()).convertArrayToFormat(films);
			case "csv":
				response.setContentType("text/csv");
				return new MultiplePOJOFormatContext(new FilmsToCSVArray()).convertArrayToFormat(films);
			default:
				response.setContentType("application/json");
				return new MultiplePOJOFormatContext(new FilmsToJSONArray()).convertArrayToFormat(films);
		}
	}
}
