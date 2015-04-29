import java.util.Scanner;

public class platoUI {
	
	private static IUser.UserType utype;
	private static Scanner scanner;
	private static LibraryController libControl;
	
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
		
	   /*  This is written in this fashion, as:
		*  - Admins have access to all commands (though regrettably, only a few exclusive ones)
		*  - Employees have access to the next tier of commands (which is to say a vast majority of them [all but two])
		*  - Customers the next (though they don't have much at all, which is regrettable, but fairly expected)
		*  And since it's such that there aren't any commands hidden as one goes up the ladder this structure made the most sense
		*/
		int counter = 1;
		switch(utype)
		{
			case Admin:
				System.out.println("\nAdmin Commands:");
				System.out.println(counter++ + ": Set Late Fee Policy");
				System.out.println(counter++ + ": Delete Customer");
			case Employee:
				System.out.println("\nEmployee Commands:");
				System.out.println(counter++ + ": ");
				//TODO Options for the rest of the commands in the program as well as the count
			case Customer:
				System.out.println("\nCustomer Options:");
				System.out.println(counter++ + ": View Book");
				System.out.println(counter++ + ": Search Book");
				
				break;
		}
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
	
	private static void viewCustomer(Customer customer)
	{
		
	}
	
	private static void viewBook(Book book)
	{
		
	}
	
	private static void viewMovie(Movie movie)
	{
		
	}
	
	private static void viewCD(CD cd)
	{
		
	}
}
