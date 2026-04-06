package SS13.Bai3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Part2 {
    public class DatabaseManager {
        // Thay đổi các thông số bên dưới cho phù hợp với máy của bạn
        private static final String URL = "jdbc:sqlserver://localhost:3306;databaseName=RikkeiHospital;encrypt=true;trustServerCertificate=true;";
        private static final String USERNAME = "sa";
        private static final String PASSWORD = "QAyeuHutaodenchet02092006";

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
    }
}
