package coreservlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IGetFormat;
import interfaces.IHandleHTTP;
import interfaces.IFormatToPOJO;
import models.Film;

@WebServlet("/updateFilm")
public class UpdateFilm extends HttpServlet
		implements interfaces.IHandleHTTP, interfaces.IFormatToPOJO, interfaces.IGetFormat {
	private static final long serialVersionUID = -909062916095173117L;

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
}
