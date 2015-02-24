import java.util.ArrayList;


public class RentalHistory implements IRentalHistory {

	// TODO 
	// Maybe we need to give Checkout cards IDs and just create an ArrayList of all the IDs
	ArrayList<CheckoutCard> rentalHistory = new ArrayList<>();
	
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
			rentalHistory.add(cc);
			return true;
		}
	}

	@Override
	public boolean removeCheckoutCard() {
		// TODO
		// Hmmm... I think we might need an argument here, otherwise we won't know which CheckoutCard to remove
		return false;
	}

}
