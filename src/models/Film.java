package models;

public class Film {

	private int id;
	private String title;
	private int year;
	private String director;
	private String stars;
	private String review;

	private Film(FilmBuilder builder) {
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
		return String.format("%s,%s,%s,%s,\"%s\",\"%s\"", this.getId(), this.getTitle(), this.getYear(),
				this.getDirector(), this.getStars(), this.getReview());
	}

	public static class FilmBuilder {
		private int id;
		private String title;
		private int year;
		private String director;
		private String stars;
		private String review;
		
		public FilmBuilder (Film film) {
			this.title = film.getTitle();
			this.year = film.getYear();
			this.director = film.getDirector();
			this.stars = film.getStars();
			this.review = film.getReview();
		}

		public FilmBuilder id(int id) {
			this.id = id;
			return this;
		}

		public FilmBuilder title(String title) {
			this.title = title;
			return this;
		}

		public FilmBuilder year(int year) {
			this.year = year;
			return this;
		}

		public FilmBuilder director(String director) {
			this.director = director;
			return this;
		}

		public FilmBuilder stars(String stars) {
			this.stars = stars;
			return this;
		}

		public FilmBuilder review(String review) {
			this.review = review;
			return this;
		}

		public Film build() {
			Film film = new Film(this);
			return film;
		}
	}

}
