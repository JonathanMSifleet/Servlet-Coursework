package interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import models.Film;

public interface IFormatToPOJO {

	public static final Logger logger = LoggerFactory.getLogger(IFormatToPOJO.class);

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
		XStream xstream = new XStream();
		xstream.allowTypes(new Class[] { Film.class });
		xstream.processAnnotations(Film.class);

		try {
			if (newFilm) {
				return new Film.Builder((Film) xstream.fromXML(xmlString)).id(ISQLOperations.generateNewID()).build();
			} else {
				return new Film.Builder((Film) xstream.fromXML(xmlString)).build();
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

}
