
public abstract class Media {
	private long id;
	private CheckoutCard card;
	private Type type;

	public Media(long id, Type type) {
		this.id = id;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public static enum Type {
		Book
	}
	
	//TODO determine if we want this here or in each form of individual media
	public CheckoutCard checkOut(long cid){
		// TODO flesh out this method
		return null;
		
	// Do we want checkIn within this abstract class
	}
}
