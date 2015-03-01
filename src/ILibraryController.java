
public interface ILibraryController {
	boolean addBook(String T);
	boolean deleteBook(Media m);
	boolean addCustomer(String name);
	boolean removeCustomer(long cid);
	boolean checkOutMedia(long cid, long mid);
	boolean checkInMedia(long mid);
	boolean setLatePolicy(int daysUntilLate, float costPerDay);
}
