package SS13.Bai1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Part2 {
    public void capPhatThuoc(int medicineId, int patientId) {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try {
            conn = DatabaseManager.getConnection();

            // Bước 1: Tắt chế độ Auto-Commit để bắt đầu Transaction thủ công
            conn.setAutoCommit(false);

            // Thao tác 1: Trừ số lượng thuốc trong kho
            String sqlUpdateInventory = "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE medicine_id = ?";
            ps1 = conn.prepareStatement(sqlUpdateInventory);
            ps1.setInt(1, medicineId);
            ps1.executeUpdate();

            // Giả lập sự cố mạng hoặc lỗi runtime
            int x = 10 / 0;

            // Thao tác 2: Ghi vào lịch sử bệnh án
            String sqlInsertHistory = "INSERT INTO Prescription_History (patient_id, medicine_id, date) VALUES (?, ?, GETDATE())";
            ps2 = conn.prepareStatement(sqlInsertHistory);
            ps2.setInt(1, patientId);
            ps2.setInt(2, medicineId);
            ps2.executeUpdate();

            // Bước 2: Nếu mọi thứ suôn sẻ, chốt dữ liệu (Commit)
            conn.commit();
            System.out.println("Cấp phát thuốc thành công và đã lưu lịch sử!");

        } catch (Exception e) {
            // Bước 3: Nếu có bất kỳ lỗi nào, hoàn tác mọi thay đổi (Rollback)
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Đã xảy ra lỗi. Hệ thống đã hoàn tác (Rollback) dữ liệu để đảm bảo an toàn.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Chi tiết lỗi: " + e.getMessage());
        } finally {
            // Bước 4: Đóng tài nguyên và trả lại trạng thái Auto-Commit (nếu cần)
            try {
                if (ps1 != null) ps1.close();
                if (ps2 != null) ps2.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Trả lại trạng thái mặc định cho Connection pool
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
