package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Print_TableToConsole {

	// Specify the data for creating the Connection to the database SQL/PostgreSQL:
	
    //static private String url = "jdbc:mysql://localhost:3306/mydb"; // for mysql (port:3306).
    //static private String usernameJDBC = "albertcordina";
	static private String url = "jdbc:postgresql://localhost:5432/application"; // for PostgreSQL
	static private String usernameJDBC = "postgres";
	
	static private String passwordJDBC = "abcABC123";
	
	// Specify the table name you want to print
	static private String tableName = "application";

	public static void main(String[] args) {

		printTableToConsole();
	}

	
	public static void printTableToConsole() {

		// 1. Establish the connection to the database
		try (Connection connection = DriverManager.getConnection(url, usernameJDBC, passwordJDBC)) {

			// 2. Create a statement of the selected Query and execute it
			try (Statement statement = connection.createStatement();
					
				// 3. Execute the Query in this statement object and return the ResultSet object
					ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName)) {
				
	            /*
	             * 4. Print column names with proper formatting:
	             *  
	             *     getMetaData - Retrieves the number, types and properties of this ResultSet object's columns
	             *     getColumnCount - Returns the number of columns in this ResultSet object
	             *     getColumnName - Gets the designated column's name
	             */				
				int columnCount = resultSet.getMetaData().getColumnCount();
				
				for (int i = 1; i <= columnCount; i++) {
					String columnName = resultSet.getMetaData().getColumnName(i);

			     // 5. Adjust the width of the name/head of each column based on your needs
					System.out.printf("%-20s", columnName);
				}
				System.out.println(); // Move to the next line

								
				// 6. Print each row with proper formatting
				while (resultSet.next()) {
					for (int i = 1; i <= columnCount; i++) {
						String columnValue = resultSet.getString(i);

			    // 7. Adjust the width of the value of each column and each row of the table based on your needs
						System.out.printf("%-20s", columnValue);
					}
					System.out.println(); // Move to the next line
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
