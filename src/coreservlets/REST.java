package coreservlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response = IHandleHTTP.setHeaders(response, "GET");

		FilmDAOSingleton filmDAO = FilmDAOSingleton.getFilmDAO();
		String format = IGetFormat.getFormat(request);
		Object payload = null;

		String getType = request.getParameter("getType");
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

		IHandleHTTP.sendResponse(response, payload);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response = IHandleHTTP.setHeaders(response, "POST");

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		String requestBodyFilm = IMonoObjServletCommon.getRequestBody(request);
		String format = IGetFormat.getFormat(request);

		Film film = switch (format) {
			case "xml" -> IMonoObjServletCommon.xmlToFilm(requestBodyFilm, true);
			case "csv" -> IMonoObjServletCommon.csvToFilm(requestBodyFilm, true);
			default -> IMonoObjServletCommon.jsonToFilm(requestBodyFilm, true);
		};

		IHandleHTTP.sendResponse(response, filmDAO.insertFilm(film));
	}

	private static Object getAllFilms(FilmDAOSingleton filmDAO, String format, HttpServletResponse response) {
		ArrayList<Film> films = filmDAO.getAllFilms();

		return handleGetAllOrByTitleFormat(films, format, response);
	}

	private static Object getFilmByTitle(FilmDAOSingleton filmDAO, String format, String title,
	    HttpServletResponse response) {
		ArrayList<Film> films = filmDAO.getFilmByTitle(title);

		return handleGetAllOrByTitleFormat(films, format, response);
	}

	private static Object handleGetAllOrByTitleFormat(ArrayList<Film> films, String format,
	    HttpServletResponse response) {
		Object payload;

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

		return payload;
	}

	private static Object getFilmByID(FilmDAOSingleton filmDAO, String format, int id, HttpServletResponse response) {
		Film film = filmDAO.getFilmByID(id);

		Object payload = null;
		switch (format) {
			case "xml" -> {
				response.setContentType("text/xml");
				XStream xstream = new XStream();
				xstream.alias("film", Film.class);
				payload = xstream.toXML(film);
			}
			case "csv" -> {
				payload = film.getId() + ",," + film.getTitle() + ",," + film.getYear() + ",," + film.getDirector() + ",,"
				    + film.getStars() + ",," + film.getReview();
			}
			default -> {
				response.setContentType("application/json");
				payload = new Gson().toJson(film);
			}
		}

		return payload;
	}

}
