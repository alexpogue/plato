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
	// viewCustomer(long mid); needs a return type
	// viewBook(long bid); ditto
	boolean isMediaCheckedOut(long mid, ErrorContainer err);
	float calculateMediaLateFee(long mid, ErrorContainer err);
	Error payMediaLateFee(long mid, float amount);
	List<Book> searchBooks(Book.BookField field, String searchString);

}
