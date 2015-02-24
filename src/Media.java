
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
	
//	public void accept(Visitor visitor) {
//		visitor.visit(this);
//	}
	public static enum Type {
		Book
	}
}
