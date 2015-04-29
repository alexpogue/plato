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
	public boolean editCustomerName(long cid, String newName) {
		return library.editCustomerName(cid, newName);
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
//Iteration 3
	@Override
	public boolean deleteMovie(long mid) {
		return library.deleteMovie(mid);
	}

	@Override
	public boolean addMovie(String t) {
		return library.addMovie(t);
	}

	@Override
	public boolean editMovieTitle(long mid, String newValue) {
		return library.editMovieTitle(mid, newValue);
	}

	@Override
	public boolean editMovieGenre(long mid, String newValue) {
		return library.editMovieGenre(mid, newValue);
	}

	@Override
	public boolean deleteCD(long cid) {
		return library.deleteCD(cid);
	}

	@Override
	public boolean addCD(String t) {
		return library.addCD(t);
	}

	@Override
	public boolean editCDTitle(long cid, String newValue) {
		return library.editCDTitle(cid, newValue);
	}

	@Override
	public boolean editCDGenre(long cid, String newValue) {
		return library.editCDGenre(cid, newValue);
	}

	@Override
	public boolean editBookTitle(long mid, String newTitle) {
		return library.editBookTitle(mid, newTitle);
	}

	@Override
	public boolean editBookAuthor(long mid, String newAuthor) {
		return library.editBookAuthor(mid, newAuthor);
	}

	@Override
	public boolean editBookPublisher(long mid, String newPublisher) {
		return library.editBookPublisher(mid, newPublisher);
	}

	@Override
	public Book getBook(long bid) {
		return library.getBook(bid);
	}

	@Override
	public Customer getCustomer(long cid) {
		return library.getCustomer(cid);
	}

	@Override
	public Movie getMovie(long mid) {
		return library.getMovie(mid);
	}

	@Override
	public CD getCD(long cdid) {
		return library.getCD(cdid);
	}

}
