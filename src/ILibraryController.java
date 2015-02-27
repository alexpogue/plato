
public interface ILibraryController {
	boolean addBook(String T);
	boolean deleteBook(long mid);
	boolean addCustomer(long cid);
	boolean removeCustomer(long cid);
	boolean checkOutMedia(long cid, long mid);
	boolean checkInMedia(long mid);
	boolean setLatePolicy(int daysUntilLate, float costPerDay);
}
