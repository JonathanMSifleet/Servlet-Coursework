package coreservlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAO;
import models.Film;
import sharedUtils.ResponseFormatting;

@WebServlet("/getAllFilms")
public class GetAllFilms extends HttpServlet {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setCharacterEncoding("UTF-8");

		FilmDAO filmDAO = new FilmDAO();
		ArrayList<Film> films = filmDAO.getAllFilms();

		ResponseFormatting.handleFormat(request, response, films);
	}

}