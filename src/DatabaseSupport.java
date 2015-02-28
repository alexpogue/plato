import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		List<String> dbcreds = new ArrayList<String>();
		Path path = Paths.get("dbcreds.txt");
		try {
			dbcreds = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		String url = dbcreds.get(0);
		String user = dbcreds.get(1);
		String password = dbcreds.get(2);

		DatabaseSupport ds = new DatabaseSupport(url, user, password);

		Scanner scan = new Scanner(System.in);

		System.out.print("Enter 'p' (put book), 'g' (get book), or 'u' (update book): ");
		String cmd = scan.nextLine();
		char cmdc = cmd.charAt(0);
		if(cmdc == 'p') {
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
		else if(cmdc == 'u') {
			System.out.println("Updating a book in the database:");
			System.out.print("Enter id of book to update: ");
			int id = scan.nextInt();
			scan.nextLine();
			System.out.print("Enter title: ");
			String title = scan.nextLine();
			System.out.print("Enter author: ");
			String author = scan.nextLine();
			System.out.print("Enter publisher: ");
			String publisher = scan.nextLine();
			System.out.print("Enter isbn: ");
			String isbn = scan.nextLine();

			Media m = new Book(id, title, author, publisher, isbn);

			boolean success = ds.putMedia(m);

			if(success) {
				System.out.println("Success!");
			}
			else {
				System.out.println("Fail :(");
			}
		}
		else if(cmdc == 'g') {
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

	private boolean executeSql(String sql, List<String> preparedStrings) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);

			try {
				PreparedStatement statement = con.prepareStatement(sql);
				for(int i = 0; i < preparedStrings.size(); i++) {
					statement.setString(i+1, preparedStrings.get(i));
				}
				statement.executeUpdate();
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
		long id = getNewMediaId();
		m.setId(id);
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

	private long getNewMediaId() {
		String sql = "INSERT INTO Media DEFAULT VALUES";
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if(generatedKeys.next()) {
				return generatedKeys.getLong(1);
			}
			else {
				return -1;
			}
		} catch (SQLException e) {
			return -1;
		}
	}

	private boolean putBook(Book b) {
		// TODO: Warning! Autoincrement field on books does not guarantee unique
		//       ids on all media, only unique to the books. Must add a "Media"
		//       table that generates the unique ids, which are then inserted as
		//       the id of the "Books" table. Then we must worry about table
		//       synchronization. For example, what if the database inserts into
		//       media, and then the server immediately shuts down. That would
		//       leave an orphaned id without a corresponding book.
		// TODO: parameterize id rather than doing string manipulation (safer)
		String sql = "INSERT INTO Books " +
				"(id, title, author, publisher, isbn) VALUES (" + b.getId() + ", ?, ?, ?, ?)";
		List<String> preparedStrings = getBookPreparedStrings(b);

		return executeSql(sql, preparedStrings);
	}

	private boolean updateOldMedia(Media m) {
		boolean success;
		Media.Type type = m.getType();
		switch(type) {
		case Book:
			success = updateBook((Book) m);
			break;
		default:
			success = false;
			break;
		}
		return success;
	}

	private boolean updateBook(Book b) {
		// TODO: use a prepared int to insert the book id into the sql query (safer than string manipulation)
		String sql = "UPDATE Books SET " +
				"title = ?, author = ?, publisher = ?, isbn = ? WHERE id = " + b.getId(); 
		List<String> preparedStrings = getBookPreparedStrings(b);

		return executeSql(sql, preparedStrings);
	}

	private List<String> getBookPreparedStrings(Book b) {
		List<String> preparedStrings = new ArrayList<String>();
		preparedStrings.add(b.getTitle());
		preparedStrings.add(b.getAuthor());
		preparedStrings.add(b.getPublisher());
		preparedStrings.add(b.getIsbn());
		return preparedStrings;
	}

	private Book getBook(long bid) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			ResultSet rs = null;

			String sql = "SELECT " +
					"Books.title AS title," +
					"Books.author AS author," +
					"Books.publisher AS publisher," +
					"Books.isbn AS isbn " +
					"FROM Books WHERE id='"+ bid +"'";

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
				"(name, rentalhistory, balance) VALUES " +
				"(?, ?, ?)";
		List<String> preparedStrings = new ArrayList<String>();
		preparedStrings.add(c.getName());
		//TODO We need to figure out how PostGres stores arrays
		// preparedStrings.add(c.getRentalHistory());
		preparedStrings.add(((Integer)c.getBalanceOwed()).toString());

		return executeSql(sql, preparedStrings);
	}

	@Override
	public Customer getCustomer(long cid) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			ResultSet rs = null;

			String sql = "SELECT " +
					"plato.customers.name AS name," +
					"plato.customers.rentalhistory AS rentalhistory," +
					"plato.customers.balance AS balance " +
					"FROM plato.customers WHERE id='"+ cid +"'";

			String name;
			RentalHistory rentalHistory;
			int balanceOwed;

			try {
				Statement stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				rs.next();

				name = rs.getString("name");
				rentalHistory = (RentalHistory) rs.getArray("rentalhistory");
				balanceOwed = rs.getInt("balance");

			} finally {
				con.close();
				if(rs != null) {
					rs.close();
				}
			}

			return new Customer(cid, name, rentalHistory, balanceOwed);
			
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public boolean putCheckoutCard(CheckoutCard cc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CheckoutCard getRecentCheckoutCardForMedia(long mid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LatePolicy getLatePolicy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean putLatePolicy(LatePolicy lp) {
		// TODO Auto-generated method stub
		return false;
	}
}