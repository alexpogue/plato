
public interface ILateFeeCalculator {
	float calculateMediaLateFee(Media.MediaType mt, CheckoutCard cc, LatePolicy lp);
	boolean amountIsAppropriate(Media.MediaType mt, CheckoutCard cc, LatePolicy lp, float amount);
}
