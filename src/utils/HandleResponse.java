package utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import models.Film;

public class HandleResponse {

	public static void handleFormat(HttpServletRequest request, HttpServletResponse response, ArrayList<Film> result) {
		String format = request.getParameter("format");
		if (format == null)
			format = "json";

		Object payload = null;

		try {

			switch (format) {
			case "json":
				response.setContentType("application/json");
				payload = handleJSON(result);
				break;
			case "xml":
				response.setContentType("text/xml");
				payload = handleXML(result);
				break;
			case "csv":
				response.setContentType("text/csv");
				payload = handleCSV(result);
				break;
			}

			sendResponse(response, payload);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String handleJSON(ArrayList<Film> data) throws IOException {
		Gson gson = new Gson();
		return gson.toJson(data);
	}

	private static String handleXML(ArrayList<Film> data) throws IOException {
		XStream xstream = new XStream();
		xstream.alias("root", List.class);
		xstream.alias("film", models.Film.class);

		String xmlString = xstream.toXML(data);
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "\n" + xmlString;
	}

	private static String handleCSV(ArrayList<Film> data) throws IOException {
		String csvString = "";
		for (Film film : (ArrayList<Film>) data) {
			csvString += film.getObjectValues(film);
			csvString += "\n";
		}

		return csvString;
	}

	private static void sendResponse(HttpServletResponse response, Object payload) throws IOException {
		PrintWriter out = response.getWriter();
		out.print(payload);
		out.flush();
	}

}
