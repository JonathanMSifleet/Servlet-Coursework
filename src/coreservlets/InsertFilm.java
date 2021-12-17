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

@WebServlet("/insertFilm")
public class InsertFilm extends HttpServlet implements interfaces.IHandleHTTP, interfaces.ISQLOperations,
    interfaces.IMonoObjServletCommon, interfaces.IGetFormat {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response = IHandleHTTP.setHeaders(response, "POST");

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		String filmString = IMonoObjServletCommon.getRequestBody(request);
		String format = IGetFormat.getFormat(request);

		Film film = switch (format) {
			case "xml" -> IMonoObjServletCommon.xmlToFilm(filmString, true);
			case "csv" -> IMonoObjServletCommon.csvToFilm(filmString);
			default -> IMonoObjServletCommon.jsonToFilm(filmString, true);
		};

		IHandleHTTP.sendResponse(response, filmDAO.insertFilm(film));

	}

}