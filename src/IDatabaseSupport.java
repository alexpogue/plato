
public interface IDatabaseSupport {
	boolean putMedia(Media m); 
	Media getMedia(long mid);
	boolean putCustomer(Customer c);
	Customer getCustomer(long cid);
	boolean putCheckoutCard(CheckoutCard cc);
	CheckoutCard getCheckoutCard(long ccid);
}
