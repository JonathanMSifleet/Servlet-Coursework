package coreservlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IHandleHTTP;
import interfaces.IPolyObjServletCommon;
import interfaces.IGetFormat;
import models.Film;

@WebServlet("/getAllFilms")
public class GetAllFilms extends HttpServlet
		implements interfaces.IHandleHTTP, interfaces.IGetFormat, interfaces.IPolyObjServletCommon {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IHandleHTTP.setHeaders(response, "GET");

		// get all films from data access object
		ArrayList<Film> films = new FilmDAOSingleton().getAllFilms();
		// get format from url
		String format = IGetFormat.getFormat(request);

		Object payload;
		// convert film array list to relevant data type
		// and set appropriate header for HTTP response
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
		
		// send response containing formatted list of all films
		IHandleHTTP.sendResponse(response, payload);
	}
}
