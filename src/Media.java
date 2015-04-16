
public abstract class Media implements IMedia {
	private long id;
	private MediaType type;

	public Media(long id, MediaType type) {
		this.id = id;
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public MediaType getType() {
		return type;
	}

	public void setType(MediaType type) {
		this.type = type;
	}

	public static enum MediaType {
		Error,
		Book
	}
}
