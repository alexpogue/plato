import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatabaseSupport implements IDatabaseSupport {

	private String url;
	private String user;
	private String password;

	public DatabaseSupport(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}
	
	public static void main(String[] args) {
		String url = "jdbc:postgresql://localhost/postgres";
		String user = "user123";
		String password = "postgres";

		DatabaseSupport ds = new DatabaseSupport(url, user, password);

		Scanner scan = new Scanner(System.in);

		System.out.print("Enter 'p' to put a new book, or 'g' to get a book: ");
		String cmd = scan.nextLine();
		if(cmd.charAt(0) == 'p') {
			System.out.println("Putting a new book into the database:");
			System.out.print("Enter title: ");
			String title = scan.nextLine();
			System.out.print("Enter author: ");
			String author = scan.nextLine();
			System.out.print("Enter publisher: ");
			String publisher = scan.nextLine();
			System.out.print("Enter isbn: ");
			String isbn = scan.nextLine();

			Media m = new Book(title, author, publisher, isbn);

			boolean success = ds.putMedia(m);

			if(success) {
				System.out.println("Success!");
			}
			else {
				System.out.println("Fail :(");
			}
		}
		else if(cmd.charAt(0) == 'g') {
			System.out.println("Getting a book from the database:");
			System.out.print("Enter book id: ");
			long id = scan.nextLong();
			scan.nextLine();
			Media m = ds.getMedia(id);
			if(m != null && m.getType() == Media.Type.Book) {
				Book b = (Book) m;
				System.out.println("Got book:");
				System.out.println("  title = " + b.getTitle());
				System.out.println("  author = " + b.getAuthor());
				System.out.println("  publisher = " + b.getPublisher());
				System.out.println("  isbn = " + b.getIsbn());
				System.out.println("Success!");
			}
			else {
				System.out.println("Fail :(");
			}
		}
		scan.close();
	}

	private boolean insertRecord(String sql, List<String> preparedStrings) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);

			try {
				if(!preparedStrings.isEmpty()) {
					PreparedStatement preparedStatement = con.prepareStatement(sql);
					for(int i = 0; i < preparedStrings.size(); i++) {
						preparedStatement.setString(i+1, preparedStrings.get(i));
					}
					preparedStatement.executeUpdate();
				}
				return true;
			} finally {
				con.close();
			}
		} catch (SQLException e) {
			return false;
		}
	}

	public boolean putMedia(Media m) {
		if(m.getId() < 0) {
			return putNewMedia(m);
		}
		else {
			return updateOldMedia(m);
		}
	}

	private boolean putNewMedia(Media m) {
		boolean success;
		Media.Type type = m.getType();
		switch(type) {
		case Book:
			success = putBook((Book) m);
			break;
		default:
			success = false;
			break;
		}
		return success;
	}

	private boolean updateOldMedia(Media m) {
		// TODO: implement
		return false;
	}

	private boolean putBook(Book b) {
		// TODO: Warning! Autoincrement field on books does not guarantee unique
		//       ids on all media, only unique to the books. Must add a "Media"
		//       table that generates the unique ids, which are then inserted as
		//       the id of the "Books" table. Then we must worry about table
		//       synchronization. For example, what if the database inserts into
		//       media, and then the server immediately shuts down. That would
		//       leave an orphaned id without a corresponding book.
		String sql = "INSERT INTO plato.books " +
				"(title, author, publisher, isbn) VALUES " +
				"(?, ?, ?, ?)";
		List<String> preparedStrings = new ArrayList<String>();
		preparedStrings.add(b.getTitle());
		preparedStrings.add(b.getAuthor());
		preparedStrings.add(b.getPublisher());
		preparedStrings.add(b.getIsbn());

		return insertRecord(sql, preparedStrings);
	}

	private Book getBook(long bid) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			ResultSet rs = null;

			String sql = "SELECT " +
					"plato.books.title AS title," +
					"plato.books.author AS author," +
					"plato.books.publisher AS publisher," +
					"plato.books.isbn AS isbn " +
					"FROM plato.books WHERE id='"+ bid +"'";

			String title, author, publisher, isbn;

			try {
				Statement stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				rs.next();

				title = rs.getString("title");
				author = rs.getString("author");
				publisher = rs.getString("publisher");
				isbn = rs.getString("isbn");

			} finally {
				con.close();
				if(rs != null) {
					rs.close();
				}
			}

			return new Book(bid, title, author, publisher, isbn);
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public Media getMedia(long mid) {
		// for now we assume media can only be a book
		return getBook(mid);
	}

	@Override
	public boolean putCustomer(Customer c) {
		// TODO Customer table needs to be added to database
		String sql = "INSERT INTO plato.customers " +
				"(name, rentalhistory) VALUES " +
				"(?, ?)";
		List<String> preparedStrings = new ArrayList<String>();
		preparedStrings.add(c.getName());
		//TODO We need to figure out how PostGres stores arrays
		// preparedStrings.add(c.getRentalHistory());

		return insertRecord(sql, preparedStrings);
	}

	@Override
	public Customer getCustomer(long cid) {
		// TODO Auto-generated method stub
		return null;
	}
}