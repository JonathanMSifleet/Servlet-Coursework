package coreservlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import utils.HandleHTTP;

@WebServlet("/deleteFilm")
public class DeleteFilm extends HttpServlet {
	private static final long serialVersionUID = -5107276414138521171L;

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response = HandleHTTP.setHeaders(response, "DELETE");
		int id = Integer.parseInt(request.getParameter("id"));

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();

		PrintWriter out = response.getWriter();
		out.print(filmDAO.deleteFilm(id));
		out.flush();
	}

}