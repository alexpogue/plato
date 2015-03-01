import java.util.Date;


public class CheckoutCard {
	private long cardId;
	private long customerId;
	private long mediaId;
	private Date checkedOut;
	private Date checkedIn;
	
	public CheckoutCard(long customerId, long mediaId) {
		init(-1, customerId, mediaId, new Date(), null);
	}
	
	public CheckoutCard(long customerId, long mediaId, Date checkedOut)
	{
		init(-1, customerId, mediaId, checkedOut, null);
	}

	public CheckoutCard(long customerId, long mediaId, Date checkedOut, Date checkedIn)
	{
		init(-1, customerId, mediaId, checkedOut, checkedIn);
	}

	public CheckoutCard(long cardId, long customerId, long mediaId, Date checkedOut, Date checkedIn) {
		init(cardId, customerId, mediaId, checkedOut, checkedIn);
	}

	private void init(long id, long cid, long mid, Date out, Date in) {
		cardId = id;
		customerId = cid;
		mediaId = mid;
		checkedOut = out;
		checkedIn = in;
	}

	public long getId() {
		return cardId;
	}

	public void setId(long id) {
		cardId = id;
	}
	
	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public long getMediaId() {
		return mediaId;
	}

	public void setMediaId(long mediaId) {
		this.mediaId = mediaId;
	}

	public Date getCheckOutDate() {
		return checkedOut;
	}
	
	public void setCheckOutDate(Date checkOutDate) {
		checkedOut = checkOutDate;
	}

	public Date getCheckInDate() {
		return checkedIn;
	}

	public void setCheckInDate(Date checkInDate) {
		checkedIn = checkInDate;
	}
}
