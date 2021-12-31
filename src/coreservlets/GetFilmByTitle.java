package coreservlets;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IRequestHelpers;
import interfaces.IPolyObjServletCommon;
import models.Film;

@WebServlet("/getFilmByTitle")
public class GetFilmByTitle extends HttpServlet implements interfaces.IRequestHelpers {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// set relevant headers
		response = IRequestHelpers.setHeaders(response, "GET");

		// get film title from url
		String title = request.getParameter("title");

		// get films with title containing title from url
		ArrayList<Film> films = new FilmDAOSingleton().getFilmsByTitle(title);
		// get format from url
		String format = IRequestHelpers.getFormat(request);

		Object payload;

		// convert film array list to relevant data type
		// and set appropriate header for HTTP response
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

		// send response containing formatted list of films
		IRequestHelpers.sendResponse(response, payload);
	}
}
