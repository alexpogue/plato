
public interface IDatabaseSupport {
	boolean putMedia(Media m); 
	Media getMedia(long mid);
	boolean removeMedia(long mid);
	boolean putCustomer(Customer c);
	Customer getCustomer(long cid);
	boolean removeCustomer(long cid);
	CheckoutCard getRecentCheckoutCardForMedia(long mid);
	boolean putCheckoutCard(CheckoutCard cc);
	LatePolicy getLatePolicy();
	boolean putLatePolicy(LatePolicy lp);
}
