
public class CD extends Media implements ICD {
	private String title;
	private String genre;
		
	public CD() {
		super(-1, MediaType.CD);
	}

	public CD(String title, String genre) {
		super(-1, MediaType.CD);
		init(title, genre);
	}

	public CD(long id, String title, String genre) {
		super(id, MediaType.CD);
		init(title, genre);
	}

	private void init(String title, String genre) {
		this.title = title;
		this.genre = genre;
	}

	public CD(long id, String title)
	{
		super(id, MediaType.CD);
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
		return "CD: title = " + title + ", genre = " + genre;
	}

	public static enum BookField {
		Title, Author;
	}
	
}
