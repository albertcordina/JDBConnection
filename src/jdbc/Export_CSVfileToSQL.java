package jdbc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/*
 * In case of INSERTing data from CSV file to the already existed table, it's also recommended to export
 *          the table from the CSV file and operate it with the already existed table in the database.
 */
public class Export_CSVfileToSQL {
	
	// Specify the data for creating the Connection to the database SQL/PostgreSQL:
	
    //static private String url = "jdbc:mysql://localhost:3306/mydb"; // for mysql (port:3306).
    //static private String usernameJDBC = "albertcordina";
	static private String url = "jdbc:postgresql://localhost:5432/application"; // for PostgreSQL
	static private String usernameJDBC = "postgres";
	
	static private String passwordJDBC = "abcABC123";

	/*
	 *  Specify the table name that has to be exported from the file, 
	 *   and the commands to 'CREATE' and 'INSERT INTO' the table
	 */
	
	static private String tableName = "nameOfTheTableFromCSVfile";	
	static private String createTable = 
		    "CREATE TABLE IF NOT EXISTS " + tableName + " "
		    + "(phone_number INT, name VARCHAR(30), surname VARCHAR(30), "
		    + "age INT, occupation VARCHAR(255), overallincome DOUBLE PRECISION, password VARCHAR(30), statusofapplication TEXT)";
	
    static private String insertValuesIntoTable = "INSERT INTO " + tableName + 
    		" (phone_number, name, surname, age, occupation, overallincome, password, statusofapplication) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	// Specify the name of the file
	static private String csvFilePath = "List of the Applicants.csv";

	
    public static void main(String[] args) {

        // Establish database connection
        try (Connection connection = DriverManager.getConnection(url, usernameJDBC, passwordJDBC)) {
        	
        	// The method to create the table
            createTable(connection);

            // The method to read CSV file and insert data into the database/table
            importCSV(connection);

            System.out.println("Data has been successfully imported from CSV to SQL table.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    // The method to create the table
    private static void createTable (Connection connection) throws SQLException {	
        try (PreparedStatement statement = connection.prepareStatement(createTable)) {
            statement.executeUpdate();
        }
    }

 // The method to read CSV file and insert data into the database
    private static void importCSV(Connection connection) throws SQLException {

    	/*
    	 * BufferedReader - Reads text from a character-input stream, buffering characters so as 
    	 *                  to provide for the efficient reading of characters, arrays, and lines.
    	 * FileReader -  Creates a new FileReader, given the name of the file to read.              
    	 */
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath));
             PreparedStatement statement = connection.prepareStatement(insertValuesIntoTable)) {

            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue; // Skip the first line (headers)
                }
                /*
                 * Method 'split' works as if by invoking the two-argument split method 
                 *   with the given expression and a limit argument of zero. 
                 */
                String[] data = line.split(",");

                // Sets the designated parameter to the given Java types of values
                statement.setInt(1, Integer.parseInt(data[0])); // phone_number (assuming it's an INT)
                statement.setString(2, data[1]); // name
                statement.setString(3, data[2]); // surname
                statement.setInt(4, Integer.parseInt(data[3])); // age
                statement.setString(5, data[4]); // occupation
                statement.setDouble(6, Double.parseDouble(data[5])); // overallincome
                statement.setString(7, data[6]); // password
                statement.setString(8, data[7]); // statusofapplication

                statement.addBatch();
            }

            statement.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
	