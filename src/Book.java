
public class Book extends Media implements IBook {
	private String title;
	private String author;
	private String publisher;
	private String isbn;
	
	public Book(long id, String title, String author, String publisher, String isbn) {
		//TODO generate long id in library
		super(id, Type.Book);
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.isbn = isbn;
	}
	
	public Book(long id, String title)
	{
		//TODO generate long id in library
		super(id, Type.Book);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Override
	public boolean editBook(int field, String newValue) {
		// TODO Auto-generated method stub
		return false;
	}
}
