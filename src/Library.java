
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
