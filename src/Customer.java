import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer implements ICustomer{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String name;

	public Customer()
	{
		id = -1;
		name = "";
	}
	
	public Customer(String name)
	{
		this.name = name;
	}

	public Customer(long cid, String name) {
		this.id = cid;
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
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
	
	public String toString() {
		return "Customer: id = " + id + ", name = " + name;
	}
	
}
