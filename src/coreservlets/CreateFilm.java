package coreservlets;

import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IRequestHelpers;
import interfaces.ISharedFormatMethods;
import models.Film;

/**
 * Create film functionality
 */
@WebServlet("/createFilm")
public class CreateFilm extends HttpServlet implements interfaces.IRequestHelpers {
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

		// get format from url
		String format = IRequestHelpers.getFormat(request);

		// get film from HTTP body
		String requestBodyFilm = IRequestHelpers.getRequestBody(request);

		// set film equal to film object converted based on relevant format
		Film film = ISharedFormatMethods.formatToFilmPOJO(format, requestBodyFilm);

		// send response containing number of rows affected by creating new film
		IRequestHelpers.sendResponse(response, FilmDAOSingleton.getFilmDAO().createFilm(film));
	}

}
