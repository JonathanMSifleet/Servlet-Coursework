package coreservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.FilmDAO;
import models.Film;

@WebServlet("/insertFilm")
public class InsertFilm extends HttpServlet {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		response.setHeader("Cache-Control", "no-cache");
//		response.setHeader("Pragma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
//		response.setContentType("text/plain");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		FilmDAO filmDAO = new FilmDAO();

		Film dummyFilm = filmDAO.generateDummyFilm();
		int result = filmDAO.insertFilm(dummyFilm);

		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		String jsonFilms = gson.toJson(result);

		out.print(jsonFilms);
		out.flush();
	}

}