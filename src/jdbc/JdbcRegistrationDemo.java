package jdbc;

public class JdbcRegistrationDemo {

	public static void main(String[] args) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver class loaded and registered");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
