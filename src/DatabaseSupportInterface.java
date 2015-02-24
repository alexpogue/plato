
public interface DatabaseSupportInterface {
	boolean putMedia(Media m); 
	Media getMedia(long mid);
	boolean putCustomer(Customer c);
	Customer getCustomer(long cid);
}
