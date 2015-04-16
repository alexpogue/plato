import java.util.Date;
import java.util.List;

public class Library implements ILibrary{

	private IDatabaseSupport databaseSupport;
	private IUser loggedIn;
	
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
		return databaseSupport.removeMedia(mid);
	}

	@Override
	public boolean addCustomer(String name) {	
		Customer customer = new Customer(name);
		return databaseSupport.putCustomer(customer);
	}

	@Override
	public boolean removeCustomer(long cid) {
		return databaseSupport.removeCustomer(cid);
	}
	
	public boolean editCustomerName(long cid, String newName)
	{
		Customer customer = databaseSupport.getCustomer(cid);
		customer.setName(newName);

		return databaseSupport.putCustomer(customer);
	}

	@Override
	public boolean checkOutMedia(long cid, long mid) {
		if(databaseSupport.getMedia(mid) == null) {
			// TODO: communicate these specific errors to the outside world
			return false;
		}
		if(databaseSupport.getCustomer(cid) == null) {
			return false;
		}

		List<CheckoutCard> cards = databaseSupport.getCheckoutCardsForMedia(mid);
		CheckoutCard recent = findMostRecentCard(cards);
		if(recent.isCheckedOut()) {
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
		List<CheckoutCard> cards = databaseSupport.getCheckoutCardsForMedia(mid);
		// TODO: validate this checkout card's fields
		CheckoutCard cc = findMostRecentCard(cards);
		if(cc == null) {
			// no checkout card exists for this media
			return false;
		}
		if(cc.isCheckedOut() == false) {
			// media is not checked out
			return false;
		}
		long cid = cc.getCustomerId();
		if(databaseSupport.getCustomer(cid) == null) {
			// customer is not in the system
			return false;
		}
		cc.setCheckInDate(new Date());
		return databaseSupport.putCheckoutCard(cc);
	}

	private CheckoutCard findMostRecentCard(List<CheckoutCard> cards) {
		CheckoutCard mostRecent = cards.get(0);
		for(CheckoutCard card : cards) {
			if(card.getCheckOutDate() == null) {
				continue;
			}
			if(card.getCheckOutDate().after(mostRecent.getCheckOutDate())) {
				mostRecent = card;
			}
		}
		return mostRecent;
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
	
	//START ITERATION 2 HERE!

	public boolean isMediaCheckedOut(long mid, ErrorContainer err){
		Media m = databaseSupport.getMedia(mid);
		if(m == null) {
			err.setError(IErrorContainer.ErrorCode.MediaNotFound);
			return false;
		}
		CheckoutCard cc = databaseSupport.getMostRecentCheckoutCardForMedia(mid);
		boolean b = cc.isCheckedOut();
		err.setError(IErrorContainer.ErrorCode.Success);
		return b;
	}

	public float calculateMediaLateFee(long mid, ErrorContainer err){
		Media.MediaType mt = databaseSupport.getMediaType(mid);
		if(mt == Media.MediaType.Error) {
			// couldn't find the media
			err.setError(IErrorContainer.ErrorCode.MediaNotFound);
			return -1.0f;
		}
		CheckoutCard cc = databaseSupport.getMostRecentCheckoutCardForMedia(mid);
		boolean checkedOut = cc.isCheckedOut();
		if(cc == null || !checkedOut) {
			err.setError(IErrorContainer.ErrorCode.MediaNotCheckedOut);
			return -1.0f;
		}
		LatePolicy lp = databaseSupport.getLatePolicy();
		if(lp == null) {
			err.setError(IErrorContainer.ErrorCode.LatePolicyNotFound);
			return -1.0f;
		}
		Float fee = (new LateFeeCalculator()).calculateMediaLateFee(mt, cc, lp);
		err.setError(IErrorContainer.ErrorCode.Success);
		return fee;
	}
	/*
	 * TODO it will be fixed after ErrorContainer is done
	 * @see ILibrary#payMediaLateFee(long, float)
	 */
	public IErrorContainer.ErrorCode payMediaLateFee(long mid, float amount){
		Media.MediaType mt = databaseSupport.getMediaType(mid);
		if(mt == Media.MediaType.Error) {
			return IErrorContainer.ErrorCode.MediaNotFound;
		}
		CheckoutCard cc = databaseSupport.getMostRecentCheckoutCardForMedia(mid);
		boolean checkedOut = cc.isCheckedOut();
		if(cc == null || !checkedOut) {
			return IErrorContainer.ErrorCode.MediaNotCheckedOut;
		}
		LatePolicy lp = databaseSupport.getLatePolicy();
		if(lp == null) {
			return IErrorContainer.ErrorCode.LatePolicyNotFound;
		}
		boolean goodAmount = (new LateFeeCalculator()).amountIsAppropriate(mt, cc, lp, amount);
		if(!goodAmount) {
			return IErrorContainer.ErrorCode.BadAmount;
		}
		cc.payLateFee(amount);
		databaseSupport.putCheckoutCard(cc);
		return null;
	}

	@Override
	public List<Book> searchBooks(Book.BookField field, String searchString) {
		return databaseSupport.searchBooks(field, searchString);
	}

	@Override
	public void viewCustomer(long cid, ICustomerDisplay cd) {
		Customer c = databaseSupport.getCustomer(cid);
		cd.display(c);
		
	}

	@Override
	public void viewBook(long bid, IBookDisplay bd) {
		Book b = (Book)databaseSupport.getMedia(bid);
		bd.display(b);
	}

	@Override
	public IUser.UserType login(String uid, String pass) {
		if(loggedIn != null)
		{
			// A user is already logged in
			return null;
		}
		else
		{
			User u = databaseSupport.getUser(uid);
			if(u == null)
				return null;
	
			IUser.UserType utype = u.validate(pass);
			if(utype != null)
			{
				loggedIn = u;
			}
			return utype;
		}
	}

	@Override
	public void logout(User u) {
		if(loggedIn != null)
		{
			loggedIn = null;
		}
		
		return;
	}
	
}
