package interfaces;

import javax.servlet.http.HttpServletRequest;

public interface IGetFormat {
	static String getFormat(HttpServletRequest request) {
		String format = request.getParameter("format");
		if (format == null)
			format = "json";

		return format;
	}
}
