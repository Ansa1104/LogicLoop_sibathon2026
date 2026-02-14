

import java.sql.Connection;
import java.sql.DriverManager;

 class SQLConnection {

    private Connection connection;

    public SQLConnection() {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/student_management_database",
                    "root",
                    "1234567890"
            );

            System.out.println("Database Connected Successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
