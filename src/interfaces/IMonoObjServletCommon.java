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

	static Film jsonToFilm(String jsonString) {
		return new Film.Builder(new Gson().fromJson(jsonString, Film.class)).build();
	}

	static Film xmlToFilm(String xmlString) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlObject;

			xmlObject = builder.parse(new InputSource(new StringReader(xmlString)));
			Element root = xmlObject.getDocumentElement();

			return new Film.Builder(null).id(Integer.valueOf(root.getElementsByTagName("id").item(0).getTextContent()))
					.title(root.getElementsByTagName("title").item(0).getTextContent())
					.year(Integer.valueOf(root.getElementsByTagName("year").item(0).getTextContent()))
					.director(root.getElementsByTagName("director").item(0).getTextContent())
					.stars(root.getElementsByTagName("stars").item(0).getTextContent())
					.review(root.getElementsByTagName("director").item(0).getTextContent()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static String getRequestBody(HttpServletRequest request) {
		try {
			return request.getReader().lines().collect(Collectors.joining());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
