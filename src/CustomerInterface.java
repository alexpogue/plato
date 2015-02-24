
public interface CustomerInterface {
	boolean editCustomer(int field, String newValue);
	boolean addCheckoutCard(CheckoutCard cc);
	boolean removeCheckoutCard();
}
