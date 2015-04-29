import java.util.List;

public interface ILibrary {
	boolean addBook(String T);
	boolean deleteBook(long mid);
	boolean addCustomer(String Name);
	boolean editCustomerName(long cid, String newName);
	boolean removeCustomer(long cid);
	boolean checkOutMedia(long cid, long mid);
	boolean checkInMedia(long mid);
	boolean setLatePolicy(int daysUntilLate, float costPerDay);
	boolean editBookTitle(long mid, String newTitle);
	boolean editBookAuthor(long mid, String newAuthor);
	boolean editBookPublisher(long mid, String newPublisher);
	
	//Iteration 2
	void viewCustomer(long mid, ICustomerDisplay cd);
	void viewBook(long bid, IBookDisplay bd);
	boolean isMediaCheckedOut(long mid, ErrorContainer err);
	float calculateMediaLateFee(long mid, ErrorContainer err);
	IErrorContainer.ErrorCode payMediaLateFee(long mid, float amount);
	List<Book> searchBooks(Book.BookField field, String searchString);
	
	//This may be a bit strange, but our application tracks who is currently logged in.
	IUser.UserType login(String uid, String pass);
	void logout(User u);
	
	//Iteration 3
	boolean deleteMovie(long mid);
	boolean addMovie(String t);
	boolean editMovieTitle(long mid, String newValue);
	boolean editMovieGenre(long mid, String newValue);
	boolean deleteCD(long cid);
	boolean addCD(String t);
	boolean editCDTitle(long cid, String newValue);
	boolean editCDGenre(long cid, String newValue);
}
