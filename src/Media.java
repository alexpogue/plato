import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="mediatype")
public abstract class Media implements IMedia {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private MediaType type;

	@OneToMany(mappedBy="media", cascade=CascadeType.ALL)
	private Set<CheckoutCard> checkoutCards;

	public Media(long id, MediaType type) {
		this.id = id;
		this.type = type;
		checkoutCards = new HashSet<CheckoutCard>();
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

	public Set<CheckoutCard> getCheckoutCards() {
		return checkoutCards;
	}

	public void setCheckoutCards(Set<CheckoutCard> checkoutCards) {
		this.checkoutCards = checkoutCards;
	}

	public void addCheckoutCard(CheckoutCard card) {
		checkoutCards.add(card);
	}

	public static enum MediaType {
		Error,
		Book,
		Movie,
		CD
	}
}
