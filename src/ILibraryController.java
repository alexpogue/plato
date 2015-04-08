import java.util.List;

public interface ILibraryController {
	boolean addBook(String T);
	boolean deleteBook(Media m);
	boolean addCustomer(String name);
	boolean removeCustomer(long cid);
	boolean checkOutMedia(long cid, long mid);
	boolean checkInMedia(long mid);
	boolean setLatePolicy(int daysUntilLate, float costPerDay);
	
	//Iteration 2
	void viewCustomer(long mid, ICustomerDisplay cd);
	void viewBook(long bid, IBookDisplay bd);
	boolean isMediaCheckedOut(long mid, ErrorContainer err);
	float calculateMediaLateFee(long mid, ErrorContainer err);
	IErrorContainer.ErrorCode payMediaLateFee(long mid, float amount);
	List<Book> searchBooks(Book.BookField field, String searchString);
	
	//Library is going to handle logins
	public IUser.UserType login(String uid, String pass);
	public void logout(User u);

}
