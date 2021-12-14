package interfaces;

public interface IGetFormat {
	static String getFormat(javax.servlet.http.HttpServletRequest request) {
		String format = request.getParameter("format");
		if (format == null)
			format = "json";

		return format;
	}
}
