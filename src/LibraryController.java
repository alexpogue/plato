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
	public boolean deleteBook(long mid) {
		return library.deleteBook(mid);
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
		return library.isMediaCheckedOut(mid, err);
	}

	@Override
	public float calculateMediaLateFee(long mid, ErrorContainer err) {
		return library.calculateMediaLateFee(mid, err);
	}

	@Override
	public IErrorContainer.ErrorCode payMediaLateFee(long mid, float amount) {
		return library.payMediaLateFee(mid, amount);
	}

	@Override
	public List<Book> searchBooks(Book.BookField field, String searchString) {
		return library.searchBooks(field, searchString);
	}

	@Override
	public void viewCustomer(long cid, ICustomerDisplay cd) {
		library.viewCustomer(cid, cd);
		
	}

	@Override
	public void viewBook(long bid, IBookDisplay bd) {
		library.viewBook(bid, bd);
		
	}

	@Override
	public IUser.UserType login(String uid, String pass) {
		return library.login(uid, pass);
	}

	@Override
	public void logout(User u) {
		library.logout(u);
		return;
	}

}
