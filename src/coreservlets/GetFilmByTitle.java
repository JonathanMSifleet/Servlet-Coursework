package coreservlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IGetFormat;
import interfaces.IHandleHTTP;
import interfaces.IPolyObjServletCommon;
import models.Film;

@WebServlet("/getFilmByTitle")
public class GetFilmByTitle extends HttpServlet implements interfaces.IHandleHTTP, interfaces.IGetFormat {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response = IHandleHTTP.setHeaders(response, "GET");

		String title = request.getParameter("title");

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		ArrayList<Film> films = filmDAO.getFilmByTitle(title);
		String format = IGetFormat.getFormat(request);

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

		IHandleHTTP.sendResponse(response, payload);
	}

}