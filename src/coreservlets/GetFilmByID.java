package coreservlets;

import java.io.IOException;

import javax.servlet.ServletException;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response = IHandleHTTP.setHeaders(response, "GET");

		int id = Integer.parseInt(request.getParameter("id"));

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		Film film = filmDAO.getFilmByID(id);
		String format = IGetFormat.getFormat(request);

		Object payload;

		Gson gson = new Gson();

		switch (format) {
		case "json":
			response.setContentType("application/json");
			payload = gson.toJson(film);
			break;
		case "xml":
			response.setContentType("text/xml");
			XStream xstream = new XStream();
			xstream.alias("film", models.Film.class);

			String xmlString = xstream.toXML(film);

			payload = xmlString;
			break;
//		case "csv":
//			response.setContentType("text/csv");
//			payload = IPolyObjServletCommon.handleCSV(films);
//			break;
		default:
			response.setContentType("application/json");
			payload = gson.toJson(film);
			break;
		}

		IHandleHTTP.sendResponse(response, payload);
	}

}