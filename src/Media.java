
public abstract class Media implements IMedia {
	private long id;
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
}
