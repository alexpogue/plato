
public class Customer implements ICustomer {
	
	String name;
	long customerId = -1;
	RentalHistory customerHistory;
	
	public Customer(String name)
	{
		this.name = name;
		// TODO Randomly generate our customerId and assign it
		customerHistory = new RentalHistory();
	}

	// TODO We need to nail down what fields we want, and if we want individual use cases/methods for the fields
	@Override
	public boolean editCustomer(int field, String newValue) {
		return false;
	}

	@Override
	public boolean addCheckoutCard(CheckoutCard cc) {
		return customerHistory.addCheckoutCard(cc);
	}

	@Override
	public boolean removeCheckoutCard() {
		// TODO pretty sure this doesn't make any dang sense as we don't know which checkoutCard to remove
		return customerHistory.removeCheckoutCard();
	}
	
}
