
public interface ILibrary {
	boolean addBook(String T);
	boolean deleteBook(long mid);
	boolean addCustomer(String Name);
	boolean removeCustomer(long cid);
	boolean editCustomerName(long cid, String newName);
	boolean checkOutMedia(long cid, long mid);
	boolean checkInMedia(long mid);
	boolean setLatePolicy(int daysUntilLate, float costPerDay);
	boolean editBook(String title, String field, String newValue);
}
