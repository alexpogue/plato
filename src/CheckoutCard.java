import java.util.Date;


public class CheckoutCard {
	private int customerId;
	private Date checkedOut;
	
	public CheckoutCard(int customerId) {
		this.customerId = customerId;
	}
	
	public CheckoutCard(int customerId, Date checkedOut)
	{
		this.customerId = customerId;
		this.checkedOut = checkedOut;
	}
	
	public int getCustomerId() {
		return customerId;
	}
	
	public Date getCheckoutDate()
	{
		return checkedOut;
	}
	
	public void setCheckoutDate(Date checkoutDate)
	{
		this.checkedOut = checkoutDate;
	}
}
