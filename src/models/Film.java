package models;

import java.util.ArrayList;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Film")
public class Film {
	private final int id;
	private final String title;
	private final int year;
	private final String director;
	private final String stars;
	private final String review;

	/**
	 * constructor sets film's attributes equal to builder's attributes
	 * 
	 * @param builder the builder
	 */
	private Film(Builder builder) {

		this.id = builder.id;
		this.title = builder.title;
		this.year = builder.year;
		this.director = builder.director;
		this.stars = builder.stars;
		this.review = builder.review;
	}

	/**
	 * Gets the film's id.
	 *
	 * @return the film's id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the film's title.
	 *
	 * @return the film's title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Gets the film's year.
	 *
	 * @return the film's year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * Gets the film's director.
	 *
	 * @return the film's director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * Gets the film's actors.
	 *
	 * @return the film's actors
	 */
	public String getStars() {
		return stars;
	}

	/**
	 * Gets the film's review.
	 *
	 * @return the film's review
	 */
	public String getReview() {
		return review;
	}

	/**
	 * Implementation of Builder Pattern for Film model
	 */
	public static class Builder {

		private int id;
		private String title;
		private int year;
		private String director;
		private String stars;
		private String review;

		/**
		 * Due to object's created using the Builder pattern, being immutable, by
		 * passing an existing film to the Builder's constructor, attributes can be
		 * overwritten, by passing the film, and chaining the setter method for the
		 * attribute that you wish to overwrite
		 *
		 * @param film the film
		 */
		public Builder(Film film) {
			if (film != null) {
				this.id = film.getId();
				this.title = film.getTitle();
				this.year = film.getYear();
				this.director = film.getDirector();
				this.stars = film.getStars();
				this.review = film.getReview();
			}
		}

		/**
		 * Id.
		 * 
		 * @param id film's id
		 * @return sets the film's id
		 */
		public Builder id(int id) {
			this.id = id;
			return this;
		}

		/**
		 * Title.
		 *
		 * @param title film's title
		 * @return sets the film's title
		 */
		public Builder title(String title) {
			this.title = title;
			return this;
		}

		/**
		 * Year.
		 *
		 * @param year film's year
		 * @return sets the film's year
		 */
		public Builder year(int year) {
			this.year = year;
			return this;
		}

		/**
		 * Director.
		 *
		 * @param director film's director
		 * @return sets the film's director
		 */
		public Builder director(String director) {
			this.director = director;
			return this;
		}

		/**
		 * Stars.
		 *
		 * @param stars film's stars
		 * @return sets the film's stars
		 */
		public Builder stars(String stars) {
			this.stars = stars;
			return this;
		}

		/**
		 * @param review film's review
		 * @return sets the films review
		 */
		public Builder review(String review) {
			this.review = review;
			return this;
		}

		/**
		 * Builds the Film object
		 *
		 * @return the film object
		 */
		public Film build() {
			return new Film(this);
		}
	}

	/**
	 * Convert the film's attributes to a comma separated string
	 *
	 * @return the film as a CSV
	 */
	@Override
	public String toString() {
		// returns Film's attributes separated by double comma
		// to avoid issues with attributes containing commas
		return this.getId() + ",," + this.getTitle() + ",," + this.getYear() + ",," + this.getDirector() + ",,"
				+ this.getStars() + ",," + this.getReview();
	}

	/**
	 * Film attributes to param list.
	 *
	 * @param film the film
	 * @return the array list
	 */
	public ArrayList<Object> attributesToParamList() {
		ArrayList<Object> paramVals = new ArrayList<>();

		// convert film attributes to list of parameterS
		// for SQL query
		try {
			paramVals.add(this.getId());
			paramVals.add(this.getTitle());
			paramVals.add(this.getYear());
			paramVals.add(this.getDirector());
			paramVals.add(this.getStars());
			paramVals.add(this.getReview());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// return list of parameters
		return paramVals;
	}

}
