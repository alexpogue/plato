
public interface ILibrary {
	boolean addBook(String T);
	boolean deleteBook(long mid);
	boolean editBook(long mid, int field, String newValue);
	boolean addCustomer(long cid);
	boolean removeCustomer(long cid);
	boolean editCustomer(long cid, int field, String newValue);
	boolean checkOutMedia(long cid, long mid);
	boolean checkInMedia(long mid);
	boolean setLatePolicy(int daysUntilLate, float costPerDay);
}
