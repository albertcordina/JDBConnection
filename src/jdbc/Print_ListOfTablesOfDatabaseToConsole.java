package jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
// gets the list of the tables the database has
public class Print_ListOfTablesOfDatabaseToConsole {

	// JDBC URL, username, and password of PostgreSQL server
	private static final String url = "jdbc:postgresql://localhost:5432/application"; // for postgresql (port:5432).
	private static final String user = "postgres";
	private static final String password = "abcABC123";

	public static void main(String[] args) {

		try {
			// 1. Create a connection to the database
			Connection connection = DriverManager.getConnection(url, user, password);

			// 2. Get DatabaseMetaData from the Connection object
			DatabaseMetaData metaData = connection.getMetaData();

			/*
			 *  3. Get the list of tables via method 'getTables' 
			 *       and store it in the resultSet object
			 */
			String[] types = { "TABLE" };
			ResultSet resultSet = metaData.getTables(null, null, "%", types);

			// 4. Display the gathered values as the list of Tables
			System.out.println("List of Tables:");
			while (resultSet.next()) {
				String tableName = resultSet.getString("TABLE_NAME");
				System.out.println(tableName);
			}

			// Close the ResultSet, Statement, and Connection
			resultSet.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
