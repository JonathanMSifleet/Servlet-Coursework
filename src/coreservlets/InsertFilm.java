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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		// set relevant headers
		response = IHandleHTTP.setHeaders(response, "POST");

		// get film from HTTP body
		String requestBodyFilm = IMonoObjServletCommon.getRequestBody(request);
		// get format from url
		String format = IGetFormat.getFormat(request);

		// set film equal to film object converted based on
		// relevant format
		Film film = switch (format) {
			case "xml" -> IMonoObjServletCommon.xmlToFilm(requestBodyFilm, true);
			case "csv" -> IMonoObjServletCommon.csvToFilm(requestBodyFilm, true);
			default -> IMonoObjServletCommon.jsonToFilm(requestBodyFilm, true);
		};

		// send number of affected rows as a result of inserting film
		try {
			IHandleHTTP.sendResponse(response, new FilmDAOSingleton().insertFilm(film));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}