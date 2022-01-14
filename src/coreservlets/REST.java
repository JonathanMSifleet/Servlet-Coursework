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
 * Servlet containing other servlet functionality
 */
@WebServlet("/REST")
public class REST extends HttpServlet implements interfaces.IRequestHelpers, ISharedFormatMethods {
	private static final long serialVersionUID = -1942414154482873963L;

	/**
	 * Functionality for GET requests
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

		FilmDAOSingleton filmDAO = FilmDAOSingleton.getFilmDAO();

		// get get type from url
		String getType = request.getParameter("getType");

		Object films;

		// determine which type of get function to use based on get type from url
		switch (getType) {
			case "title":
				String title = request.getParameter("title");
				films = ISharedFormatMethods.filmPOJOsToFormat(filmDAO.getFilmsByTitle(title), format);
				break;
			case "id":
				int id = Integer.parseInt(request.getParameter("id"));
				films = getFilmByID(filmDAO, format, id, response);
				break;
			default:
				films = ISharedFormatMethods.filmPOJOsToFormat(filmDAO.getAllFilms(), format);
		}

		// send response containing film(s)
		IRequestHelpers.sendResponse(response, films);
	}

	/**
	 * Functionality for POST requests
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
		Film film = ISharedFormatMethods.formatToFilmPOJO(format, requestBodyFilm, true);

		// send response containing number of rows affected by creating
		// new film
		IRequestHelpers.sendResponse(response, FilmDAOSingleton.getFilmDAO().createFilm(film));
	}

	/**
	 * Functionality for PUT requests
	 *
	 * @param request  HTTP request
	 * @param response HTTP response
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IRequestHelpers.setHeaders(response, "PUT");

		// get format from url
		String format = IRequestHelpers.getFormat(request);

		// get film from HTTP body
		String requestBodyFilm = IRequestHelpers.getRequestBody(request);

		// set film equal to film object converted based on relevant format
		Film film = ISharedFormatMethods.formatToFilmPOJO(format, requestBodyFilm, false);

		// print number of affected rows due to updating film
		IRequestHelpers.sendResponse(response, FilmDAOSingleton.getFilmDAO().updateFilm(film));
	}

	/**
	 * Functionality for DELETE request
	 *
	 * @param request  HTTP request
	 * @param response HTTP response
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IRequestHelpers.setHeaders(response, "DELETE");
		// get id from url
		int id = Integer.parseInt(request.getParameter("id"));

		// print number of affected rows due to deleting film
		IRequestHelpers.sendResponse(response, FilmDAOSingleton.getFilmDAO().deleteFilm(id));
	}

	/**
	 * Gets the film by ID.
	 *
	 * @param filmDAO  Film DAO object
	 * @param format   Format for Films
	 * @param id       ID of film to search for
	 * @param response HTTP response
	 * @return Film in specified format
	 */
	private static Object getFilmByID(FilmDAOSingleton filmDAO, String format, int id, HttpServletResponse response) {
		// get film by ID parameter
		Film film = filmDAO.getFilmByID(id);

		// format film by appropriate format
		return ISharedFormatMethods.filmPOJOsToFormat(new ArrayList<Film>(Arrays.asList(film)), format);
	}
}
