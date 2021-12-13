package coreservlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import models.Film;
import utils.HandleHTTP;

@WebServlet("/getAllFilms")
public class GetAllFilms extends HttpServlet implements utils.HandleHTTP {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response = HandleHTTP.setHeaders(response, "GET");

		FilmDAOSingleton filmDAO = FilmDAOSingleton.getFilmDAO();
		ArrayList<Film> films = filmDAO.getAllFilms();

		String format = request.getParameter("format");
		if (format == null)
			format = "json";

		Object payload;

		switch (format) {
		case "json":
			response.setContentType("application/json");
			payload = HandleHTTP.handleJSON(films);
			break;
		case "xml":
			response.setContentType("text/xml");
			payload = HandleHTTP.handleXML(films);
			break;
		case "csv":
			response.setContentType("text/csv");
			payload = HandleHTTP.handleCSV(films);
			break;
		default:
			response.setContentType("application/json");
			payload = HandleHTTP.handleJSON(films);
			break;
		}

		HandleHTTP.sendResponse(response, payload);
	}

}