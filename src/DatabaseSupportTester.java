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

		printCheckoutCardTable();

		DatabaseSupport ds = new DatabaseSupport(url, user, password);

		Scanner scan = new Scanner(System.in);

		System.out.print("Enter 'p' (put book), 'g' (get book), 'u' (update book), 'a' (put checkoutcard): ");
		String cmd = scan.nextLine();
		char cmdc = cmd.charAt(0);
		
		boolean success = false;
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
			if(m != null && m.getType() == Media.Type.Book) {
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
			System.out.print("Enter media id: ");
			int mediaId = scan.nextInt();
			success = ds.putCheckoutCard(new CheckoutCard(customerId, mediaId));
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