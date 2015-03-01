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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean checkInMedia(long mid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setLatePolicy(int daysUntilLate, float costPerDay) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
