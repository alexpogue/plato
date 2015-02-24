
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
	public boolean editBook(long mid, int field, String newValue) {
		return library.editBook(mid, field, newValue);
	}

	@Override
	public boolean addCustomer(long cid) {
		return library.addCustomer(cid);
	}

	@Override
	public boolean removeCustomer(long cid) {
		return library.removeCustomer(cid);
	}

	@Override
	public boolean editCustomer(long cid, int field, String newValue) {
		return library.editCustomer(cid, field, newValue);
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

}
