package coreservlets;

import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.google.gson.Gson;

import dao.FilmDAOSingleton;
import models.Film;
import utils.HandleHTTP;
import utils.SQLOperations;

@WebServlet("/insertFilm")
public class InsertFilm extends HttpServlet implements utils.HandleHTTP, utils.SQLOperations {
	private static final long serialVersionUID = -1809220141023596490L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response = HandleHTTP.setHeaders(response, "POST");

		FilmDAOSingleton filmDAO = new FilmDAOSingleton();
		String requestBody = request.getReader().lines().collect(Collectors.joining());

		String format = request.getParameter("format");
		if (format == null)
			format = "json";

		Film film = null;

		switch (format) {
		case "json":
			film = new Film.FilmBuilder(new Gson().fromJson(requestBody, Film.class)).id(SQLOperations.generateNewID())
					.build();
			break;
		case "xml":
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document xmlObject;

				xmlObject = builder.parse(new InputSource(new StringReader(requestBody)));
				Element root = xmlObject.getDocumentElement();

				film = new Film.FilmBuilder(null)
						.id(SQLOperations.generateNewID())
						.title(root.getElementsByTagName("title").item(0).getTextContent())
						.year(Integer.valueOf(root.getElementsByTagName("year").item(0).getTextContent()))
						.director(root.getElementsByTagName("director").item(0).getTextContent())
						.stars(root.getElementsByTagName("stars").item(0).getTextContent())
						.review(root.getElementsByTagName("director").item(0).getTextContent()).build();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}

		try {
			if (film.getId() == -1)
				throw new Exception("Invalid SQL result");

			HandleHTTP.sendResponse(response, filmDAO.insertFilm(film));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}