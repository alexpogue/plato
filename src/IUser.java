
public interface IUser {
	UserType validate(String pass);

	public static enum UserType {
		Admin, Employee, Customer
	}
}