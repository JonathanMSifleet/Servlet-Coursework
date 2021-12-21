package coreservlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import dao.FilmDAOSingleton;
import interfaces.IGetFormat;
import interfaces.IHandleHTTP;
import models.Film;

@WebServlet("/getFilmByID")
public class GetFilmByID extends HttpServlet implements interfaces.IHandleHTTP, interfaces.IGetFormat {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		// set relevant headers
		response = IHandleHTTP.setHeaders(response, "GET");

		// get ID from URL
		int id = Integer.parseInt(request.getParameter("id"));

		// get film by ID from data access object
		Film film = new FilmDAOSingleton().getFilmByID(id);
		// get format from URL
		String format = IGetFormat.getFormat(request);

		Object payload;
		// format film based upon relevant format
		switch (format) {
			case "xml" -> {
				response.setContentType("text/xml");
				XStream xstream = new XStream();
				xstream.alias("film", Film.class);
				payload = xstream.toXML(film);
			}
			case "csv" -> {
				response.setContentType("text/csv");
				payload = film.getId() + ",," + film.getTitle() + ",," + film.getYear() + ",," + film.getDirector() + ",,"
						+ film.getStars() + ",," + film.getReview();
			}
			default -> {
				response.setContentType("application/json");
				payload = new Gson().toJson(film);
			}
		}

		// send formatted film as response
		try {
			IHandleHTTP.sendResponse(response, payload);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
