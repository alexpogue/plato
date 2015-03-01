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
	public boolean editBookTitle(long mid, String newTitle){
		Media m = databaseSupport.getMedia(mid);
		Book b = (Book) m;
		b.setTitle(newTitle);
		return databaseSupport.putMedia(b);
	}
	@Override
	public boolean editBookAuthor(long mid, String newAuthor){
		Media m = databaseSupport.getMedia(mid);
		Book b = (Book) m;
		b.setTitle(newAuthor);
		return databaseSupport.putMedia(b);
	}
	@Override
	public boolean editBookPublisher(long mid, String newPublisher){
		Media m = databaseSupport.getMedia(mid);
		Book b = (Book) m;
		b.setTitle(newPublisher);
		return databaseSupport.putMedia(b);
	}

	@Override
	public boolean deleteBook(long mid) {
		// TODO Make the call to delete the tuple with the media ID specified
		databaseSupport.getMedia(mid);
		// TODO make sure the media is eliminated 
		return true;
	}

	@Override
	public boolean addCustomer(String name) {	
		Customer customer = new Customer(name);
		return databaseSupport.putCustomer(customer);
	}

	@Override
	public boolean removeCustomer(long cid) {
		// TODO Make the call to delete the tuple with the Customer ID specified
		databaseSupport.getCustomer(cid);
		// TODO remove the customer
		return true;
	}
	
	public boolean editCustomerName(long cid, String newName)
	{
		Customer customer = databaseSupport.getCustomer(cid);
		customer.setName(newName);
		
		return databaseSupport.putCustomer(customer);
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
	/* TODO Sort this out
	 * Currently, we just have Book as media, but in the future, costs and the days until it's considered late will change for other types of media.
	 * We're going to need to put one of these in every form of media.
	 */
	public boolean setLatePolicy(int daysUntilLate, float costPerDay) {
		LatePolicy lp = new LatePolicy(daysUntilLate, costPerDay);
		return databaseSupport.putLatePolicy(lp);
	}
	
}
