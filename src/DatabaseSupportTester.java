import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DatabaseSupportTester {
	
	private static String url;
	private static String user;
	private static String password;
	
	public static void main(String[] args)
	{
		List<String> dbcreds = new ArrayList<String>();
		Path path = Paths.get("dbcreds.txt");
		try {
			dbcreds = Files.readAllLines(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		url = dbcreds.get(0);
		user = dbcreds.get(1);
		password = dbcreds.get(2);

		DatabaseSupport ds = new DatabaseSupport(url, user, password);

		Scanner scan = new Scanner(System.in);

		System.out.println("Options:");
		System.out.println("  'p' (put book),");
		System.out.println("  'g' (get book),");
		System.out.println("  'u' (update book),");
		System.out.println("  'r' (remove book),");
		System.out.println("  'a' (put checkoutcard),");
		System.out.println("  't' (get media type)");
		System.out.println("  'c' (put customer)");
		System.out.println("  'q' (get customer)");
		System.out.println("  's' (search books)");
		System.out.println("  'o' (checkout book)");
		System.out.println("  'e' (put user)");
		System.out.println("  'l' (get user)");
		System.out.print("Choice: ");
		String cmd = scan.nextLine();
		char cmdc = cmd.charAt(0);
		
		boolean success = true;
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
			success = ds.putMedia(m);
			
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

			success = ds.putMedia(m);
		}
		else if(cmdc == 'g') {
			System.out.println("Getting a book from the database:");
			System.out.print("Enter book id: ");
			long id = scan.nextLong();
			scan.nextLine();
			Media m = ds.getMedia(id);
			if(m != null && m.getType() == Media.MediaType.Book) {
				Book b = (Book) m;
				System.out.println("Got book:");
				System.out.println("  title = " + b.getTitle());
				System.out.println("  author = " + b.getAuthor());
				System.out.println("  publisher = " + b.getPublisher());
				System.out.println("  isbn = " + b.getIsbn());
				success = true;
			}
			else {
				success = false;
			}
		}
		else if(cmdc == 'a') {
			System.out.println("Putting a new checkout card:");
			System.out.print("Enter customer id: ");
			int customerId = scan.nextInt();
			Customer customer = ds.getCustomer(customerId);
			System.out.print("Enter media id: ");
			int mediaId = scan.nextInt();
			Media media = ds.getMedia(mediaId);
			success = ds.putCheckoutCard(new CheckoutCard(customer, media));
		}
		else if(cmdc == 't') {
			System.out.println("Getting the media type:");
			System.out.print("Enter media id: ");
			int mediaId = scan.nextInt();
			Media.MediaType type = ds.getMediaType(mediaId);
			if(type == Media.MediaType.Error) {
				success = false;
			}
			if(type == Media.MediaType.Book) {
				System.out.println("Book");
				success = true;
			}
		}
		else if(cmdc == 'c') {
			System.out.println("Putting a customer:");
			success = true;
			System.out.print("Enter customer name: ");
			String name = scan.nextLine();
			success = ds.putCustomer(new Customer(name));
		}
		else if(cmdc == 'q') {
			System.out.println("Getting a customer:");
			System.out.print("Enter customer id: ");
			long id = scan.nextLong();
			Customer c = ds.getCustomer(id);
			if(c != null) {
				System.out.println(c);
				success = true;
			}
			else {
				success = false;
			}
		}
		else if(cmdc == 'r') {
			System.out.println("Removing a book:");
			System.out.print("Enter book id: ");
			long id = scan.nextLong();
			success = ds.removeMedia(id);
		}
		else if(cmdc == 's') {
			System.out.println("Searching books:");
			Book.BookField field = null;
			System.out.println("  Possible fields:");
			System.out.println("    't' (title)");
			System.out.println("    'a' (author)");
			System.out.println("    'p' (publisher)");
			System.out.println("    'i' (isbn)");
			System.out.print("  Enter field command: ");
			String fcmd = scan.nextLine();
			char fcmdc = fcmd.charAt(0);
			if(fcmdc == 't')
				field = Book.BookField.Title;
			else if(fcmdc == 'a')
				field = Book.BookField.Author;
			else if(fcmdc == 'p')
				field = Book.BookField.Publisher;
			else if(fcmdc == 'i')
				field = Book.BookField.ISBN;
			else
				success = false;

			List<Book> results;
			if(success) {
				System.out.print("  Enter search string: ");
				String search = scan.nextLine();
				results = ds.searchBooks(field, search);
				if(results != null)
					System.out.println(results);
				else
					success = false;
			}
		}
		else if(cmdc == 'o') {
			System.out.println("Checking out media to customer:");
			System.out.print(" Media id: ");
			long mid = scan.nextLong();
			System.out.print(" Customer id: ");
			long cid = scan.nextLong();

			Media media = ds.getMedia(mid);
			Customer customer = ds.getCustomer(cid);
			CheckoutCard card = new CheckoutCard();
			card.setMedia(media);
			card.setCustomer(customer);
			success = ds.putCheckoutCard(card);
		}
		else if(cmdc == 'e') {
			System.out.println("Putting user:");
			System.out.print("Username: ");
			String username = scan.nextLine();
			System.out.print("Password: ");
			String password = scan.nextLine();
			System.out.print("User type (a = admin, e = employee, c = customer: ");
			char uTypeC = scan.nextLine().charAt(0);
			IUser.UserType uType = null;

			if(uTypeC == 'a')
				uType = IUser.UserType.Admin;
			else if(uTypeC == 'e')
				uType = IUser.UserType.Employee;
			else if(uTypeC == 'c')
				uType = IUser.UserType.Customer;

			User user = new User(username, password, uType);
			ds.putUser(user);
		}
		else if(cmdc == 'l') {
			System.out.println("Getting user:");
			System.out.print("Username: ");
			String username = scan.nextLine();
			User user = ds.getUser(username);
			if(user != null) {
				System.out.println(user);
				success = true;
			}
			else {
				success = false;
			}
		}
		if(success) {
			System.out.println("Success!");
		}
		else {
			System.out.println("Fail :(");
		}
		scan.close();
	}
	
protected static void printBookTable() {
	
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			ResultSet rs = null;

			String sql = "SELECT " +
					"Books.id AS id," +
					"Books.title AS title," +
					"Books.author AS author," +
					"Books.publisher AS publisher," +
					"Books.isbn AS isbn " +
					"FROM Books";
			
			long id;
			String title, author, publisher, isbn;

			try {
				Statement stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{
					id = rs.getLong("id");
					title = rs.getString("title");
					author = rs.getString("author");
					publisher = rs.getString("publisher");
					isbn = rs.getString("isbn");
					
					System.out.println("Book Id: " + id);
					System.out.println("Title: " + title);
					System.out.println("Author: " + author);
					System.out.println("Publisher: " + publisher);
					System.out.println("ISBN: " + isbn + "\n");
				}

			} finally {
				con.close();
				if(rs != null) {
					rs.close();
				}
			}
			
			return;
			
		} catch (SQLException e) {
			return;
		}
			
	}
	
	protected static void printCustomerTable() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			ResultSet rs = null;

			String sql = "SELECT " +
					"Customers.id AS id, " +
					"Customers.name AS name, " +
					"Customers.balance AS balance " +
					"FROM Customers";
			
			long id;
			float balance;
			String name;
			
			try {
				Statement stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{
					id = rs.getLong("id");
					name = rs.getString("name");
					balance = rs.getFloat("balance");
					
					System.out.println("Customer ID: " + id);
					System.out.println("Name: " + name);
					System.out.println("Balance: " + balance + "\n");
				}
				
			} finally {
				con.close();
				if(rs != null) {
					rs.close();
				}
			}
			
			return;
			
		} catch (SQLException e) {
			return;
		}
	}
	
	protected static void printCheckoutCardTable() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);
			ResultSet rs = null;

			String sql = "SELECT " +
					"CheckoutCards.id AS id, " +
					"CheckoutCards.customerid AS customerid, " +
					"CheckoutCards.mediaid AS mediaid, " +
					"CheckoutCards.timeout AS timeout, " +
					"CheckoutCards.timein AS timein " +
					"FROM CheckoutCards";
			
			long id, cid, mid;
			Timestamp timeout, timein;
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			
			try {
				Statement stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{
					id = rs.getLong("id");
					cid = rs.getLong("customerid");
					mid = rs.getLong("mediaid");
					
					timeout = rs.getTimestamp("timeout");
					timein = rs.getTimestamp("timein");
					
					System.out.println("Checkout ID: " + id);
					System.out.println("Checked out by Customer #: " + cid);
					System.out.println("Media checked out (Media #): " + mid);
					
					if(timeout != null)
					{
						Date checkedOut = new Date(timeout.getTime());
						String checkOutString = df.format(checkedOut);
						System.out.println("Date checked out: " + checkOutString);
					}
					else
					{
						System.out.println("Date checked out: null");
					}
					
					if(timein != null)
					{
						Date checkedIn = new Date(timein.getTime());
						String checkInString = df.format(checkedIn);
						System.out.println("Date checked in: " + checkInString +"\n");
					}
					else
					{
						System.out.println("Date checked in: null");
					}
					System.out.println();

				}
				
			} finally {
				con.close();
				if(rs != null) {
					rs.close();
				}
			}
			
			return;
			
		} catch (SQLException e) {
			return;
		}
	}
}