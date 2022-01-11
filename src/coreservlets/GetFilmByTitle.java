package coreservlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IRequestHelpers;
import models.Film;
import strategies.FilmsToXMLArray;
import strategyContexts.MultiplePOJOFormatContext;

/**
 * Get film by title servlet
 */
@WebServlet("/getFilmByTitle")
public class GetFilmByTitle extends HttpServlet implements interfaces.IRequestHelpers {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1809220141023596490L;

	/**
	 * Get film by title functionality
	 *
	 * @param request  the request
	 * @param response the response
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// set relevant headers
		response = IRequestHelpers.setHeaders(response, "GET");
		
		// get format from url
		String format = IRequestHelpers.getFormat(request);

		// get film title from url
		String title = request.getParameter("title");

		// get films with title containing title from url
		ArrayList<Film> films = FilmDAOSingleton.getFilmDAO().getFilmsByTitle(title);

		Object formattedFilms = formatFilms(format, films, response);

		// send response containing formatted list of films
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
				return new MultiplePOJOFormatContext(new FilmsToXMLArray()).convertArrayToFormat(films);
			default:
				response.setContentType("application/json");
				return new MultiplePOJOFormatContext(new FilmsToXMLArray()).convertArrayToFormat(films);
		}
	}
}
