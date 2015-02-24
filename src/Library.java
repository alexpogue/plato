public class Library implements ILibrary{

	public String editTitle(String s){
		return "";
	}

	@Override
	public boolean addBook(String T) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteBook(long mid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editBook(long mid, int field, String newValue) {
		// TODO Auto-generated method stub
		return false;
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
	public boolean editCustomer(long cid, int field, String newValue) {
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
