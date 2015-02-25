import java.util.Random;

public class Library implements ILibrary{

	private Random idGenerator = new Random();
	
	public String editTitle(String s){
		return "";
	}

	@Override
	public boolean addBook(String T) {
		// TODO How do we determine the validity of a book title?
		boolean valid = true;
		
		if(!valid)
			return false;
		
		else
		{
			Book b = new Book(generateId(), T);
		}
		// TODO return status of DatabaseSupport.putMedia or .putBook
	}

	@Override
	public boolean deleteBook(long mid) {
		// TODO Make the call to delete the tuple with the media ID specified
		return false;
	}

	// TODO This "responsibility" will likely change into many different methods
	@Override
	public boolean editBook(long mid, int field, String newValue) {
		
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
	
	public long generateId()
	{
		long id = nextLong(idGenerator, Long.MAX_VALUE);
		// TODO Maybe some sort of redundancy check...? 
		// We could also use MD5 hashes as we're just looking for a random number with low chance of collision
		
		// This implementation is probably temporary
		return id;
	}
	
	/**
	 * Code taken from URL: http://stackoverflow.com/questions/2546078/java-random-long-number-in-0-x-n-range
	 * @param rng a Random variable as implemented in java.util.Random
	 * @param n the upper bound on the long number generation
	 * @return next the next random long
	 */
	long nextLong(Random rng, long n) {
	   // error checking and 2^x checking removed for simplicity.
	   long bits, val;
	   do {
	      bits = (rng.nextLong() << 1) >>> 1;
	      val = bits % n;
	   } while (bits-val+(n-1) < 0L);
	   return val;
	}
}
