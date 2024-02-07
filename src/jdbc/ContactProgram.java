package jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContactProgram {

    public static void main(String[] args) {

        //String sql = "select name from Products where id=2"; // getting a value.
       // String sql = "insert into Products (name,id) values ('Anna', 4)"; // adding a line.
      //  String sql = "delete from Products where name= 'Anna'";   // removing the line(s).
      //  String sql = "insert into public.\"Students\" (name,surname,email) values ('Lulu', 'Bolan', 'lulu@gmail.com');";
        String sql = "create table List (id SERIAL PRIMARY KEY,name VARCHAR(30) NOT NULL);";
        
        String url = "jdbc:postgresql://localhost:5432/albertcordina";
        String username = "postgres";
        String password = "abcABC123";
        try {

            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to PostgreSQL server.");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            String name = resultSet.getString(1);
            System.out.println(name);

            connection.close();

        } catch (SQLException e) {
            System.out.println("Error in connection to PostgreSQL server.");
            e.printStackTrace();
        }
    }
}
