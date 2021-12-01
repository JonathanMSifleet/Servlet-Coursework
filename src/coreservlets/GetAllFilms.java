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

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "PUT, POST, GET, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
//		response.setContentType("text/plain");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

//		response.setContentType("text/xml");

		FilmDAO filmDAO = new FilmDAO();

		ArrayList<Film> allFilms = new ArrayList<>();
		allFilms.addAll(filmDAO.loadAllFilms());

		Gson gson = new Gson();
		String jsonFilms = gson.toJson(allFilms);
		System.out.println(jsonFilms);
		
		response.getWriter().write(jsonFilms);
	}

	// request.setAttribute("testJsonString", testJsonString);

	// String format = request.getParameter("format");
	// String outputPage = "";

	// if ("xml".equals(format)) {
	// response.setContentType("text/xml");
	// String outputPage = "/WEB-INF/outputXML.jsp";
	// // }
	// RequestDispatcher dispatcher = request.getRequestDispatcher(outputPage);
	// dispatcher.forward(request, response);
	
	@Override
	protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setHeader("Access-Control-Allow-Origin", "*");
	    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");   
	    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
	    response.setStatus(HttpServletResponse.SC_OK);
	}

}