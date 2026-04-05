package SS12.Bai1;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Part2 {


        public boolean loginDoctor(String doctorCode, String password) {
            // 1. Câu lệnh SQL sử dụng dấu hỏi chấm (?) làm placeholder
            String sql = "SELECT * FROM doctors WHERE doctor_code = ? AND password = ?";

            // 2. Sử dụng try-with-resources để tự động đóng Connection, PreparedStatement và ResultSet
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // 3. Truyền tham số vào các dấu hỏi chấm (Index bắt đầu từ 1)
                // Mọi ký tự đặc biệt trong doctorCode hoặc password đều bị vô hiệu hóa tại đây
                pstmt.setString(1, doctorCode);
                pstmt.setString(2, password);

                // 4. Thực thi truy vấn
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("Đăng nhập thành công! Chào bác sĩ: " + rs.getString("full_name"));
                        return true;
                    } else {
                        System.out.println("Sai mã bác sĩ hoặc mật khẩu.");
                    }
                }

            } catch (SQLException e) {
                System.err.println("Lỗi kết nối cơ sở dữ liệu: " + e.getMessage());
            }

            return false;
        }
    }

