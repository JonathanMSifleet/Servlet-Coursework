package coreservlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import models.Film;


@WebServlet("/getAllFilms")
public class GetAllFilms extends HttpServlet {

	private Gson gson = new Gson();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");

//		response.setContentType("text/xml");

		Film film = new Film();
				
		String testJsonString = "Test";
		
//		String testJsonString = this.gson.toJson(film);

		PrintWriter out = response.getWriter();
        out.print(testJsonString);
        out.flush();  
		
        //		request.setAttribute("testJsonString", testJsonString);

		// String format = request.getParameter("format");
		// String outputPage = "";

		// if ("xml".equals(format)) {
		// response.setContentType("text/xml");
		//		String outputPage = "/WEB-INF/outputXML.jsp";
		//		// }
		//		RequestDispatcher dispatcher = request.getRequestDispatcher(outputPage);
		//		dispatcher.forward(request, response);
	}

}