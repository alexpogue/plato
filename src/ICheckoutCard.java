import java.util.Date;

public interface ICheckoutCard {
	long getId();
	void setId(long id);
	Customer getCustomer();
	void setCustomer(Customer c);
	Media getMedia();
	void setMedia(Media m);
	Date getCheckOutDate();
	void setCheckOutDate(Date d);
	Date getCheckInDate();
	void setCheckInDate(Date d);
	boolean isCheckedOut();
}
