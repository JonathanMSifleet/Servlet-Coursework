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
import utils.HandleResponse;

@WebServlet("/getFilmByTitle")
public class GetFilmByTitle extends HttpServlet {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setCharacterEncoding("UTF-8");

		String title = request.getParameter("title");

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		ArrayList<Film> films = filmDAO.getFilm(title);

		HandleResponse.handleFormat(request, response, films);
	}

}