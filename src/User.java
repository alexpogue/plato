import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class User implements IUser {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String username;
	private String pass;
	private UserType utype;
	
	public User()
	{
		init(-1, null, null, null);
	}
	
	public User(String username, String pass, UserType utype)
	{
		init(-1, username, pass, utype);
	}

	public void init(long id, String username, String pass, UserType utype)
	{
		this.id = id;
		this.username = username;
		this.pass = pass;
		this.utype = utype;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public UserType getUtype() {
		return utype;
	}

	public void setUtype(UserType utype) {
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
			//Password is incorrect
			return null;
		}
	}

	public String toString() {
		return "User: id = " + id + ", username = " + username + ", pass = " + pass + ", utype = " + utype;
	}

}
