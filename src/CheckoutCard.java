import java.util.Date;


public class CheckoutCard {
	private long ccId;
	private long customerId;
	private Date checkedOut;
	
	public CheckoutCard(int customerId) {
		this.customerId = customerId;
	}
	
	public CheckoutCard(int customerId, Date checkedOut)
	{
		this.customerId = customerId;
		this.checkedOut = checkedOut;
	}
	
	public long getId()
	{
		return ccId;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long ccId) {
		this.ccId = ccId;
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
