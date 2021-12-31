package coreservlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IRequestHelpers;
import interfaces.IPolyPOJOToFormat;
import models.Film;

@WebServlet("/getAllFilms")
public class GetAllFilms extends HttpServlet
		implements interfaces.IRequestHelpers, interfaces.IPolyPOJOToFormat {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IRequestHelpers.setHeaders(response, "GET");
		// get all films from data access object
		ArrayList<Film> films = new FilmDAOSingleton().getAllFilms();
		// get format from url
		String format = IRequestHelpers.getFormat(request);
		Object payload;
		// convert film array list to relevant data type
		// and set appropriate header for HTTP response
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
		// send response containing formatted list of all films
		IRequestHelpers.sendResponse(response, payload);
	}
}
