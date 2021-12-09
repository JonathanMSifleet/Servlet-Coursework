package coreservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.FilmDAOSingleton;
import models.Film;
import utils.HandleHTTP;

@WebServlet("/updateFilm")
public class UpdateFilm extends HttpServlet {
	private static final long serialVersionUID = -909062916095173117L;

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("application/json");

		response = HandleHTTP.setHeaders(response, "PUT");

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();

		String requestBody = request.getReader().lines().collect(Collectors.joining());
		Film film = new Gson().fromJson(requestBody, Film.class);

		PrintWriter out = response.getWriter();
		out.print(filmDAO.updateFilm(film));
		out.flush();
	}

}
