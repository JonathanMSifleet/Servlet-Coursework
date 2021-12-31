package interfaces;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IHandleHTTP {
	
	static void sendResponse(javax.servlet.http.HttpServletResponse response, Object payload) {
		// send value of payload parameter
		// as HTTP response
		try {
			PrintWriter out = response.getWriter();
			out.print(payload);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	static HttpServletResponse setHeaders(javax.servlet.http.HttpServletResponse response, String method) {
		// set response headers
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Allow-Methods", method);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setCharacterEncoding("UTF-8");

		return response;
	}
	
	static String getRequestBody(HttpServletRequest request) {
		// return request body as string
		try {
			return request.getReader().lines().collect(Collectors.joining());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
