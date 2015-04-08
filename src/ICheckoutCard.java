import java.util.Date;

public interface ICheckoutCard {
	long getId();
	void setId(long id);
	long getCustomerId();
	void setCustomerId(long cid);
	long getMediaId();
	void setMediaId(long mid);
	Date getCheckOutDate();
	void setCheckOutDate(Date d);
	Date getCheckInDate();
	void setCheckInDate(Date d);
	boolean isCheckedOut();
}
