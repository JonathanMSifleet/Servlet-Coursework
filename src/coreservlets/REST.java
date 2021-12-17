package coreservlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IGetFormat;
import interfaces.IHandleHTTP;
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

		System.out.println(getType);

		switch (getType) {
			case "all" -> payload = getAllFilms(filmDAO, format, response);
			case "byTitle" -> {
				String title = request.getParameter("title");
				System.out.println(format);
				System.out.println(title);
				payload = getByTitle(filmDAO, format, title, response);
			}
		}

		IHandleHTTP.sendResponse(response, payload);
	}

	private static Object getAllFilms(FilmDAOSingleton filmDAO, String format, HttpServletResponse response) {
		ArrayList<Film> films = filmDAO.getAllFilms();

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

	private static Object getByTitle(FilmDAOSingleton filmDAO, String format, String title,
	    HttpServletResponse response) {
		ArrayList<Film> films = filmDAO.getFilmByTitle(title);

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
}
