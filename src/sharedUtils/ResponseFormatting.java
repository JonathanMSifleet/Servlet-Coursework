package sharedUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import models.Film;

public interface ResponseFormatting {

	static void handleJSON(HttpServletResponse response, ArrayList<Film> films) throws IOException {
		response.setContentType("application/json");

		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		String jsonFilms = gson.toJson(films);

		out.print(jsonFilms);
		out.flush();
	}

	static void handleXML(HttpServletResponse response, ArrayList<Film> films) throws IOException {
		response.setContentType("text/xml");

		PrintWriter out = response.getWriter();
		out.print(films);
		out.flush();
	}

}
