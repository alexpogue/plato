
public class Customer implements ICustomer{

	private long customerId = -1;
	private String name;
	private float balance = 0.00f;
	
	public Customer(String name)
	{
		this.name = name;
	}

	public Customer(long cid, String name, float balanceOwed) {
		this.customerId = cid;
		this.name = name;
		this.balance = balanceOwed;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public float getBalanceOwed()
	{
		return balance;
	}
	
	public void setBalance(float newBalance)
	{
		this.balance = newBalance;
	}
	
	public long getId()
	{
		return customerId;
	}
	
}
