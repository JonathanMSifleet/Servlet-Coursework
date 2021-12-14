package interfaces;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import models.Film;

public interface IHandleHTTP {

//	static String handleCSV(ArrayList<Film> data) throws IOException {
//		String csvString = "";
//		for (Film film : data) {
//			csvString += film.toString();
//			csvString += "\n";
//		}
//
//		return csvString;
//	}

	static void sendResponse(HttpServletResponse response, Object payload) throws IOException {
		PrintWriter out = response.getWriter();
		out.print(payload);
		out.flush();
	}

	static HttpServletResponse setHeaders(HttpServletResponse response, String method) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Allow-Methods", method);
		response.setCharacterEncoding("UTF-8");

		return response;
	}

}
