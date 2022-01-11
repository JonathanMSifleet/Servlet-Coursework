package coreservlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IRequestHelpers;

/**
 * Delete film servlet
 */
@WebServlet("/deleteFilm")
public class DeleteFilm extends HttpServlet implements interfaces.IRequestHelpers {
	private static final long serialVersionUID = -5107276414138521171L;

	/**
	 * Delete film functionality
	 *
	 * @param request  HTTP request
	 * @param response HTTP response
	 */
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
		// set relevant headers
		response = IRequestHelpers.setHeaders(response, "DELETE");
		
		// get id from url
		int id = Integer.parseInt(request.getParameter("id"));

		// print number of affected rows due to deleting film
		IRequestHelpers.sendResponse(response, FilmDAOSingleton.getFilmDAO().deleteFilm(id));
	}
}
