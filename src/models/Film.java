package models;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Film")
public class Film {
	// use builder design pattern

	private final int id;
	private final String title;
	private final int year;
	private final String director;
	private final String stars;
	private final String review;

	private Film(Builder builder) {
		// constructor sets film's attributes equal to
		// builder's attributes
		this.id = builder.id;
		this.title = builder.title;
		this.year = builder.year;
		this.director = builder.director;
		this.stars = builder.stars;
		this.review = builder.review;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public String getDirector() {
		return director;
	}

	public String getStars() {
		return stars;
	}

	public String getReview() {
		return review;
	}

	@Override
	public String toString() {
		// returns Film's attributes separated by double comma
		// to avoid issues with attributes containing commas
		return this.getId() + ",, " + this.getTitle() + ",, " + this.getYear() + ",, " + this.getDirector() + ",, "
				+ this.getStars() + ",, " + this.getReview();
	}

	public static class Builder {
		private int id;
		private String title;
		private int year;
		private String director;
		private String stars;
		private String review;

		public Builder(Film film) {
			// allows passing of existing film to set Film
			// equal to existing film, for converting to JSON
			if (film != null) {
				this.id = film.getId();
				this.title = film.getTitle();
				this.year = film.getYear();
				this.director = film.getDirector();
				this.stars = film.getStars();
				this.review = film.getReview();
			}
		}

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder year(int year) {
			this.year = year;
			return this;
		}

		public Builder director(String director) {
			this.director = director;
			return this;
		}

		public Builder stars(String stars) {
			this.stars = stars;
			return this;
		}

		public Builder review(String review) {
			this.review = review;
			return this;
		}

		public Film build() {
			return new Film(this);
		}
	}

}
