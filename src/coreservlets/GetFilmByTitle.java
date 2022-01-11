package coreservlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IRequestHelpers;
import interfaces.ISharedFormatMethods;
import models.Film;

/**
 * Get film by title servlet
 */
@WebServlet("/getFilmByTitle")
public class GetFilmByTitle extends HttpServlet implements interfaces.IRequestHelpers {
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

		Object formattedFilms = ISharedFormatMethods.filmPOJOsToFormat(films, format);

		// send response containing formatted list of films
		IRequestHelpers.sendResponse(response, formattedFilms);
	}
}
