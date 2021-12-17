package coreservlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IGetFormat;
import interfaces.IHandleHTTP;
import interfaces.IMonoObjServletCommon;
import models.Film;

@WebServlet("/updateFilm")
public class UpdateFilm extends HttpServlet
    implements interfaces.IHandleHTTP, interfaces.IMonoObjServletCommon, interfaces.IGetFormat {
	private static final long serialVersionUID = -909062916095173117L;

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response = IHandleHTTP.setHeaders(response, "PUT");

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		String filmString = IMonoObjServletCommon.getRequestBody(request);
		String format = IGetFormat.getFormat(request);

		Film film = switch (format) {
			case "xml" -> IMonoObjServletCommon.xmlToFilm(filmString, false);
			case "csv" -> IMonoObjServletCommon.csvToFilm(filmString, false);
			default -> IMonoObjServletCommon.jsonToFilm(filmString, false);
		};

		IHandleHTTP.sendResponse(response, filmDAO.updateFilm(film));
	}

}
