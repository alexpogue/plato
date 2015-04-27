import java.util.Date;

public class LateFeeCalculator implements ILateFeeCalculator {

	@Override
	public float calculateMediaLateFee(Media.MediaType mt, CheckoutCard cc, LatePolicy lp) {
		// TODO: Java.util.Date is not accurate - consider using a library (JodaTime?)
		int daysUntilLate = lp.getDaysUntilLate();
		
		long outTime = cc.getCheckOutDate().getTime();
		long now = (new Date()).getTime();
		long diffTime = outTime - now;
		long daysCheckedOut = diffTime / (1000 * 60 * 60 * 24);
		if(daysCheckedOut < daysUntilLate) {
			return 0;
		}
		
		long daysLate = daysUntilLate - daysCheckedOut;

		float costPerDay = lp.getCostPerDayLate();
		
		return costPerDay * daysLate;
	}

	@Override
	public boolean amountIsAppropriate(Media.MediaType mt, CheckoutCard cc, LatePolicy lp, float amount) {
		return amount > 0 && amount <= calculateMediaLateFee(mt, cc, lp);
	}

}
