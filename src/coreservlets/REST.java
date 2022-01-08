package coreservlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import dao.FilmDAOSingleton;
import interfaces.IRequestHelpers;
import interfaces.IFormatToPOJO;
import interfaces.IPolyPOJOToFormat;
import models.Film;

/**
 * Servlet containing other servlet functionality
 */
@WebServlet("/REST")
public class REST extends HttpServlet implements interfaces.IFormatToPOJO, interfaces.IPolyPOJOToFormat, interfaces.IRequestHelpers {
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

		FilmDAOSingleton filmDAO = FilmDAOSingleton.getFilmDAO();
		// get format from url
		String format = IRequestHelpers.getFormat(request);
		Object payload = null;

		// get get type from url
		String getType = request.getParameter("getType");

		// determine which type of get function to use
		// based on get type from url
		switch (getType) {
			case "all":
				payload = getAllFilms(filmDAO, format, response);
				break;
			case "title":
				String title = request.getParameter("title");
				payload = getFilmByTitle(filmDAO, format, title, response);
				break;
			case "id":
				int id = Integer.parseInt(request.getParameter("id"));
				payload = getFilmByID(filmDAO, format, id, response);
		}

		// send response containing film(s)
		IRequestHelpers.sendResponse(response, payload);
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

		// get film from HTTP body
		String requestBodyFilm = IRequestHelpers.getRequestBody(request);
		// get format from url
		String format = IRequestHelpers.getFormat(request);

		// set film equal to film object converted based on
		// relevant format
		Film film;
		switch (format) {
			case "xml":
				film = IFormatToPOJO.xmlToFilm(requestBodyFilm, false);
				break;
			case "csv":
				film = IFormatToPOJO.csvToFilm(requestBodyFilm, false);
				break;
			default:
				film = IFormatToPOJO.jsonToFilm(requestBodyFilm, false);
		};

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
	 * Return formatted list of all films
	 *
	 * @param filmDAO  Film DAO object
	 * @param format   Format for Films
	 * @param response HTTP response
	 * @return List of all Films in specified format
	 */
	private static Object getAllFilms(FilmDAOSingleton filmDAO, String format, HttpServletResponse response) {
		return formatFilms(filmDAO.getAllFilms(), format, response);
	}

	/**
	 * Return formatted list of films containing specified title.
	 *
	 * @param filmDAO  Film DAO object
	 * @param format   Format for Films
	 * @param title    Title of film to search for
	 * @param response HTTP response
	 * @return List of Films containing specified title in specified format
	 */
	private static Object getFilmByTitle(FilmDAOSingleton filmDAO, String format, String title,
			HttpServletResponse response) {
		// return formatted list of all films with a title
		// containing the value of the title parameter
		return formatFilms(filmDAO.getFilmsByTitle(title), format, response);
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

		Object payload = null;

		// format film by appropriate format
		switch (format) {
			case "xml":
				response.setContentType("text/xml");
				XStream xstream = new XStream();
				xstream.alias("film", Film.class);
				payload = xstream.toXML(film);
				break;
			case "csv":
				response.setContentType("text/csv");
				payload = film.toString();
				break;
			default:
				response.setContentType("application/json");
				payload = new Gson().toJson(film);
		}

		// return formatted film
		return payload;
	}

	/**
	 * Format films by appropriate format
	 *
	 * @param films    List of Film POJOs
	 * @param format   Format for Films
	 * @param response HTTP response
	 * @return List of films in specified format
	 */
	private static Object formatFilms(ArrayList<Film> films, String format, HttpServletResponse response) {
		Object payload;

		switch (format) {
			case "xml":
				response.setContentType("text/xml");
				payload = IPolyPOJOToFormat.filmsToXMLArray(films);
				break;
			case "csv":
				response.setContentType("text/csv");
				payload = IPolyPOJOToFormat.filmsToCSVArray(films);
				break;
			default:
				response.setContentType("application/json");
				payload = IPolyPOJOToFormat.filmsToJSONArray(films);
		}

		// return formatted films
		return payload;
	}
}
