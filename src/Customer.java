import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Customer implements ICustomer{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private String name;

	@OneToMany(mappedBy="customer", cascade=CascadeType.ALL)
	Set<CheckoutCard> checkoutCards;

	public Customer()
	{
		id = -1;
		name = "";
	}

	public Customer(String name)
	{
		this.id = -1;
		this.name = name;
	}

	public Customer(long cid, String name) {
		this.id = cid;
		this.name = name;
	}

	public long getId()
	{
		return id;
	}

	public void setId(long cid)
	{
		id = cid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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

	@Override
	public String toString() {
		String result = "Customer [id=" + id + ", name=" + name + "]\n";
		Set<CheckoutCard> cardsCheckedOut = getCurrentlyCheckedOut(checkoutCards);
		result += "  With " + cardsCheckedOut.size() + " piece(s) of media checked out:\n";
		for(CheckoutCard card : cardsCheckedOut) {
			result += "    " + card.getMedia().toString() + "\n";
		}
		return result;
	}

	private Set<CheckoutCard> getCurrentlyCheckedOut(Set<CheckoutCard> cardsIn) {
		Set<CheckoutCard> cardsOut = new HashSet<CheckoutCard>();
		for(CheckoutCard card : cardsIn) {
			if(isCheckedOut(card)) {
				cardsOut.add(card);
			}
		}
		return cardsOut;
	}

	private boolean isCheckedOut(CheckoutCard card) {
		return card.getCheckOutDate() != null && card.getCheckInDate() == null;
	}
}
