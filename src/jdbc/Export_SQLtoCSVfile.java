package jdbc;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Export_SQLtoCSVfile {
	
	// Specify the data for creating the Connection to the database SQL/PostgreSQL:
	
    //static private String url = "jdbc:mysql://localhost:3306/mydb"; // for mysql (port:3306).
    //static private String usernameJDBC = "albertcordina";
	static private String url = "jdbc:postgresql://localhost:5432/application"; // for PostgreSQL
	static private String usernameJDBC = "postgres";
	
	static private String passwordJDBC = "abcABC123";
	
	// Specify the table name you want to export to the file
	static private String tableName = "application";
	
	// Specify the name of the file
	static private String outputFile = "List of the Applicants.csv";

	
    public static void main(String[] args) {
        
        exportTableToCSVfile(); // call the method
    }
    
    // The method that exports a table from database into the CSV file
    public static void exportTableToCSVfile () {
        
    	try {
            // 1. Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. Establish the connection to the database
            try (Connection connection = DriverManager.getConnection(url, usernameJDBC, passwordJDBC)) {

                // 3. Create PreperedStatement of the Query which selects all data from the table
                try (PreparedStatement preparedStatement = connection.prepareStatement ("SELECT * FROM " + tableName);
                		
                	// 4. Execute the Query in this PreparedStatement object and return the ResultSet object	
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    // 5. Write the data to a CSV file (call the 'helper method' below)
                    writeResultSetToCSV(resultSet, outputFile);
                    System.out.println("Data exported successfully to " + outputFile);
                }
            }
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    
    // The 'helper method' that writes the result (ResultSet object(see the method above)) to a CSV file
    private static void writeResultSetToCSV (ResultSet resultSet, String outputFile) throws SQLException, IOException {
    	
    	/*
    	 *  FileWriter- Writes text to character files using a default buffer size. 
    	 *       Encoding from characters to bytes uses either a specified charset.
    	 */
        try (FileWriter writer = new FileWriter(outputFile)) {

            /*
             *  Write column headers (calling the methods below for the object 'resultSet'):
             *  
             *    getMetaData - Retrieves the number, types and properties of this ResultSet object's columns
             *    getColumnCount - Returns the number of columns in this ResultSet object
             *    getColumnName - Gets the designated column's name
             */
            int columnCount = resultSet.getMetaData().getColumnCount();// gets the number of the columns
            
            for (int i = 1; i <= columnCount; i++) {
                writer.append(resultSet.getMetaData().getColumnName(i)); // gets the name(s) of the columns
                if (i < columnCount) {
                    writer.append(","); // method 'append' specifies character sequence to this writer
                }
            }
            writer.append("\n"); // specifies the position of the rows (i.e. underneath each other)

            /*
             *  Write data into the file via the getString method:
             *    
             *    getString - Retrieves the value of the designated column in the current row of this ResultSet object as a String
             */
            
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    writer.append(resultSet.getString(i));
                    if (i < columnCount) {
                        writer.append(","); // method 'append' specifies character sequence to this writer
                    }
                }
                writer.append("\n"); // specifies the position of the rows (i.e. underneath each other)
            }
        }
    }
}

