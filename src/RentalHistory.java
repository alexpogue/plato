import java.util.ArrayList;


public class RentalHistory implements IRentalHistory {

	// TODO 
	// Maybe we need to give Checkout cards IDs and just create an ArrayList of all the IDs
	ArrayList<Long> rentalHistory = new ArrayList<Long>();
	
	@Override
	public boolean addCheckoutCard(CheckoutCard cc) {
		
		boolean valid = true;
		// TODO If the CheckoutCard is valid...
		if (!valid)
		{
			return false;
		}
		else
		{
			long id = cc.getId();
			rentalHistory.add(id);
			return true;
		}
	}

	@Override
	public boolean removeCheckoutCard(CheckoutCard cc) 
	{
		long id = cc.getId();
		if(rentalHistory.contains(id))
		{
			return rentalHistory.remove(id);
		}
		else
			return false;
	}

}
