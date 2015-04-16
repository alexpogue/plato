
public class Customer implements ICustomer{
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
