package multiplePOJOFormatStrategy;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

import models.Film;

public class FilmsToXMLArray implements IMultiplePOJOFormatStrategy {
	/**
	 * Convert list of Film POJOs to XML
	 *
	 * @param data List of Film POJOs
	 * @return Film POJOs as XML
	 */
	@Override
	public String convertArrayToFormat(ArrayList<Film> data) {
		XStream xstream = new XStream();
		// change xml tag name from class name to value of
		// first function parameter
		xstream.alias("root", List.class);
		xstream.alias("film", models.Film.class);

		// convert films to XML
		String xmlString = xstream.toXML(data);

		// return XML and XML declaration
		return "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" + "\n" + xmlString;
	}

}
