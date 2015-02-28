import java.util.Date;


public class Library implements ILibrary{

	private IDatabaseSupport databaseSupport;
	
	public String editTitle(String s){
		return "";
	}

	@Override
	public boolean addBook(String T) {
		Book B= new Book();
		B.setTitle(T);
		return databaseSupport.putMedia(B);
	}

	@Override
	public boolean deleteBook(long mid) {
		// TODO Make the call to delete the tuple with the media ID specified
		databaseSupport.getMedia(mid);
		//TODO remove the media
		return true;
	}

	@Override
	public boolean addCustomer(long cid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeCustomer(long cid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkOutMedia(long cid, long mid) {
		Media m = databaseSupport.getMedia(mid);
		Customer c = databaseSupport.getCustomer(cid);
		if(m == null || c == null) {
			// TODO: communicate these specific errors to the outside world
			return false;
		}
		CheckoutCard cc = new CheckoutCard(cid, mid);
		return databaseSupport.putCheckoutCard(cc);
	}

	@Override
	public boolean checkInMedia(long mid) {
		if(databaseSupport.getMedia(mid) == null) {
			// media with specified id does not exist
			return false;
		}
		CheckoutCard cc = databaseSupport.getRecentCheckoutCardForMedia(mid);
		if(cc == null) {
			// no checkout card exists for this media
			return false;
		}
		if(cc.getCheckInDate() != null) {
			// media has already been checked in
			return false;
		}
		cc.setCheckInDate(new Date());
		return databaseSupport.putCheckoutCard(cc);
	}

	@Override
	public boolean setLatePolicy(int daysUntilLate, float costPerDay) {
		LatePolicy lp = new LatePolicy(daysUntilLate, costPerDay);
		return databaseSupport.putLatePolicy(lp);
	}
	
}
