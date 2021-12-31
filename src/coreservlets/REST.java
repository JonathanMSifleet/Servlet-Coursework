package coreservlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import dao.FilmDAOSingleton;
import interfaces.IGetFormat;
import interfaces.IHandleHTTP;
import interfaces.IFormatToPOJO;
import interfaces.IPolyObjServletCommon;
import models.Film;

@WebServlet("/REST")
public class REST extends HttpServlet implements interfaces.IPolyObjServletCommon {
	private static final long serialVersionUID = -1942414154482873963L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IHandleHTTP.setHeaders(response, "GET");

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		// get format from url
		String format = IGetFormat.getFormat(request);
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
		IHandleHTTP.sendResponse(response, payload);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IHandleHTTP.setHeaders(response, "POST");

		// get film from HTTP body
		String requestBodyFilm = IHandleHTTP.getRequestBody(request);
		// get format from url
		String format = IGetFormat.getFormat(request);

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

		// send response containing number of rows affected by inserting
		// new film
		IHandleHTTP.sendResponse(response, new FilmDAOSingleton().insertFilm(film));
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IHandleHTTP.setHeaders(response, "PUT");

		// get film from HTTP body
		String requestBodyFilm = IHandleHTTP.getRequestBody(request);
		// get format from url
		String format = IGetFormat.getFormat(request);

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
		IHandleHTTP.sendResponse(response, new FilmDAOSingleton().updateFilm(film));
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IHandleHTTP.setHeaders(response, "DELETE");
		// get id from url
		int id = Integer.parseInt(request.getParameter("id"));

		// print number of affected rows due to deleting film
		IHandleHTTP.sendResponse(response, new FilmDAOSingleton().deleteFilm(id));
	}

	private static Object getAllFilms(FilmDAOSingleton filmDAO, String format, HttpServletResponse response) {
		// return formatted list of all films
		return handleGetAllOrByTitleFormat(filmDAO.getAllFilms(), format, response);
	}

	private static Object getFilmByTitle(FilmDAOSingleton filmDAO, String format, String title,
			HttpServletResponse response) {
		// return formatted list of all films with a title
		// containing the value of the title parameter
		return handleGetAllOrByTitleFormat(filmDAO.getFilmsByTitle(title), format, response);
	}

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
				payload = film.getId() + ",," + film.getTitle() + ",," + film.getYear() + ",," + film.getDirector() + ",,"
						+ film.getStars() + ",," + film.getReview();
				break;
			default:
				response.setContentType("application/json");
				payload = new Gson().toJson(film);
		}

		// return formatted film
		return payload;
	}

	private static Object handleGetAllOrByTitleFormat(ArrayList<Film> films, String format,
			HttpServletResponse response) {
		Object payload;

		// format list of films by appropriate format
		switch (format) {
			case "xml":
				response.setContentType("text/xml");
				payload = IPolyObjServletCommon.filmsToXMLArray(films);
				break;
			case "csv":
				response.setContentType("text/csv");
				payload = IPolyObjServletCommon.filmsToCSVArray(films);
				break;
			default:
				response.setContentType("application/json");
				payload = IPolyObjServletCommon.filmsToJSONArray(films);
		}

		// return formatted films
		return payload;
	}
}
