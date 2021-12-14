package coreservlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IHandleHTTP;
import models.Film;

@WebServlet("/getFilmByID")
public class GetFilmByID extends HttpServlet implements interfaces.IHandleHTTP {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response = IHandleHTTP.setHeaders(response, "GET");

		int id = Integer.parseInt(request.getParameter("id"));

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		ArrayList<Film> films = filmDAO.getFilmByID(id);

		String format = request.getParameter("format");
		if (format == null)
			format = "json";

		Object payload;

		switch (format) {
		case "json":
			response.setContentType("application/json");
			payload = IHandleHTTP.handleJSON(films);
			break;
		case "xml":
			response.setContentType("text/xml");
			payload = IHandleHTTP.handleXML(films);
			break;
		case "csv":
			response.setContentType("text/csv");
			payload = IHandleHTTP.handleCSV(films);
			break;
		default:
			response.setContentType("application/json");
			payload = IHandleHTTP.handleJSON(films);
			break;
		}

		IHandleHTTP.sendResponse(response, payload);
	}

}