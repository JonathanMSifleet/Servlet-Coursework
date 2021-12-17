package coreservlets;

import java.io.IOException;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response = IHandleHTTP.setHeaders(response, "GET");

		FilmDAOSingleton filmDAO = FilmDAOSingleton.getFilmDAO();
		ArrayList<Film> films = filmDAO.getAllFilms();
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