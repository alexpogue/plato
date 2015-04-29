import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class CheckoutCard implements ICheckoutCard{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="customerId")
	private Customer customer;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="mediaId")
	private Media media;

	@Temporal(TemporalType.TIMESTAMP)
	private Date checkOutDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date checkInDate;
	private float lateFeeAmountPaid;

	public CheckoutCard() {
		init(-1, null, null, null, null);
	}

	public CheckoutCard(long cardId, Customer customer, Media media) {
		this.id = cardId;
		this.customer = customer;
		this.media = media;
	}

	public CheckoutCard(Customer customer, Media media) {
		init(-1, customer, media, new Date(), null);
	}

	public CheckoutCard(Customer customer, Media media, Date checkedOut)
	{
		init(-1, customer, media, checkedOut, null);
	}

	public CheckoutCard(Customer customer, Media media, Date checkedOut, Date checkedIn)
	{
		init(-1, customer, media, checkedOut, checkedIn);
	}

	public CheckoutCard(long cardId, Customer customer, Media media, Date checkedOut, Date checkedIn) {
		init(cardId, customer, media, checkedOut, checkedIn);
	}

	private void init(long id, Customer c, Media m, Date out, Date in) {
		this.id = id;
		this.customer = c;
		this.media = m;
		this.checkOutDate = out;
		this.checkInDate = in;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public boolean isCheckedOut() {
		return checkOutDate != null && checkInDate == null;
	}

	public float getLateFeeAmountPaid() {
		return lateFeeAmountPaid;
	}

	public void setLateFeeAmountPaid(float lateFeeAmountPaid) {
		this.lateFeeAmountPaid = lateFeeAmountPaid;
	}

	public void payLateFee(float amount) {
		lateFeeAmountPaid += amount;
	}
}
