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
	
	private String url;
	private String user;
	private String password;

	public DatabaseSupport(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	private boolean executeSql(String sql, List<String> preparedStrings) {
		Connection con = null;
		try {
			con = DriverManager.getConnection(url, user, password);

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
			con = DriverManager.getConnection(url, user, password);
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
			con = DriverManager.getConnection(url, user, password);
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
			con = DriverManager.getConnection(url, user, password);
			
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
	// TODO: use a prepared int to insert the book id into the sql query (safer than string manipulation)
	public boolean putCustomer(Customer c) {
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
			con = DriverManager.getConnection(url, user, password);
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
			con = DriverManager.getConnection(url, user, password);
			
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
		String sql = "INSERT INTO CheckoutCards " +
				"(customerid, mediaid, timeout, timein) " +
				"VALUES (?, ?, ?, ?);";
		return executeCheckoutCardSql(sql, cc);
	}
	
	private boolean updateOldCheckoutCard(CheckoutCard cc) {
		String sql = "UPDATE CheckoutCards SET " +
				"customerid = ?, mediaid = ?, timeout = ?, timein = ? WHERE id = " + cc.getId() + ";"; 
		return executeCheckoutCardSql(sql, cc);
	}
	
	private boolean executeCheckoutCardSql(String sql, CheckoutCard card) {
		Date checkOutDate = card.getCheckOutDate();
		Date checkInDate = card.getCheckInDate();
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
			con = DriverManager.getConnection(url, user, password);
			try {
				PreparedStatement statement = con.prepareStatement(sql);
				statement.setLong(1, card.getCustomerId());
				statement.setLong(2, card.getMediaId());
				statement.setTimestamp(3, checkOutTimestamp);
				statement.setTimestamp(4, checkInTimestamp);
				
				statement.execute();
			} finally {
				con.close();
			}
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	@Override
	public List<CheckoutCard> getCheckoutCardsForMedia(long mid) {
		String sql = "SELECT " +
				"CheckoutCards.id AS id," +
				"CheckoutCards.customerid AS customerid," +
				"CheckoutCards.mediaid AS mediaid," +
				"CheckoutCards.timeout AS timeout," +
				"CheckoutCards.timein AS timein " +
				"FROM CheckoutCards WHERE mediaid='"+ mid +"'";

		List<CheckoutCard> cards = new ArrayList<CheckoutCard>();

		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, user, password);
			ResultSet resultSet = null;
			try {
				Statement statement = connection.createStatement();
				resultSet = statement.executeQuery(sql);

				while(resultSet.next()) {
					long id = resultSet.getLong("id");
					long customerId = resultSet.getLong("customerid");
					long mediaId = resultSet.getLong("mediaid");
					Timestamp checkOutTime = resultSet.getTimestamp("timeout");
					Timestamp checkInTime = resultSet.getTimestamp("timein");
					Date checkOutDate = null;
					Date checkInDate = null;
					if(checkOutTime != null) {
						checkOutDate = new Date(checkOutTime.getTime());
					}
					if(checkInTime != null) {
						checkInDate = new Date(checkInTime.getTime());
					}
					cards.add(new CheckoutCard(id, customerId, mediaId, checkOutDate, checkInDate));
				}
			} finally {
				connection.close();
			}
		} catch (SQLException e) {
			return null;
		}

		return cards;
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
	
}