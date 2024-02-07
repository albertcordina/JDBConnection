package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CommitAndRollback {

    private static final String url = "jdbc:postgresql://localhost:5432/application"; // for PostgreSQL (port:5432).
    private static final String user = "postgres";
    private static final String password = "abcABC123";
    private static Connection connection;

    public static void main(String[] args) {
        try {
            // 1. Create a connection to the database
            connection = DriverManager.getConnection(url, user, password);

            // 2. Change auto commit status
            connection.setAutoCommit(false);

            // 3. Execute update query
            updateQuery();

            // 4. Commit the transaction
            connection.commit();

            // 5. Close the connection
            connection.close();
        } catch (SQLException e) {
            // Handle exception or rethrow if necessary
            e.printStackTrace();

            
            try {
                // Rollback the transaction in case of an exception
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
        }
    }

    private static void updateQuery() throws SQLException {
    	
        String sql = "INSERT INTO People VALUES (4, 'Lulu')";
        
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            // Handle exception or rethrow if necessary
            e.printStackTrace();
            throw e;
        }
    }
}


