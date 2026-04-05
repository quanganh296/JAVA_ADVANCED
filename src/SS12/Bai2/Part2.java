package SS12.Bai2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Part2{

    public void updatePatientVitals(int patientId, double temp, int heartRate) {
        // 1. Định nghĩa SQL với các placeholder
        String sql = "UPDATE Vitals SET temperature = ?, heart_rate = ? WHERE p_id = ?";

        // 2. Sử dụng try-with-resources để tự động giải phóng tài nguyên
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // 3. Set các giá trị theo đúng kiểu dữ liệu
            // JDBC Driver sẽ lo liệu việc định dạng (formatting) cho bạn
            pstmt.setDouble(1, temp);       // Không lo dấu chấm/phẩy
            pstmt.setInt(2, heartRate);     // Kiểu số nguyên
            pstmt.setInt(3, patientId);     // ID bệnh nhân

            // 4. Thực thi cập nhật
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Cập nhật chỉ số sinh tồn cho bệnh nhân " + patientId + " thành công!");
            } else {
                System.out.println("Không tìm thấy bệnh nhân có ID: " + patientId);
            }

        } catch (SQLException e) {
            // Xử lý lỗi Database
            System.err.println("Lỗi khi cập nhật dữ liệu y tế: " + e.getMessage());
        }
    }
}