
public class Customer implements ICustomer{
	
	private String name;
	private int balance = 0;
	
	// This silly thing complains when it might not get used.
	// TODO remove after Class is done, to denote the warnings we still have.
	@SuppressWarnings("unused")
	private long customerId = -1;
	private RentalHistory customerHistory;
	
	public Customer(String name)
	{
		this.name = name;
		customerHistory = new RentalHistory();
	}

	public Customer(long cid, String name, RentalHistory rentalHistory, int balanceOwed) {
		this.customerId = cid;
		this.name = name;
		this.customerHistory = rentalHistory;
		this.balance = balanceOwed;
	}

	@Override
	public boolean addCheckoutCard(CheckoutCard cc) 
	{
		return customerHistory.addCheckoutCard(cc);
	}

	@Override
	public boolean removeCheckoutCard(CheckoutCard cc) 
	{
		return customerHistory.removeCheckoutCard(cc);
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public RentalHistory getRentalHistory()
	{
		return customerHistory;
	}
	
	public int getBalanceOwed()
	{
		return balance;
	}
	
	public void setBalance(int balance)
	{
		this.balance = balance;
	}
	
}
