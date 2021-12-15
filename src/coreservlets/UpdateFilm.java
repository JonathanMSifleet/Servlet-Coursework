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
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException
			 {

		IHandleHTTP.setHeaders(response, "PUT");

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		String filmString = IMonoObjServletCommon.getRequestBody(request);
		String format = IGetFormat.getFormat(request);

		System.out.println("filmString " + filmString);

		Film film = switch (format) {
			case "json" -> IMonoObjServletCommon.jsonToFilm(filmString);
			case "xml" -> IMonoObjServletCommon.xmlToFilm(filmString);
			default -> null;
		};

		IHandleHTTP.sendResponse(response, filmDAO.updateFilm(film));
	}

}
