import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
		System.out.println("Enter book information to put it into the database:");
		System.out.print("Enter title: ");
		String title = scan.nextLine();
		System.out.print("Enter author: ");
		String author = scan.nextLine();
		System.out.print("Enter publisher: ");
		String publisher = scan.nextLine();
		System.out.print("Enter isbn: ");
		String isbn = scan.nextLine();
		scan.close();

		Media m = new Book(-1, title, author, publisher, isbn);

		boolean success = ds.putMedia(m);

		if(success) {
			System.out.println("Success!");
		}
		else {
			System.out.println("Fail :(");
		}
	}

	private void insertRecord(String sql, List<String> preparedStrings) throws SQLException {
		Connection con = DriverManager.getConnection(url, user, password);

		if(!preparedStrings.isEmpty()) {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			for(int i = 0; i < preparedStrings.size(); i++) {
				preparedStatement.setString(i+1, preparedStrings.get(i));
			}
			preparedStatement.executeUpdate();
		}

		con.close();
	}

	public boolean putMedia(Media m) {
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

	private boolean putBook(Book b) {
		// TODO: Warning! Autoincrement field on books does not guarantee unique
		//       ids on all media, only unique to the books. Must add a "Media"
		//       table that generates the unique ids, which are then inserted as
		//       the id of the "Books" table. Then we must worry about table
		//       synchronization. For example, what if the database inserts into
		//       media, and then the server immediately shuts down. That would
		//       leave an orphaned id without a corresponding book.
		boolean success = true;
		String sql = "INSERT INTO plato.books " +
				"(title, author, publisher, isbn) VALUES " +
				"(?, ?, ?, ?)";
		List<String> preparedStrings = new ArrayList<String>();
		preparedStrings.add(b.getTitle());
		preparedStrings.add(b.getAuthor());
		preparedStrings.add(b.getPublisher());
		preparedStrings.add(b.getIsbn());

		try {
			insertRecord(sql, preparedStrings);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			success = false;
		}
		return success;
	}

	private Book getBook(long bid) {
		// TODO fill in
		return null;
	}

	@Override
	public Media getMedia(long mid) {
		// TODO: assuming media can only be a book
		return getBook(mid);
	}

	@Override
	public boolean putCustomer(Customer c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Customer getCustomer(long cid) {
		// TODO Auto-generated method stub
		return null;
	}
}