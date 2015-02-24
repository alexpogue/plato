import java.util.ArrayList;


public class RentalHistory implements IRentalHistory {

	ArrayList<CheckoutCard> rentalHistory = new ArrayList<>();
	
	@Override
	public boolean addCheckoutCard(CheckoutCard cc) {
		
		// TODO If the CheckoutCard is valid...
		rentalHistory.add(cc);
		return true;

		// TODO else ... return false
	}

	@Override
	public boolean removeCheckoutCard() {
		// TODO
		// Hmmm... I think we might need an argument here, otherwise we won't know which CheckoutCard to remove
		return false;
	}

}
