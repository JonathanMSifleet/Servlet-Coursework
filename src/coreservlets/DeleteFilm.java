package coreservlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IHandleHTTP;

@WebServlet("/deleteFilm")
public class DeleteFilm extends HttpServlet implements interfaces.IHandleHTTP {
	private static final long serialVersionUID = -5107276414138521171L;

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IHandleHTTP.setHeaders(response, "DELETE");
		// get id from url
		int id = Integer.parseInt(request.getParameter("id"));

		// print number of affected rows due to deleting film
		IHandleHTTP.sendResponse(response, new FilmDAOSingleton().deleteFilm(id));
	}
}
