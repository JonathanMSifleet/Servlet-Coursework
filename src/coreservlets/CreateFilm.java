package coreservlets;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IFormatToPOJO;
import interfaces.IRequestHelpers;
import models.Film;

/**
 * Create film functionality
 */
@WebServlet("/createFilm")
public class CreateFilm extends HttpServlet
		implements interfaces.IRequestHelpers, interfaces.ISQLOperations, interfaces.IFormatToPOJO {

	private static final long serialVersionUID = -1809220141023596490L;

	/**
	 * Create film functionality
	 *
	 * @param request  HTTP request
	 * @param response HTTP response
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		// set relevant headers
		response = IRequestHelpers.setHeaders(response, "POST");

		// get film from HTTP body
		String requestBodyFilm = IRequestHelpers.getRequestBody(request);
		// get format from url
		String format = IRequestHelpers.getFormat(request);

		// set film equal to film object converted based on
		// relevant format
		Film film;
		switch (format) {
			case "xml":
				film = IFormatToPOJO.xmlToFilm(requestBodyFilm, true);
				break;
			case "csv":
				film = IFormatToPOJO.csvToFilm(requestBodyFilm, true);
				break;
			default:
				film = IFormatToPOJO.jsonToFilm(requestBodyFilm, true);
		};

		// send response containing number of rows affected by creating new film
		IRequestHelpers.sendResponse(response, FilmDAOSingleton.getFilmDAO().createFilm(film));
	}

}
