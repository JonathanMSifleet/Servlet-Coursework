package sharedUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import models.Film;

public interface ResponseFormatting {

	static void handleFormat(HttpServletRequest request, HttpServletResponse response, Object result) {
		String format = request.getParameter("format");
		if (format == null)
			format = "json";

		try {
			switch (format) {
			case "json":
				handleJSON(response, result);
				break;
			case "xml":
				handleXML(response, result);
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void handleJSON(HttpServletResponse response, Object data) throws IOException {
		response.setContentType("application/json");

		Gson gson = new Gson();
		PrintWriter out = response.getWriter();
		String jsonFilms = gson.toJson(data);

		out.print(jsonFilms);
		out.flush();
	}

	static void handleXML(HttpServletResponse response, Object data) throws IOException {
		response.setContentType("text/xml");

		PrintWriter out = response.getWriter();
		out.print(data);
		out.flush();
	}

}
