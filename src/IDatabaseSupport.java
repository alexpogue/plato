
public interface IDatabaseSupport {
	Media getMedia(long mid);
	boolean putMedia(Media m);
	Customer getCustomer(long cid);
	boolean putCustomer(Customer c);
	CheckoutCard getRecentCheckoutCardForMedia(long mid);
	boolean putCheckoutCard(CheckoutCard cc);
	LatePolicy getLatePolicy();
	boolean putLatePolicy(LatePolicy lp);
}
