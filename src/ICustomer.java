
public interface ICustomer {
	boolean editCustomer(int field, String newValue);
	boolean addCheckoutCard(CheckoutCard cc);
	boolean removeCheckoutCard();
}
