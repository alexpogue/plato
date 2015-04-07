import java.util.List;

//TODO we want a main method that tests our library function
public class LibraryController implements ILibraryController {
	private ILibrary library;
	
	public LibraryController() {
		library = new Library();
	}

	@Override
	public boolean addBook(String T) {
		return library.addBook(T);
	}

	@Override
	public boolean deleteBook(Media m) {
		return library.deleteBook(m);
	}

	@Override
	public boolean addCustomer(String name) {
		return library.addCustomer(name);
	}

	@Override
	public boolean removeCustomer(long cid) {
		return library.removeCustomer(cid);
	}

	@Override
	public boolean checkOutMedia(long cid, long mid) {
		return library.checkOutMedia(cid, mid);
	}

	@Override
	public boolean checkInMedia(long mid) {
		return library.checkInMedia(mid);
	}

	@Override
	public boolean setLatePolicy(int daysUntilLate, float costPerDay) {
		return library.setLatePolicy(daysUntilLate, costPerDay);
	}

	@Override
	public boolean isMediaCheckedOut(long mid, ErrorContainer err) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public float calculateMediaLateFee(long mid, ErrorContainer err) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Error payMediaLateFee(long mid, float amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Book> searchBooks(Book.BookField field, String searchString) {
		// TODO Auto-generated method stub
		return null;
	}

}
