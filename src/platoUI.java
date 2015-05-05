import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class platoUI {
	private static IUser.UserType utype;
	private static Scanner scanner;
	private static LibraryController libControl;
	
	private static final int numCustomerOptions = 6;
	private static final int numEmployeeOptions = 5;
	private static final int numAdminOptions = 1;

	private static boolean exit = false;
	private static boolean submenuExit = false;
	
	public static void main(String[] args) {
		libControl = new LibraryController();
		scanner = new Scanner(System.in);
		
		System.out.println("Welcome to PLATO!");
		System.out.println("\nPlease log in: ('guest' if you are a visitor)\n");
		
		while(utype == null)
		{
			System.out.print("Username: ");
			String username = scanner.next();
			utype = authorizeUser(username);
		}

		while(!exit)
		{
			submenuExit = false;
			mainMenu();
		}
		
		scanner.close();
	}

	private static void mainMenu()
	{
		int counter = 1;
		switch(utype)
		{
			/*  This is written in this fashion, as:
			*  - Admins have access to all commands (though regrettably, only a few exclusive ones)
			*  - Employees have access to the next tier of commands (which is to say a vast majority of them [all but two])
			*  - Customers the next (though they don't have much at all, which is regrettable, but fairly expected)
			*  And since it's such that there aren't any commands hidden as one goes up the ladder this structure made the most sense
			*/
		
			case Admin:
				System.out.println("\nAdmin Commands:");
				System.out.println(counter++ + ": Delete Customer");
			case Employee:
				System.out.println("\nEmployee Commands:");
				System.out.println(counter++ + ": Media Menu");
				System.out.println(counter++ + ": Customer Menu");
				System.out.println(counter++ + ": Book Menu");
				System.out.println(counter++ + ": Movie Menu");
				System.out.println(counter++ + ": CD Menu");
			case Customer:
				System.out.println("\nCustomer Options:");
				System.out.println(counter++ + ": Search Book");
				System.out.println(counter++ + ": View Book");
				System.out.println(counter++ + ": View Movie");
				System.out.println(counter++ + ": View CD");
				System.out.println(counter++ + ": View Media Checked Out");
				System.out.println(counter++ + ": Exit");
				break;
		}
		
		int numOptions = 0;
		
		if(isAdmin())
		{
			numOptions = numAdminOptions + numEmployeeOptions + numCustomerOptions;
		} else if (isEmployee()) {
			numOptions = numEmployeeOptions + numCustomerOptions;
		} else {
			numOptions = numCustomerOptions;
		}
			
		
		int selection = -1;
		while(selection == -1)
		{
			selection = acknowledgeSelection(numOptions);
		}
		
		if(isEmployee())
			selection += numAdminOptions;
		if(isCustomer())
			selection += numEmployeeOptions + numAdminOptions;
		
		handleMainMenuChoice(selection);
	}

	private static void handleMainMenuChoice(int selection) {
		switch(selection)
		{		
			//Delete Customer
			case 1:
				deleteCustomer();
				break;
				
			//Media Menu
			case 2:
				while(!submenuExit)
					mediaMenu();
				break;
				
			//Customer Menu
			case 3:
				while(!submenuExit)
					customerMenu();
				break;
			
			//Book Menu
			case 4:
				while(!submenuExit)
					bookMenu();
				break;
				
			//Movie Menu
			case 5:
				while(!submenuExit)
					movieMenu();
				break;
			
			//CD Menu
			case 6:
				while(!submenuExit)
					cdMenu();
				break;
				
			//Search Book
			case 7:
				searchBook();
				break;
				
			//View Book
			case 8:	
				long bid = -1;
				while(bid == -1)
				{
					System.out.print("\nEnter Book id: ");
					bid = handleID();
				}
				viewBook(bid);
				break;
				
			//View Movie
			case 9:
				long mid = -1;
				while(mid == -1)
				{
					System.out.print("\nEnter Movie id: ");
					mid = handleID();
				}
				viewMovie(mid);
				break;
			
			//View CD
			case 10:
				long cdid = -1;
				while(cdid == -1)
				{
					System.out.print("\nEnter CD id: ");
					cdid = handleID();
				}
				viewCD(cdid);
				break;
			
			//View media checked out
			case 11:
				long cid = -1;
				while(cid == -1)
				{
					System.out.println("\nEnter Customer id: ");
					cid = handleID();
				}
				viewCheckedOutMedia(cid);
				break;
				
			//Exit	
			case 12:
				exit = true;
				break;
				
			default:
				exit = true;
				break;
		}
	}
	
	private static void mediaMenu()
	{
		int counter = 1;
		System.out.println(counter++ + ": Check In Media");
		System.out.println(counter++ + ": Check Out Media");
		System.out.println(counter++ + ": View Media Status");
		System.out.println(counter++ + ": Main Menu");
		
		int selection = -1;
		while(selection == -1)
		{
			selection = acknowledgeSelection(4);
		}
		
		long mid = -1;
		switch(selection)
		{
			//Check in media
			case 1:
				mid = -1;
				while(mid == -1)
				{
					System.out.print("\nEnter Media id: ");
					mid = handleID();
				}
				opSuccess(libControl.checkInMedia(mid));
				break;
				
			//Check out media
			case 2:
				long cid = -1;
				while(cid == -1)
				{
					System.out.print("\nEnter checking out Customer id: ");
					cid = handleID();
				}
				
				mid = -1;
				while(mid == -1)
				{
					System.out.print("\nEnter Media id to be checked out: ");
					mid = handleID();
				}
				
				opSuccess(libControl.checkOutMedia(cid, mid));
				break;
				
			//View media status
			case 3:
				viewMediaStatus();
				break;
				
				
			//Main menu
			case 4:
				submenuExit = true;
				break;
				
			default:
				submenuExit = true;
				break;
		}
	}
	
	private static void customerMenu()
	{
		int counter = 1;
		System.out.println(counter++ + ": Add Customer");
		System.out.println(counter++ + ": Change Customer Name");
		System.out.println(counter++ + ": View Customer");
		System.out.println(counter++ + ": Main Menu");
		
		int selection = -1;
		String name;
		while(selection == -1)
		{
			selection = acknowledgeSelection(4);
		}
		
		long cid = -1;
		switch(selection)
		{
			case 1:
				System.out.println("\nEnter customer name to be added: ");
				name = scanner.next();
				
				opSuccess(libControl.addCustomer(name));
				break;
				
			case 2:
				System.out.println("\nEnter id of customer to be changed: ");
				cid = -1;
				while(cid == -1)
				{
					System.out.print("\nEnter Customer id: ");
					cid = handleID();
				}
				
				System.out.println("\nEnter customer's new name: ");
				name = scanner.next();
				
				opSuccess(libControl.editCustomerName(cid, name));
				
				break;
				
			case 3:
				cid = -1;
				while(cid == -1)
				{
					System.out.print("\nEnter Customer id: ");
					cid = handleID();
				}
				viewCustomer(cid);
				break;
				
			//Main menu
			case 4:
				submenuExit = true;
				break;
				
			default:
				submenuExit = true;
				break;
		}
	}
	
	private static void bookMenu()
	{
		int counter = 1;
		System.out.println(counter++ + ": Add Book");
		System.out.println(counter++ + ": Delete Book");
		System.out.println(counter++ + ": Edit Book Title");
		System.out.println(counter++ + ": Edit Book Author");
		System.out.println(counter++ + ": Edit Book Publisher");
		System.out.println(counter++ + ": Edit Book ISBN");
		System.out.println(counter++ + ": Main Menu");
		
		int selection = -1;
		while(selection == -1)
		{
			selection = acknowledgeSelection(7);
		}
		
		String title;
		long mid = -1;
		switch(selection)
		{
			//Add book
			case 1:
				System.out.println("Enter title of book to be added: ");
				title = scanner.next();
				opSuccess(libControl.addBook(title));
				break;
				
			//Delete book
			case 2:
				deleteMedia(Media.MediaType.Book);
				break;
				
			//Edit book title
			case 3:
				editMediaTitle(Media.MediaType.Book);
				break;
				
			//Edit book author
			case 4:
				mid = -1;
				while(mid == -1)
				{
					System.out.print("\nEnter media id of Book to be edited: ");
					mid = handleID();
				}
				
				System.out.println("Enter new Author: ");
				String author = scanner.next();
				
				opSuccess(libControl.editBookAuthor(mid, author));
				break;
				
			//Edit book publisher
			case 5:
				mid = -1;
				while(mid == -1)
				{
					System.out.print("\nEnter media id of Book to be edited: ");
					mid = handleID();
				}
				
				System.out.println("Enter new Publisher of book: ");
				String pub = scanner.next();
				
				opSuccess(libControl.editBookPublisher(mid, pub));
				
				break;
				
			//Edit book isbn
			case 6:
				mid = -1;
				while(mid == -1)
				{
					System.out.print("\nEnter media id of Book to be edited: ");
					mid = handleID();
				}

				System.out.println("Enter new Publisher of book: ");
				String isbn = scanner.next();

				opSuccess(libControl.editBookIsbn(mid, isbn));
				break;

			//Main menu
			case 7:
				submenuExit = true;
				break;
				
			default:
				submenuExit = true;
				break;
		}
	}
	
	private static void movieMenu()
	{
		int counter = 1;
		System.out.println(counter++ + ": Add Movie");
		System.out.println(counter++ + ": Delete Movie");
		System.out.println(counter++ + ": Edit Movie Title");
		System.out.println(counter++ + ": Edit Movie Genre");
		System.out.println(counter++ + ": Main Menu");
		
		int selection = -1;
		while(selection == -1)
		{
			selection = acknowledgeSelection(5);
		}
		
		String title;
		switch(selection)
		{
			//Add movie
			case 1:
				System.out.println("Enter title of movie to be added: ");
				title = scanner.next();
				opSuccess(libControl.addMovie(title));
				break;
				
			//Delete movie
			case 2:
				deleteMedia(Media.MediaType.Movie);
				break;
				
			//Edit movie title
			case 3:
				editMediaTitle(Media.MediaType.Movie);
				break;
				
			//Edit movie genre
			case 4:
				editMediaGenre(Media.MediaType.Movie);
				break;

			//Main menu
			case 5:
				submenuExit = true;
				break;
				
			default:
				submenuExit = true;
				break;
		}
	}
	
	private static void cdMenu()
	{
		int counter = 1;
		System.out.println(counter++ + ": Add CD");
		System.out.println(counter++ + ": Delete CD");
		System.out.println(counter++ + ": Edit CD Title");
		System.out.println(counter++ + ": Edit CD Genre");
		System.out.println(counter++ + ": Main Menu");
		
		int selection = -1;
		while(selection == -1)
		{
			selection = acknowledgeSelection(5);
		}
		
		String title;
		switch(selection)
		{
			//Add CD
			case 1:
				System.out.println("Enter title of CD to be added: ");
				title = scanner.next();
				opSuccess(libControl.addCD(title));
				break;
				
			//Delete CD
			case 2:
				deleteMedia(Media.MediaType.CD);
				break;
				
			//Edit CD title
			case 3:
				editMediaTitle(Media.MediaType.CD);
				break;
				
			//Edit CD genre
			case 4:
				editMediaGenre(Media.MediaType.CD);
				break;

			//Main menu
			case 5:
				submenuExit = true;
				break;
				
			default:
				submenuExit = true;
				break;
		}
	}
	
	private static int acknowledgeSelection(int upperBound)
	{
		System.out.print("\nInput a selection: ");
		String choice = scanner.next();
		int convert = -1;
		
		try {
		convert = Integer.parseInt(choice);
		} catch(NumberFormatException e)
		{
			System.out.println("\nPlease input a number corresponding with options above.\n");
			return -1;
		}
		
		if(convert > (upperBound) || convert < 1)
		{
			System.out.println("\nPlease input a number corresponding with options above.\n");
			return -1;
		}
		
		return convert;
	}

	private static IUser.UserType authorizeUser(String username)
	{
		IUser.UserType usertype;
		if(!(username.toLowerCase().equals("guest")))
		{
			System.out.print("Password: ");
			String password = scanner.next();
			
			try {
				usertype = libControl.login(username, password); 
			} catch(NullPointerException err) {
				System.out.println("\nLogin information was incorrect. Please try again.\n");
				return null;
			}
			return usertype;
		}

		else
			return IUser.UserType.Customer;
	}
	
	private static long handleID()
	{
		long id = -1;
		
		String selection = scanner.next();
		try {
		id = Long.parseLong(selection);
		} catch(NumberFormatException e) {
			System.out.println("\nPlease input a valid id.");
			return -1;
		}
		
		return id;
	}
	
	private static boolean isAdmin()
	{
		return utype == IUser.UserType.Admin;
	}
	
	private static boolean isEmployee()
	{
		return utype == IUser.UserType.Employee;
	}
	
	private static boolean isCustomer()
	{
		return utype == IUser.UserType.Customer;
	}
	
	private static void opSuccess(boolean b)
	{
		if(b)
			System.out.println("Operation success.");
		if(!b)
			System.out.println("Operation failure.");
	}
	
	private static void viewMediaStatus() {
		long mid;
		ErrorContainer err = new ErrorContainer();
		mid = -1;
		while(mid == -1)
		{
			System.out.print("Enter Media id: ");
			mid = handleID();
		}
		
		boolean isCheckedOut = libControl.isMediaCheckedOut(mid, err);
		switch(err.getError())
		{
			case Success:
				if(isCheckedOut)
					System.out.println("Media *is* checked out");
				else
					System.out.println("Media is *not* checked out");
				System.out.println();
				opSuccess(true);
				break;
			case MediaNotFound:
				System.out.println("Media not found.");
				break;
			default:
				System.out.println("Error");
				break;
		}
	}
	
	private static void editMediaTitle(Media.MediaType mtype) {
		String title;
		long mid;
		mid = -1;
		while(mid == -1)
		{
			System.out.print("\nEnter media id of item to be edited: ");
			mid = handleID();
		}
		
		System.out.println("Enter new Title: ");
		title = scanner.next();
		
		switch(mtype)
		{
			case Book:
				opSuccess(libControl.editBookTitle(mid, title));
				break;
			case Movie:
				opSuccess(libControl.editMovieTitle(mid, title));
				break;
			case CD:
				opSuccess(libControl.editCDTitle(mid, title));
				break;
			default:
				break;
		}
		
	}
	
	private static void editMediaGenre(Media.MediaType mtype) {
		long mid;
		mid = -1;
		while(mid == -1)
		{
			System.out.print("\nEnter media id of item to be edited: ");
			mid = handleID();
		}
		
		System.out.println("Enter new Genre: ");
		String genre = scanner.next();
		
		switch(mtype)
		{
			case Movie:
				opSuccess(libControl.editMovieGenre(mid, genre));
				break;
			case CD:
				opSuccess(libControl.editCDGenre(mid, genre));
				break;
			default:
				break;
		}
	}
	
	private static void deleteMedia(Media.MediaType mtype) 
	{
		long mid;
		mid = -1;
		while(mid == -1)
		{
			System.out.print("Enter media id of item to be deleted: ");
			mid = handleID();
		}
		System.out.println("Are you sure? This operation cannot be undone. (Y/N): ");
		String decision = scanner.next();
		decision = decision.toLowerCase();
		
		if(decision.equals("yes") || decision.equals("y"))
		{
			switch(mtype)
			{
				case Book:
					opSuccess(libControl.deleteBook(mid));
					break;
				case Movie:
					opSuccess(libControl.deleteMovie(mid));
					break;
				case CD:
					opSuccess(libControl.deleteCD(mid));
					break;
				default:
					break;
			}
		}
	}
	
	private static void deleteCustomer() {
		long cid = -1;
		while(cid == -1)
		{
			System.out.print("Enter Customer id to be deleted: ");
			cid = handleID();
		}
		System.out.println("Are you sure? This operation cannot be undone. (Y/N): ");
		String decision = scanner.next();
		decision = decision.toLowerCase();
		
		if(decision.equals("yes") || decision.equals("y"))
		{
			opSuccess(libControl.removeCustomer(cid));
		}
	}
	
	private static void searchBook() {
		boolean fielded = false;
		Book.BookField bookfield = null;
		while(!fielded)
		{
			System.out.println("\nEnter a book field to search by (Title, Author, Publisher, ISBN): ");
			
			String field = scanner.next();
			field = field.toLowerCase();
			
			switch(field)
			{
				case "title":
					fielded = true;
					bookfield = Book.BookField.Title;
					break;
				case "author":
					fielded = true;
					bookfield = Book.BookField.Author;
					break;
				case "publisher":
					fielded = true;
					bookfield = Book.BookField.Publisher;
					break;
				case "isbn":
					fielded = true;
					bookfield = Book.BookField.ISBN;
					break;
				default:
					fielded = false;
			}
		}
		
		System.out.println("Enter a search string: ");
		String searchString = scanner.next();
		
		List<Book> blist = new ArrayList<Book>();
		blist = libControl.searchBooks(bookfield, searchString);
		
		for(Book b : blist)
		{
			viewBook(b);
		}
	}
	
	private static void printMediaTitle(Media m, Media.MediaType type)
	{
		switch(type)
		{
			case Book:
				Book b = (Book) m;
				System.out.println("\n" + b.getTitle());
				break;
				
			case Movie:
				Movie mo = (Movie) m;
				System.out.println("\n" + mo.getTitle());
				break;
				
			case CD:
				CD cd = (CD) m;
				System.out.println("\n" + cd.getTitle());
				break;
				
			default:
				break;
		}
	}
	
	private static void viewCheckoutCard(CheckoutCard cc) 
	{
		//Don't really need to print a customer ID, as they should know the one they inputed to get these
		
		Media m = cc.getMedia();
		Media.MediaType type = m.getType();
		DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
		
		Date checkoutDate = cc.getCheckOutDate();
		Date checkinDate = cc.getCheckInDate();
		
		printMediaTitle(m, type);
		System.out.println(dateformat.format(checkoutDate));
		System.out.println(dateformat.format(checkinDate));
	}

	private static void viewCustomer(long cid)
	{
		Customer customer = libControl.getCustomer(cid);
		
		System.out.println(customer.getId());
		System.out.println(customer.getName());
	}

	private static void viewCheckedOutMedia(long cid) 
	{
		Customer customer = libControl.getCustomer(cid);
		Set<CheckoutCard> checkedOut = new HashSet<CheckoutCard>();
		checkedOut = customer.getCheckoutCards();
		
		for(CheckoutCard cc : checkedOut)
		{
			viewCheckoutCard(cc);
		}
	}

	private static void viewBook(long bid)
	{
		Book book = libControl.getBook(bid);
		viewBook(book);
	}
	
	private static void viewBook(Book b)
	{
		System.out.println(b.getTitle());
		System.out.println(b.getAuthor());
		System.out.println(b.getPublisher());
		System.out.println(b.getIsbn());
	}

	private static void viewMovie(long mid)
	{
		Movie movie = libControl.getMovie(mid);
		System.out.println(movie.getTitle());
		System.out.println(movie.getGenre());
	}

	private static void viewCD(long cdid)
	{
		CD cd = libControl.getCD(cdid);
		System.out.println(cd.getTitle());
		System.out.println(cd.getGenre());
	}
}
