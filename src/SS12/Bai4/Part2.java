package SS12.Bai4;


    import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Part2 {

        public void insertTestResults(List<TestResult> list) {
            // 1. Khởi tạo SQL template bên ngoài vòng lặp
            String sql = "INSERT INTO Results(data) VALUES(?)";

            // 2. Sử dụng try-with-resources để quản lý tài nguyên
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) { // BIÊN DỊCH 1 LẦN DUY NHẤT

                for (TestResult tr : list) {
                    // 3. Chỉ nạp dữ liệu (Parameter) bên trong vòng lặp
                    pstmt.setString(1, tr.getData());

                    // 4. Thực thi (Lúc này DB chỉ việc lấy data nạp vào Plan đã có sẵn)
                    pstmt.executeUpdate();
                }

                System.out.println("Đã nạp xong " + list.size() + " kết quả xét nghiệm.");

            } catch (SQLException e) {
                System.err.println("Lỗi hệ thống khi nạp dữ liệu: " + e.getMessage());
            }
        }
    }
}
