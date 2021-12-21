package interfaces;

public interface IGetFormat {
	static String getFormat(javax.servlet.http.HttpServletRequest request) {
		// get format from URL
		String format = request.getParameter("format");
		// set default format as JSON
		if (format == null)
			format = "json";

		// return format
		return format;
	}
}
