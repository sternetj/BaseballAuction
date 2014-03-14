import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class Db {
public static String query(){
	try {
		Class.forName("com.mysql.jdbc.Driver") ;
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Connection conn = null;
	try {
		conn = DriverManager.getConnection("jdbc:mysql://169.254.85.226:81/Baseball", "root", "root");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Statement stmt = null;
	try {
		stmt = conn.createStatement();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	String query = "select * from bTeam ;" ;
	ResultSet rs = null;
	try {
		rs = stmt.executeQuery(query);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return rs.toString();
}
}
