package multiplePOJOFormatStrategy;

import java.util.ArrayList;

import com.google.gson.Gson;

import models.Film;

public class FilmsToJSONArray implements IMultiplePOJOFormatStrategy {
	/**
	 * Returns list of Film POJOs to JSON
	 *
	 * @param data List of Film POJOs
	 * @return Film POJOs as JSON
	 */
	@Override
	public String convertArrayToFormat(ArrayList<Film> data) {
		return new Gson().toJson(data);
	}

}
