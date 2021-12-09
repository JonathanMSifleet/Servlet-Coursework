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

@WebServlet("/getFilmByID")
public class GetFilmByID extends HttpServlet {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response = HandleHTTP.setHeaders(response, "GET");

		int id = Integer.parseInt(request.getParameter("ID"));

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		ArrayList<Film> films = filmDAO.getFilmByID(id);

		HandleHTTP.handleFormat(request, response, films);
	}

}