package interfaces;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import models.Film;

public interface IPolyObjServletCommon {

	static String filmsToJSONArray(ArrayList<Film> data) {
		Gson gson = new Gson();
		return gson.toJson(data);
	}

	static String filmsToXMLArray(ArrayList<Film> data) {
		XStream xstream = new XStream();
		xstream.alias("root", List.class);
		xstream.alias("film", models.Film.class);

		String xmlString = xstream.toXML(data);
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "\n" + xmlString;
	}

	static String filmsToCSVArray(ArrayList<Film> data) {

		String films = "";

		for (Film film : data) {
			films += film.toString() + "\n";
		}

		return films;
	}

}
