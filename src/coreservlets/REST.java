package coreservlets;

import java.io.IOException;
import java.io.PrintWriter;
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
import interfaces.IMonoObjServletCommon;
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
			case "all" -> payload = getAllFilms(filmDAO, format, response);
			case "title" -> {
				String title = request.getParameter("title");
				payload = getFilmByTitle(filmDAO, format, title, response);
			}
			case "id" -> {
				int id = Integer.parseInt(request.getParameter("id"));
				payload = getFilmByID(filmDAO, format, id, response);
			}
		}

		// send result of above switch
		try {
			IHandleHTTP.sendResponse(response, payload);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IHandleHTTP.setHeaders(response, "POST");

		// get film from HTTP body
		String requestBodyFilm = IMonoObjServletCommon.getRequestBody(request);
		// get format from url
		String format = IGetFormat.getFormat(request);

		// set film equal to film object converted based on
		// relevant format
		Film film = switch (format) {
			case "xml" -> IMonoObjServletCommon.xmlToFilm(requestBodyFilm, true);
			case "csv" -> IMonoObjServletCommon.csvToFilm(requestBodyFilm, true);
			default -> IMonoObjServletCommon.jsonToFilm(requestBodyFilm, true);
		};

		// send number of affected rows as a result of inserting film
		try {
			IHandleHTTP.sendResponse(response, new FilmDAOSingleton().insertFilm(film));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IHandleHTTP.setHeaders(response, "PUT");

		// get film from HTTP body
		String requestBodyFilm = IMonoObjServletCommon.getRequestBody(request);
		// get format from url
		String format = IGetFormat.getFormat(request);

		// set film equal to film object converted based on
		// relevant format
		Film film = switch (format) {
			case "xml" -> IMonoObjServletCommon.xmlToFilm(requestBodyFilm, false);
			case "csv" -> IMonoObjServletCommon.csvToFilm(requestBodyFilm, false);
			default -> IMonoObjServletCommon.jsonToFilm(requestBodyFilm, false);
		};

		// send number of affected rows as a result of updating film
		try {
			IHandleHTTP.sendResponse(response, new FilmDAOSingleton().updateFilm(film));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IHandleHTTP.setHeaders(response, "DELETE");
		// get id from url
		int id = Integer.parseInt(request.getParameter("id"));

		// print number of affected rows due to deleting film
		try {
			PrintWriter out = response.getWriter();
			out.print(new FilmDAOSingleton().deleteFilm(id));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Object getAllFilms(FilmDAOSingleton filmDAO, String format, HttpServletResponse response) {
		// return formatted list of all films
		return handleGetAllOrByTitleFormat(filmDAO.getAllFilms(), format, response);
	}

	private static Object getFilmByTitle(FilmDAOSingleton filmDAO, String format, String title,
			HttpServletResponse response) {
		// return formatted list of all films with a title
		// containing the value of the title parameter
		return handleGetAllOrByTitleFormat(filmDAO.getFilmByTitle(title), format, response);
	}

	private static Object getFilmByID(FilmDAOSingleton filmDAO, String format, int id, HttpServletResponse response) {
		// get film by ID parameter
		Film film = filmDAO.getFilmByID(id);

		Object payload = null;

		// format film by appropriate format
		switch (format) {
			case "xml" -> {
				response.setContentType("text/xml");
				XStream xstream = new XStream();
				xstream.alias("film", Film.class);
				payload = xstream.toXML(film);
			}
			case "csv" -> {
				response.setContentType("text/csv");
				payload = film.getId() + ",," + film.getTitle() + ",," + film.getYear() + ",," + film.getDirector() + ",,"
						+ film.getStars() + ",," + film.getReview();
			}
			default -> {
				response.setContentType("application/json");
				payload = new Gson().toJson(film);
			}
		}

		// return formatted film
		return payload;
	}

	private static Object handleGetAllOrByTitleFormat(ArrayList<Film> films, String format,
			HttpServletResponse response) {
		Object payload;

		// format list of films by appropriate format
		switch (format) {
			case "xml" -> {
				response.setContentType("text/xml");
				payload = IPolyObjServletCommon.filmsToXMLArray(films);
			}
			case "csv" -> {
				response.setContentType("text/csv");
				payload = IPolyObjServletCommon.filmsToCSVArray(films);
			}
			default -> {
				response.setContentType("application/json");
				payload = IPolyObjServletCommon.filmsToJSONArray(films);
			}
		}

		// return formatted films
		return payload;
	}
}
