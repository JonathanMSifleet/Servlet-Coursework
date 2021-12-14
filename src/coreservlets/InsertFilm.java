package coreservlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IHandleHTTP;
import interfaces.IServletCommon;
import models.Film;

@WebServlet("/insertFilm")
public class InsertFilm extends HttpServlet
		implements interfaces.IHandleHTTP, interfaces.ISQLOperations, interfaces.IServletCommon {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response = IHandleHTTP.setHeaders(response, "POST");

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		String requestBody = IServletCommon.getRequestBody(request);
		String format = IServletCommon.getFormat(request);

		Film film = null;

		switch (format) {
		case "json":
			film = IServletCommon.jsonToFilm(requestBody);
			break;
		case "xml":
			film = IServletCommon.xmlToFilm(requestBody);
			break;
		}

		try {
			if (film.getId() != -1)
				IHandleHTTP.sendResponse(response, filmDAO.insertFilm(film));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}