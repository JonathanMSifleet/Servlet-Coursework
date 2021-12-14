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

public interface IServletCommon {
	
	public static Film jsonToFilm(String jsonString) {
		return new Film.Builder(new Gson().fromJson(jsonString, Film.class)).id(ISQLOperations.generateNewID())
				.build();
	}

	public static Film xmlToFilm(String xmlString) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xmlObject;

			xmlObject = builder.parse(new InputSource(new StringReader(xmlString)));
			Element root = xmlObject.getDocumentElement();

			return new Film.Builder(null).id(ISQLOperations.generateNewID())
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
	
	public static String getFormat(HttpServletRequest request) {
		String format = request.getParameter("format");
		if (format == null)
			format = "json";
		
		return format;
	}
	
	public static String getRequestBody(HttpServletRequest request) {
		try {
			return request.getReader().lines().collect(Collectors.joining());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
