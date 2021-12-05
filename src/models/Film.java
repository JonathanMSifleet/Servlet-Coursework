package models;

public class Film {

	private int id;
	private String title;
	private int year;
	private String director;
	private String stars;
	private String review;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getStars() {
		return stars;
	}

	public void setStars(String stars) {
		this.stars = stars;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public String getObjectValues(Film film) {

		// Java equivalent of JS Obj.values
		// attributes potentially containing commas are wrapped in quotes
		return String.format("%s,%s,%s,%s,\"%s\",\"%s\"", film.getId(), film.getTitle(), film.getYear(),
				film.getDirector(), film.getStars(), film.getReview());
	}

}
