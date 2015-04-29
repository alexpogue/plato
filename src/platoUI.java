import java.util.List;
import java.util.Scanner;

public class platoUI {
	private static IUser.UserType utype;
	private static Scanner scanner;
	private static LibraryController libControl;
	
	//TODO Determine this number
	private static final int numCustomerOptions = 5;
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
				
			//Exit	
			case 11:
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
		System.out.println(counter++ + ": Main Menu");
		
		int selection = -1;
		while(selection == -1)
		{
			selection = acknowledgeSelection(6);
		}
		
		String title;
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
			deleteBook();
				break;
				
			//Edit book title
			case 3:
				//TODO Resolve issue: none of these methods exist
				break;
				
			//Edit book author
			case 4:
				//TODO Resolve issue: none of these methods exist
				break;
				
			//Edit book publisher
			case 5:
				//TODO Resolve issue: none of these methods exist
				break;
				
			//Main menu
			case 6:
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
		
		switch(selection)
		{
			case 1:
				
				break;
				
			case 2:
				
				break;
				
			case 3:
				
				break;
				
			case 4:
				
				break;

			//Main menu
			case 5:
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
		
		switch(selection)
		{
			case 1:
				
				break;
				
			case 2:
				
				break;
				
			case 3:
				
				break;
				
			case 4:
				
				break;

			//Main menu
			case 5:
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
		
		boolean success = libControl.isMediaCheckedOut(mid, err);
		switch(err.getError())
		{
			case Success:
				opSuccess(success);
				break;
			case MediaNotFound:
				System.out.println("Media not found.");
				break;
		}
	}
	
	private static void deleteBook() {
		long bid;
		bid = -1;
		while(bid == -1)
		{
			System.out.print("Enter Book id to be deleted: ");
			bid = handleID();
		}
		System.out.println("Are you sure? This operation cannot be undone. (Y/N): ");
		String decision = scanner.next();
		decision = decision.toLowerCase();
		
		if(decision.equals("yes") || decision.equals("y"))
		{
			opSuccess(libControl.deleteBook(bid));
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
		
		List<Book> blist = libControl.searchBooks(bookfield, searchString);
		
		for(Book b : blist)
		{
			viewBook(b);
		}
	}

	private static void viewCustomer(long cid)
	{
		
	}

	private static void viewBook(long bid)
	{
		
	}
	
	private static void viewBook(Book b)
	{
		
	}

	private static void viewMovie(long mid)
	{
		
	}

	private static void viewCD(long cdid)
	{
		
	}
}
