
public abstract class Media {
	private int id;
	private CheckoutCard card;
	private Type type;
	
	public Media(int id, Type type) {
		this.id = id;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
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
