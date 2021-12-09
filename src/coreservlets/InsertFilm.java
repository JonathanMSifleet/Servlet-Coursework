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
import utils.SQLOperations;

@WebServlet("/insertFilm")
public class InsertFilm extends HttpServlet {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response = HandleHTTP.setHeaders(response, "POST");

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();

		String requestBody = request.getReader().lines().collect(Collectors.joining());
		Film film = new Gson().fromJson(requestBody, Film.class);
		film.setId(SQLOperations.generateNewID());

		PrintWriter out = response.getWriter();
		out.print(filmDAO.insertFilm(film));
		out.flush();
	}

}