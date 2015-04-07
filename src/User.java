
public class User implements IUser {

	private String uid;
	private String pass;
	private UserType utype;
	
	public User(String pass, UserType utype)
	{
		this.pass = pass;
		this.utype = utype;
	}
	
	public User(String uid, String pass, UserType utype)
	{
		this.uid = uid;
		this.pass = pass;
		this.utype = utype;
	}
	
	@Override
	public UserType validate(String pass) {
		if(pass == this.pass)
		{
			return utype;
		}
		
		else
		{
			return null;
		}
	}

}
