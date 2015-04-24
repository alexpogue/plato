import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="mediatype")
public abstract class Media implements IMedia {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
