import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Driver {
    public static void main(String[] args) throws SQLException {

        Connection connection = null;

        try {

            String url = "jdbc:mysql://localhost:3306/newdb";
            String user = "developer";
            String password = "developer";

            connection = DriverManager.getConnection(url, user, password);

            Statement statement = connection.createStatement();

            statement.executeUpdate("CREATE VIEW italianView AS SELECT first_name, last_name from students where country = 'italia';");
            statement.executeUpdate("CREATE VIEW germanView AS SELECT first_name, last_name from students where country = 'germania';");


            ResultSet italianView = statement.executeQuery("SELECT first_name, last_name from italianView;");

            System.out.println("_________ Name and Surname Italian Student _________");


            List<Student> italianStudents = new ArrayList<>();
            List<Student> germanStudents = new ArrayList<>();

            while(italianView.next()){
                System.out.println(italianView.getString(1) + " " + italianView.getString(2));
                italianStudents.add(new Student(italianView.getString(1),italianView.getString(2)));
            }

            ResultSet germanView = statement.executeQuery("SELECT first_name, last_name from germanView;");

            System.out.println("\n_________ Name and Surname German Student _________");

            while(germanView.next()){
                System.out.println(germanView.getString(1) + " " + germanView.getString(2));
                germanStudents.add(new Student(germanView.getString(1),germanView.getString(2)));
            }

            System.out.println("\n_________ ITALIAN STUDENT _________");

            italianStudents.stream().forEach(student -> System.out.println(student.getName() + " " + student.getSurname()));

            System.out.println("\n_________ GERMAN STUDENT _________");
            germanStudents.stream().forEach(student -> System.out.println(student.getName() + " " + student.getSurname()));

            statement.close();

        } catch (SQLException e){
            System.out.println(e.getMessage());
        } finally {

            try {
                if (connection != null)
                    connection.close();
            } catch(SQLException ex){
                System.out.println(ex.getMessage());
            }
        }
    }
}