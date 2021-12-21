package interfaces;

import java.io.IOException;
import java.io.StringReader;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.google.gson.Gson;

import models.Film;

public interface IMonoObjServletCommon {

	static Film jsonToFilm(String jsonString, Boolean newFilm) {

		// if result is a new film then create a new film POJO
		// from JSON but generate new ID, otherwise create a
		// new film POJO from JSON using existing ID
		if (newFilm) {
			return new Film.Builder(new Gson().fromJson(jsonString, Film.class)).id(ISQLOperations.generateNewID()).build();
		} else {
			return new Film.Builder(new Gson().fromJson(jsonString, Film.class)).build();
		}

	}

	static Film xmlToFilm(String xmlString, Boolean newFilm) {

		try {
			// initialise new xml builder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlObject;

			// convert from string to XML
			xmlObject = builder.parse(new InputSource(new StringReader(xmlString)));
			Element root = xmlObject.getDocumentElement();

			// same as above jsonToFilm method except using XML rather than JSON
			if (newFilm) {
				return new Film.Builder(null).id(ISQLOperations.generateNewID())
						.title(root.getElementsByTagName("title").item(0).getTextContent())
						.year(Integer.valueOf(root.getElementsByTagName("year").item(0).getTextContent()))
						.director(root.getElementsByTagName("director").item(0).getTextContent())
						.stars(root.getElementsByTagName("stars").item(0).getTextContent())
						.review(root.getElementsByTagName("director").item(0).getTextContent()).build();
			} else {
				return new Film.Builder(null).id(Integer.valueOf(root.getElementsByTagName("id").item(0).getTextContent()))
						.title(root.getElementsByTagName("title").item(0).getTextContent())
						.year(Integer.valueOf(root.getElementsByTagName("year").item(0).getTextContent()))
						.director(root.getElementsByTagName("director").item(0).getTextContent())
						.stars(root.getElementsByTagName("stars").item(0).getTextContent())
						.review(root.getElementsByTagName("director").item(0).getTextContent()).build();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	static Film csvToFilm(String csvFilm, Boolean newFilm) {
		String[] filmAttributes = csvFilm.split(",,");

		// same as above jsonToFilm method except using CSV rather than JSON
		if (newFilm) {
			return new Film.Builder(null).id(ISQLOperations.generateNewID()).title(filmAttributes[0])
					.year(Integer.valueOf(filmAttributes[1])).director(filmAttributes[2]).stars(filmAttributes[3])
					.review(filmAttributes[4]).build();
		} else {
			return new Film.Builder(null).id(Integer.valueOf(filmAttributes[0])).title(filmAttributes[1])
					.year(Integer.valueOf(filmAttributes[2])).director(filmAttributes[3]).stars(filmAttributes[4])
					.review(filmAttributes[5]).build();
		}

	}

	static String getRequestBody(HttpServletRequest request) {
		// return request body as string
		try {
			return request.getReader().lines().collect(Collectors.joining());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
