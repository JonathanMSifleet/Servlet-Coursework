package interfaces;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRequestHelpers {

	/**
	 * Send value of payload as a HTTP Response
	 * 
	 * @param response servlet response
	 * @param payload  body of HTTP request to send
	 */
	static void sendResponse(javax.servlet.http.HttpServletResponse response, Object payload) {
		try {
			PrintWriter out = response.getWriter();
			out.print(payload);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Sets headers for HTTP response
	 * 
	 * @param response servlet response
	 * @param method   HTTP method to be used
	 * @return response with set headers
	 */
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

	/**
	 * Gets HTTP request body
	 * 
	 * @param request HTTP request
	 * @return body of HTTP request
	 */
	static String getRequestBody(HttpServletRequest request) {
		// return request body as string
		try {
			return request.getReader().lines().collect(Collectors.joining());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Gets format from URL parameter
	 * 
	 * @param request servlet request
	 * @return format from URL parameter
	 */
	static String getFormat(javax.servlet.http.HttpServletRequest request) {
		String format = request.getParameter("format");
		// set default format as JSON
		if (format == null) return "json";

		// return format
		return format;
	}

}
