package interfaces;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import models.Film;

public interface IPolyObjServletCommon {

	static String filmsToJSONArray(ArrayList<Film> data) {
		// return array list of films to JSON
		return new Gson().toJson(data);
	}

	static String filmsToXMLArray(ArrayList<Film> data) {
		// convert films to XML

		XStream xstream = new XStream();
		// change xml tag name from class name to value of
		// first function parameter
		xstream.alias("root", List.class);
		xstream.alias("film", models.Film.class);
		
		// convert films to XML
		String xmlString = xstream.toXML(data);
		
		// return XML and XML declaration
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "\n" + xmlString;
	}

	static String filmsToCSVArray(ArrayList<Film> data) {

		String films = "";
		
		// use film model's toString function to convert
		// film's attributes to CSV string
		for (Film film : data) {
			films += film.toString() + "\n";
		}

		// remove additional line terminator, and return CSV film array
		return films.substring(0, films.length() - 2);
	}

}
