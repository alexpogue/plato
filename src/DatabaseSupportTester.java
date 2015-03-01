import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class DatabaseSupportTester {
	public String url;
	public String user;
	public String password;

	public DatabaseSupportTester() {
	}
	
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
		String url = dbcreds.get(0);
		String user = dbcreds.get(1);
		String password = dbcreds.get(2);

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
}