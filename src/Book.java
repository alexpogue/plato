import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("book")
public class Book extends Media implements IBook {
	private String title;
	private String author;
	private String publisher;
	private String isbn;
		
	public Book() {
		super(-1, MediaType.Book);
	}

	public Book(String title, String author, String publisher, String isbn) {
		super(-1, MediaType.Book);
		init(title, author, publisher, isbn);
	}

	public Book(long id, String title, String author, String publisher, String isbn) {
		super(id, MediaType.Book);
		init(title, author, publisher, isbn);
	}

	private void init(String title, String author, String publisher, String isbn) {
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.isbn = isbn;
	}

	public Book(long id, String title)
	{
		super(id, MediaType.Book);
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
	
	public String toString() {
		return "Book: title = " + title + ", author = " + author + ", publisher = " + publisher + ", isbn = " + isbn;
	}

	public static enum BookField {
		title, author, publisher, isbn
	}
	
}
