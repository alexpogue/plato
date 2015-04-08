import java.util.List;

public interface IDatabaseSupport {
	boolean putMedia(Media m); 
	Media getMedia(long mid);
	boolean removeMedia(Media m);
	boolean putCustomer(Customer c);
	Customer getCustomer(long cid);
	boolean removeCustomer(long cid);
	List<CheckoutCard> getCheckoutCardsForMedia(long mid);
	boolean putCheckoutCard(CheckoutCard cc);
	LatePolicy getLatePolicy();
	boolean putLatePolicy(LatePolicy lp);
	
	//Iteration 2
	Media.MediaType getMediaType(long mid);
	CheckoutCard getMostRecentCheckoutCardForMedia(long mid);
	List<Book> SearchBooks(Book.BookField field,String searchString);
	User getUser(String uid);

}
