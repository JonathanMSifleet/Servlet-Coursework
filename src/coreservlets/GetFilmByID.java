package coreservlets;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IRequestHelpers;
import interfaces.ISharedFormatMethods;
import models.Film;

/**
 * Get film by ID servlet
 */
@WebServlet("/getFilmByID")
public class GetFilmByID extends HttpServlet implements interfaces.IRequestHelpers {
	private static final long serialVersionUID = -1809220141023596490L;

	/**
	 * Get film by ID functionality
	 *
	 * @param request  HTTP request
	 * @param response HTTP response
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// set relevant headers
		response = IRequestHelpers.setHeaders(response, "GET");

		// get format from URL
		String format = IRequestHelpers.getFormat(request);

		// get ID from URL
		int id = Integer.parseInt(request.getParameter("id"));

		// get film by ID from data access object
		Film film = FilmDAOSingleton.getFilmDAO().getFilmByID(id);

		Object formattedFilm = ISharedFormatMethods.filmPOJOsToFormat(new ArrayList<Film>(Arrays.asList(film)), format);

		// send response containing formatted film
		IRequestHelpers.sendResponse(response, formattedFilm);
	}
}
