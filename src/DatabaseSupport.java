import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseSupport /*implements Visitor*/ {
	
	private static String url;
	private static String user;
	private static String password;
	
	public DatabaseSupport(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

    public static void main(String[] args) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
    
        String url = "jdbc:postgresql://localhost/postgres";
        String user = "user123";
        String password = "postgres";
        
        DatabaseSupport ds = new DatabaseSupport(url, user, password);
        Media m = new Book(0, "The Illiad (Hackett Classics)", "Homer", "Hackett Publishing", "978-0872203525");

        ds.putMedia(m);
        
        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery("SELECT VERSION()");

            if (rs.next()) {
                System.out.println(rs.getString(1));
            }

        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(DatabaseSupport.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(DatabaseSupport.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
    
    private void insertRecord(String url, String user, String password, String sql, List<String> preparedStrings) throws SQLException {
    	// TODO: do we need to return result of preparedStatement?
    	Connection con = DriverManager.getConnection(url, user, password);
    	
    	if(!preparedStrings.isEmpty()) {
    		PreparedStatement preparedStatement = con.prepareStatement(sql);
    		for(int i = 0; i < preparedStrings.size(); i++) {
    			preparedStatement.setString(i+1, preparedStrings.get(i));
    		}
    		preparedStatement.executeUpdate();
    	}
    }
    
    public boolean putMedia(Media m) {
    	boolean success = false;
		Media.Type type = m.getType();
		switch(type) {
		case Book:
			success = putBook((Book) m);
			break;
		}
		return success;
	}
    
    private boolean putBook(Book b) {
    	boolean status = false;
    	String statement = "INSERT INTO plato.books " +
    			"(title, author, publisher) VALUES ('?', '?', '?')";
    	// TODO: finish this method
    	//PreparedStatement statement = Connection.
    	
//    	try {
//			queryDatabase(url, user, password, query);
//		} catch (SQLException e) {
//			
//		}
    	return status;
    }

//	@Override
//	public void visit(Book b) {
//		queryDatabase()
//	}
}