package coreservlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;

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
		response.setCharacterEncoding("UTF-8");

//		response.setContentType("text/xml");

		Connection conn = null;
		String dbName = "";
		String url = "jdbc:mysql://localhost:3306/eecoursework?characterEncoding=utf8" + dbName;
		String username = "root";
		String password = "Phantom2011!";

		String testJsonString = "";

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();

			conn = DriverManager.getConnection(url, username, password);
			Statement stmt = (Statement) conn.createStatement();
			String SQL = "SELECT * FROM eecoursework.films LIMIT 100";
			ResultSet rs = (ResultSet) stmt.executeQuery(SQL);

			ArrayList<Film> allFilms = new ArrayList<Film>();

			while (rs.next()) {

				Film newFilm = new Film();

				newFilm.setId((int) rs.getObject(1));
				newFilm.setTitle((String) rs.getObject(2));
				newFilm.setYear((int) rs.getObject(3));
				newFilm.setDirector((String) rs.getObject(4));
				newFilm.setStars((String) rs.getObject(5));
				newFilm.setReview((String) rs.getObject(6));

				allFilms.add(newFilm);

			}

			System.err.println("Connected to db");

//			Film film = new Film();

			testJsonString = "test";

			PrintWriter out = response.getWriter();
			out.print(this.gson.toJson(testJsonString));
			out.flush();

		} catch (Exception e) {
			System.out.println(e);
//			// handle any errors
//			System.out.println("SQLException: " + ex.getMessage());
//			System.out.println("SQLState: " + ex.getSQLState());
//			System.out.println("VendorError: " + ex.getErrorCode());
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
	}

}