package coreservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IHandleHTTP;
import interfaces.IServletCommon;
import models.Film;

@WebServlet("/updateFilm")
public class UpdateFilm extends HttpServlet implements interfaces.IHandleHTTP {
	private static final long serialVersionUID = -909062916095173117L;

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response = IHandleHTTP.setHeaders(response, "PUT");

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		String requestBody = request.getReader().lines().collect(Collectors.joining());

		System.out.println("Request Body: " + requestBody);

		String format = request.getParameter("format");
		if (format == null)
			format = "json";

		Film film = null;

		switch (format) {
		case "json":
			film = IServletCommon.jsonToFilm(requestBody);
			break;
		case "xml":
			film = IServletCommon.xmlToFilm(requestBody);
			break;
		}

		PrintWriter out = response.getWriter();
		out.print(filmDAO.updateFilm(film));
		out.flush();
	}

}
