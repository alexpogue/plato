import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseSupport implements IDatabaseSupport {

	private DatabaseSupportTester data = new DatabaseSupportTester();

	public DatabaseSupport(String url, String user, String password) {
		this.data.url = url;
		this.data.user = user;
		this.data.password = password;
	}
	
	private boolean executeSql(String sql, List<String> preparedStrings) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(data.url, data.user, data.password);

			try {
				PreparedStatement statement = con.prepareStatement(sql);
				for(int i = 0; i < preparedStrings.size(); i++) {
					statement.setString(i+1, preparedStrings.get(i));
				}
				statement.executeUpdate();
				return true;
			} finally {
				con.close();
			}
		} catch (SQLException e) {
			return false;
		}
	}

	private String typeToTableName(Media.Type type)
	{
		switch(type)
		{
			case Book:
				return "Books";
		}
		
		return null;
	}
	
	public boolean putMedia(Media m) {
		if(m.getId() < 0) {
			return putNewMedia(m);
		}
		else {
			return updateOldMedia(m);
		}
	}

	private boolean putNewMedia(Media m) {
		boolean success;
		Media.Type type = m.getType();
		long id = getNewMediaId();
		m.setId(id);
		switch(type) {
		case Book:
			success = putBook((Book) m);
			break;
		default:
			success = false;
			break;
		}
		return success;
	}

	private long getNewMediaId() {
		String sql = "INSERT INTO Media DEFAULT VALUES";
		Connection con = null;
		try {
			con = DriverManager.getConnection(data.url, data.user, data.password);
			PreparedStatement statement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.executeUpdate();
			ResultSet generatedKeys = statement.getGeneratedKeys();
			if(generatedKeys.next()) {
				return generatedKeys.getLong(1);
			}
			else {
				return -1;
			}
		} catch (SQLException e) {
			return -1;
		}
	}

	private boolean putBook(Book b) {
		// TODO: parameterize id rather than doing string manipulation (safer)
		String sql = "INSERT INTO Books " +
				"(id, title, author, publisher, isbn) VALUES (" + b.getId() + ", ?, ?, ?, ?)";
		List<String> preparedStrings = getBookPreparedStrings(b);

		return executeSql(sql, preparedStrings);
	}

	private boolean updateOldMedia(Media m) {
		boolean success;
		Media.Type type = m.getType();
		switch(type) {
		case Book:
			success = updateBook((Book) m);
			break;
		default:
			success = false;
			break;
		}
		return success;
	}

	private boolean updateBook(Book b) {
		// TODO: use a prepared int to insert the book id into the sql query (safer than string manipulation)
		String sql = "UPDATE Books SET " +
				"title = ?, author = ?, publisher = ?, isbn = ? WHERE id = " + b.getId(); 
		List<String> preparedStrings = getBookPreparedStrings(b);

		return executeSql(sql, preparedStrings);
	}

	private List<String> getBookPreparedStrings(Book b) {
		List<String> preparedStrings = new ArrayList<String>();
		preparedStrings.add(b.getTitle());
		preparedStrings.add(b.getAuthor());
		preparedStrings.add(b.getPublisher());
		preparedStrings.add(b.getIsbn());
		return preparedStrings;
	}

	private Book getBook(long bid) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(data.url, data.user, data.password);
			ResultSet rs = null;

			String sql = "SELECT " +
					"Books.title AS title," +
					"Books.author AS author," +
					"Books.publisher AS publisher," +
					"Books.isbn AS isbn " +
					"FROM Books WHERE id='"+ bid +"'";

			String title, author, publisher, isbn;

			try {
				Statement stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				rs.next();

				title = rs.getString("title");
				author = rs.getString("author");
				publisher = rs.getString("publisher");
				isbn = rs.getString("isbn");

			} finally {
				con.close();
				if(rs != null) {
					rs.close();
				}
			}

			return new Book(bid, title, author, publisher, isbn);
		} catch (SQLException e) {
			return null;
		}
	}

	@Override
	public Media getMedia(long mid) {
		// for now we assume media can only be a book
		return getBook(mid);
	}

	@Override
	public boolean removeMedia(Media m) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(data.url, data.user, data.password);
			
			Media.Type type = m.getType();
			
			String sql1 = "DELETE FROM ?" +
					" WHERE id=?";
			
			String sql2 = "DELETE FROM Media WHERE id=?";
			
			try {
				
				PreparedStatement pStmt = con.prepareStatement(sql1);
				String s = typeToTableName(type);
				pStmt.setString(1, s);
				pStmt.setLong(2, m.getId());
				pStmt.executeQuery();
				
				PreparedStatement pStmt2 = con.prepareStatement(sql2);
				pStmt2.setLong(1, m.getId());
				pStmt2.executeQuery(sql2);
				
			} finally {
				con.close();
			}
			
			return true;
		} catch (SQLException e) {
			return false;
		}
		
	}
	
	@Override
	public boolean putCustomer(Customer c) {
		// TODO Customer table needs to be added to database
		String sql = "INSERT INTO Customers " +
				"(name, balance) VALUES (" +
				 + c.getId() + ", ?, " + c.getBalanceOwed() + " )";
		
		List<String> preparedStrings = new ArrayList<String>();
		preparedStrings.add(c.getName());

		return executeSql(sql, preparedStrings);
	}
	
	public List<String> getCustomerPreparedStrings(Customer c)
	{
		List<String> preparedStrings = new ArrayList<String>();
		preparedStrings.add(c.getName());
		return preparedStrings;
	}

	@Override
	public Customer getCustomer(long cid) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(data.url, data.user, data.password);
			ResultSet rs = null;

			String sql = "SELECT " +
					"Customers.name AS name," +
					"Customers.balance AS balance " +
					"FROM Customers WHERE id=?";

			String name;
			float balanceOwed;

			try {
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setLong(1, cid);
				rs = stmt.executeQuery(sql);
				rs.next();

				name = rs.getString("name");
				balanceOwed = rs.getFloat("balance");

			} finally {
				con.close();
				if(rs != null) {
					rs.close();
				}
			}

			return new Customer(cid, name, balanceOwed);
			
		} catch (SQLException e) {
			return null;
		}
	}


	@Override
	public boolean removeCustomer(long cid) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(data.url, data.user, data.password);
			
			String sql = "DELETE FROM Customers WHERE id=?";
			
			try {
				
				PreparedStatement stmt = con.prepareStatement(sql);
				stmt.setLong(1, cid);
				stmt.executeQuery(sql);
				
			} finally {
				con.close();
			}
			
			return true;
		} catch (SQLException e){
			return false;
		}
	}

	@Override
	public boolean putCheckoutCard(CheckoutCard cc) {
		long ccId = cc.getId();
		boolean success;
		if(ccId < 0) {
			success = putNewCheckoutCard(cc);
		}
		else {
			success = updateOldCheckoutCard(cc);
		}
		return success;
	}

	private boolean putNewCheckoutCard(CheckoutCard cc) {
		Date checkOutDate = cc.getCheckOutDate();
		Date checkInDate = cc.getCheckInDate();
		Timestamp checkInTimestamp = null;
		Timestamp checkOutTimestamp = null;

		if(checkOutDate != null) {
			checkOutTimestamp = new Timestamp(checkOutDate.getTime());
		}
		if(checkInDate != null) {
			checkInTimestamp = new Timestamp(checkInDate.getTime());
		}

		Connection con = null;
		try {
			con = DriverManager.getConnection(data.url, data.user, data.password);
			ResultSet rs = null;

			String sql = "INSERT INTO CheckoutCards " +
					"(customerid, mediaid, timeout, timein) " +
					"VALUES (?, ?, ?, ?);";

			try {
				PreparedStatement statement = con.prepareStatement(sql);
				statement.setLong(1, cc.getCustomerId());
				statement.setLong(2, cc.getMediaId());
				statement.setTimestamp(3, checkOutTimestamp);
				statement.setTimestamp(4, checkInTimestamp);
				
				statement.execute();

			} finally {
				con.close();
				if(rs != null) {
					rs.close();
				}
			}
		} catch (SQLException e) {
			return false;
		}
		
		return true;
	}

	private boolean updateOldCheckoutCard(CheckoutCard cc) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CheckoutCard getRecentCheckoutCardForMedia(long mid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LatePolicy getLatePolicy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean putLatePolicy(LatePolicy lp) {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected void printBookTable() {
		
		Connection con = null;
		try {
			con = DriverManager.getConnection(data.url, data.user, data.password);
			ResultSet rs = null;

			String sql = "SELECT " +
					"Books.id AS id," +
					"Books.title AS title," +
					"Books.author AS author," +
					"Books.publisher AS publisher," +
					"Books.isbn AS isbn " +
					"FROM Books";
			
			long id;
			String title, author, publisher, isbn;

			try {
				Statement stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{
					id = rs.getLong("id");
					title = rs.getString("title");
					author = rs.getString("author");
					publisher = rs.getString("publisher");
					isbn = rs.getString("isbn");
					
					System.out.println("Book Id: " + id);
					System.out.println("Title: " + title);
					System.out.println("Author: " + author);
					System.out.println("Publisher: " + publisher);
					System.out.println("ISBN: " + isbn + "\n");
				}

			} finally {
				con.close();
				if(rs != null) {
					rs.close();
				}
			}
			
			return;
			
		} catch (SQLException e) {
			return;
		}
			
	}
	
	protected void printCustomerTable() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(data.url, data.user, data.password);
			ResultSet rs = null;

			String sql = "SELECT " +
					"Customers.id AS id, " +
					"Customers.name AS name, " +
					"Customers.balance AS balance " +
					"FROM Customers";
			
			long id;
			float balance;
			String name;
			
			try {
				Statement stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				
				while(rs.next())
				{
					id = rs.getLong("id");
					name = rs.getString("name");
					balance = rs.getFloat("balance");
					
					System.out.println("Customer ID: " + id);
					System.out.println("Name: " + name);
					System.out.println("Balance: " + balance + "\n");
				}
				
			} finally {
				con.close();
				if(rs != null) {
					rs.close();
				}
			}
			
			return;
			
		} catch (SQLException e) {
			return;
		}
	}
	
	protected void printCheckoutCardTable() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(data.url, data.user, data.password);
			ResultSet rs = null;

			String sql = "INSERT INTO CheckoutCards " +
					"(customerid, mediaid, timeout, timein) " +
					"VALUES (?, ?, ?, ?);";
			
			return;
			
		} catch (SQLException e) {
			return;
		}
	}
}