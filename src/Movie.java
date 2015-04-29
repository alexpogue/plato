
public class Movie extends Media implements IMovie {
	private String title;
	private String genre;
		
	public Movie() {
		super(-1, MediaType.Movie);
	}

	public Movie(String title, String genre) {
		super(-1, MediaType.Movie);
		init(title, genre);
	}

	public Movie(long id, String title, String genre) {
		super(id, MediaType.Movie);
		init(title, genre);
	}

	private void init(String title, String genre) {
		this.title = title;
		this.genre = genre;
	}

	public Movie(long id, String title)
	{
		super(id, MediaType.Movie);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	
	public String toString() {
		return "Movie: title = " + title + ", genre = " + genre;
	}

	public static enum BookField {
		Title, Author;
	}
	
}