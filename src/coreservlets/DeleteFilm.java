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

@WebServlet("/deleteFilm")
public class DeleteFilm extends HttpServlet {
	private static final long serialVersionUID = -5107276414138521171L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response = HandleHTTP.setHeaders(response, "DELETE");
		int id = Integer.parseInt(request.getParameter("ID"));

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		
		PrintWriter out = response.getWriter();
		out.print(filmDAO.deleteFilm(id));
		out.flush();
	}

}