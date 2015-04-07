import java.util.List;

public interface ILibrary {
	boolean addBook(String T);
	boolean deleteBook(Media m);
	boolean addCustomer(String Name);
	boolean removeCustomer(long cid);
	boolean editCustomerName(long cid, String newName);
	boolean checkOutMedia(long cid, long mid);
	boolean checkInMedia(long mid);
	boolean setLatePolicy(int daysUntilLate, float costPerDay);
	boolean editBookTitle(long mid, String newTitle);
	boolean editBookAuthor(long mid, String newAuthor);
	boolean editBookPublisher(long mid, String newPublisher);
	
	//Iteration 2
	void viewCustomer(long mid);
	void viewBook(long bid);
	boolean isMediaCheckedOut(long mid, ErrorContainer err);
	float calculateMediaLateFee(long mid, ErrorContainer err);
	Error payMediaLateFee(long mid, float amount);
	List<Book> searchBooks(Book.BookField field, String searchString);

}
