package pl.coderslab.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateTables {
    private static  final String DB_URL = "jdbc:mysql://localhost:3306/workshop_2?useSSL=false&characterEncoding=utf8";
    private static  final String DB_USER= "root";
    private static  final String DB_PASS= "coderslab";



        public static String queryCreateTableUsers = "CREATE TABLE IF NOT EXISTS users (id BIGINT(20) AUTO_INCREMENT, " +
                " username VARCHAR(255), " +
                " email VARCHAR(255), " +
                " password VARCHAR(255), " +
                "user_group_id INT(11) NOT NULL, " +
                " PRIMARY KEY (id)," +
                "FOREIGN KEY (user_group_id) REFERENCES user_group(id))";

        //Zapisz w poniższej zmiennej kod zapytania SQL tworzącego drugą tabelę
        public static String queryCreateTableUserGroup = "CREATE TABLE IF NOT EXISTS user_group (id INT(11) AUTO_INCREMENT, " +
                "name VARCHAR(255)," +
                "PRIMARY KEY (id))";

        //Zapisz w poniższej zmiennej kod zapytania SQL tworzącego trzecią tabelę
        public static String queryCreateTableExercise = "CREATE TABLE IF NOT EXISTS exercise (" +
                "id INT AUTO_INCREMENT, " +
                "title VARCHAR(255), " +
                "description TEXT," +
                "PRIMARY KEY (id))";


        //Zapisz w poniższej zmiennej kod zapytania SQL tworzącego czwartą tabelę
        public static String queryCreateTableSolution = "CREATE TABLE IF NOT EXISTS solution (" +
                "id INT AUTO_INCREMENT, " +
                "created DATETIME NULL ON UPDATE CURRENT_TIMESTAMP, " +
                "updated DATETIME NULL ON UPDATE CURRENT_TIMESTAMP, " +
                "description TEXT," +
                "exercise_id INT(11) NOT NULL, " +
                "users_id BIGINT(20) NOT NULL, " +
                "PRIMARY KEY (id)," +
                "FOREIGN KEY (exercise_id) REFERENCES exercise(id), " +
                "FOREIGN KEY (users_id) REFERENCES users(id))";

        public static void main(String[] args) {
            try (Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASS)) {
                PreparedStatement stmt = connection.prepareStatement(queryCreateTableExercise);
                stmt.executeUpdate();
                stmt = connection.prepareStatement(queryCreateTableUserGroup);
                stmt.executeUpdate();
                stmt = connection.prepareStatement(queryCreateTableUsers);
                stmt.executeUpdate();
                stmt = connection.prepareStatement(queryCreateTableSolution);
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }

}
