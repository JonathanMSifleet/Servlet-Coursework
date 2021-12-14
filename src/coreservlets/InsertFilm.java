package coreservlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.SQLException;
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
import com.thoughtworks.xstream.XStream;

import dao.FilmDAOSingleton;
import models.Film;
import utils.HandleHTTP;
import utils.SQLOperations;

@WebServlet("/insertFilm")
public class InsertFilm extends HttpServlet implements utils.HandleHTTP {
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

		Film film = new Film();

		switch (format) {
		case "json":
			film = new Gson().fromJson(requestBody, Film.class);
			break;
		case "xml":
			try {
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document xmlObject = builder.parse(new InputSource(new StringReader(requestBody)));

				Element root = xmlObject.getDocumentElement();
				film.setTitle(root.getElementsByTagName("title").item(0).getTextContent());
				film.setYear(Integer.valueOf(root.getElementsByTagName("year").item(0).getTextContent()));
				film.setDirector(root.getElementsByTagName("director").item(0).getTextContent());
				film.setStars(root.getElementsByTagName("stars").item(0).getTextContent());
				film.setReview(root.getElementsByTagName("review").item(0).getTextContent());

				System.out.println(film.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		}

		try {
			int id = SQLOperations.generateNewID();
			if (id == -1)
				throw new Exception("Invalid SQL result");

			film.setId(id);

			PrintWriter out = response.getWriter();
			out.print(filmDAO.insertFilm(film));
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}