package coreservlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import dao.FilmDAOSingleton;
import interfaces.IRequestHelpers;
import models.Film;

/**
 * Get film by ID servlet
 */
@WebServlet("/getFilmByID")
public class GetFilmByID extends HttpServlet implements interfaces.IRequestHelpers {
	private static final long serialVersionUID = -1809220141023596490L;

	/**
	 * Get film by ID functionality
	 *
	 * @param request  HTTP request
	 * @param response HTTP response
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// set relevant headers
		response = IRequestHelpers.setHeaders(response, "GET");

		// get ID from URL
		int id = Integer.parseInt(request.getParameter("id"));

		// get film by ID from data access object
		Film film = FilmDAOSingleton.getFilmDAO().getFilmByID(id);
		// get format from URL
		String format = IRequestHelpers.getFormat(request);

		Object payload;
		// format film based upon relevant format
		switch (format) {
			case "xml":
				response.setContentType("text/xml");
				XStream xstream = new XStream();
				xstream.alias("film", Film.class);
				payload = xstream.toXML(film);
				break;
			case "csv":
				response.setContentType("text/csv");
				payload = film.getId() + ",," + film.getTitle() + ",," + film.getYear() + ",," + film.getDirector() + ",,"
						+ film.getStars() + ",," + film.getReview();
				break;
			default:
				response.setContentType("application/json");
				payload = new Gson().toJson(film);
		}

		// send response containing formatted film
		IRequestHelpers.sendResponse(response, payload);
	}
}
