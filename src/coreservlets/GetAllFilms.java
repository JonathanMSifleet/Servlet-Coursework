package coreservlets;

import dao.*;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import com.google.gson.Gson;

import models.Film;

@WebServlet("/getAllFilms")
public class GetAllFilms extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		response.setHeader("Cache-Control", "no-cache");
//		response.setHeader("Pragma", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
//		response.setContentType("text/plain");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		FilmDAO filmDAO = new FilmDAO();
		ArrayList<Film> allFilms = filmDAO.getAllFilms();
		System.out.println(allFilms);
	
		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		String jsonFilms = gson.toJson(allFilms);

		out.print(jsonFilms);
		out.flush();
	}

}