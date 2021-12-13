package coreservlets;

import java.io.IOException;
import java.io.PrintWriter;
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

				String xmlString = "<object><attributeOne>test</attributeOne><attributeTwo>test2</attributeTwo></object>";

				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document xmlObject = builder.parse(new InputSource(new StringReader(xmlString)));

				// print document attributes:
				System.out.println("Root element: " + xmlObject.getDocumentElement().getNodeName());

				int numRootElements = xmlObject.getDocumentElement().getAttributes().getLength();
				System.out.println("Number of root elements: " + String.valueOf(numRootElements));

				// log root element attributes:
				System.out.println("Root element attributes: ");
				for (int i = 0; i < numRootElements; i++) {
					System.out.println(xmlObject.getDocumentElement().getAttributes().item(i).getNodeName() + ": "
							+ xmlObject.getDocumentElement().getAttributes().item(i).getNodeValue());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			break;
		}

		film.setId(SQLOperations.generateNewID());

		PrintWriter out = response.getWriter();
		out.print(filmDAO.insertFilm(film));
		out.flush();
	}

}