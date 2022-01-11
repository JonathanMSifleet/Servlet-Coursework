package coreservlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FilmDAOSingleton;
import interfaces.IRequestHelpers;
import models.Film;
import strategies.CsvToPOJO;
import strategies.JsonToPOJO;
import strategies.XmlToPOJO;
import strategyContexts.PojoFormatContext;

/**
 * Update Film Servlet
 */
@WebServlet("/updateFilm")
public class UpdateFilm extends HttpServlet implements interfaces.IRequestHelpers {
	private static final long serialVersionUID = -909062916095173117L;

	/**
	 * Servlet with Update film functionality
	 *
	 * @param request  HTTP request
	 * @param response HTTP response
	 */
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) {

		// set relevant headers
		response = IRequestHelpers.setHeaders(response, "PUT");

		// get format from url
		String format = IRequestHelpers.getFormat(request);
		
		// get film from HTTP body
		String requestBodyFilm = IRequestHelpers.getRequestBody(request);

		// set film equal to film object converted based on
		// relevant format
		Film film = formatFilm(format, requestBodyFilm);

		// print number of affected rows due to updating film
		IRequestHelpers.sendResponse(response, FilmDAOSingleton.getFilmDAO().updateFilm(film));
	}

	private Film formatFilm(String format, String requestBodyFilm) {
		switch (format) {
			case "xml":
				return new PojoFormatContext(new XmlToPOJO()).convertToPOJO(requestBodyFilm, true);
			case "csv":
				return new PojoFormatContext(new CsvToPOJO()).convertToPOJO(requestBodyFilm, true);
			default:
				return new PojoFormatContext(new JsonToPOJO()).convertToPOJO(requestBodyFilm, true);
		}
	}
}
