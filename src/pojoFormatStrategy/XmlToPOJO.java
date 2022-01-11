package pojoFormatStrategy;

import com.thoughtworks.xstream.XStream;

import dao.FilmDAOSingleton;
import models.Film;
import models.Film.Builder;

public class XmlToPOJO implements PojoFormatStrategy {
	/**
	 * Converts XML to Film POJO
	 *
	 * @param xmlString Film as XML
	 * @param newFilm   Boolean specifying whether new ID for film should be
	 *                  generated
	 * @return Film POJO
	 */
	@Override
	public Film convertToPOJO(String xmlString, Boolean newFilm) {
		XStream xstream = new XStream();
		xstream.allowTypes(new Class[] { Film.class });
		xstream.processAnnotations(Film.class);

		Builder film = new Film.Builder((Film) xstream.fromXML(xmlString));

		if (newFilm) film.id(FilmDAOSingleton.getFilmDAO().generateNewID());

		return film.build();
	}

}
