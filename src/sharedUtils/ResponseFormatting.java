package sharedUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

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
		String jsonFilms = gson.toJson(data);

		PrintWriter out = response.getWriter();
		out.print(jsonFilms);
		out.flush();
	}

	static void handleXML(HttpServletResponse response, Object data) throws IOException {
		response.setContentType("text/xml");

		XStream xstream = new XStream();
		xstream.alias("root", List.class);
		xstream.alias("film", models.Film.class);

		String xmlString = xstream.toXML(data);

		xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\">" + "\n" + xmlString;

		PrintWriter out = response.getWriter();
		out.print(xmlString);
		out.flush();
	}

}
