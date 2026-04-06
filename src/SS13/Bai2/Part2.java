package SS13.Bai2;

import java.sql.SQLException;

public class Part2 {
    public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
        // Sử dụng Try-with-resources để tự động đóng kết nối
        try (Connection conn = DatabaseManager.getConnection()) {

            // 1. Tắt tự động lưu giao dịch
            conn.setAutoCommit(false);

            try {
                // Thao tác 1: Trừ tiền trong ví
                String sqlDeductWallet = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
                PreparedStatement ps1 = conn.prepareStatement(sqlDeductWallet);
                ps1.setDouble(1, amount);
                ps1.setInt(2, patientId);
                ps1.executeUpdate();

                // Thao tác 2: Cập nhật trạng thái hóa đơn (Cố tình viết sai tên bảng)
                String sqlUpdateInvoice = "UPDATE Invoicesss SET status = 'PAID' WHERE invoice_id = ?";
                PreparedStatement ps2 = conn.prepareStatement(sqlUpdateInvoice);
                ps2.setInt(1, invoiceId);
                ps2.executeUpdate();

                // 2. Xác nhận giao dịch nếu mọi thứ thành công
                conn.commit();
                System.out.println("Thanh toán hoàn tất!");

            } catch (SQLException e) {
                // 3. HÀNH ĐỘNG QUAN TRỌNG: Hoàn tác dữ liệu khi có lỗi xảy ra
                System.out.println("Lỗi hệ thống: Đang tiến hành hoàn tác dữ liệu...");
                try {
                    if (conn != null) {
                        conn.rollback();
                        System.out.println("Đã Rollback thành công. Tiền của bệnh nhân đã được bảo toàn.");
                    }
                } catch (SQLException rollbackEx) {
                    System.out.println("Lỗi nghiêm trọng: Không thể rollback! " + rollbackEx.getMessage());
                }
                // In ra lỗi ban đầu để debug
                System.out.println("Chi tiết lỗi thanh toán: " + e.getMessage());
            }

        } catch (SQLException e) {
            // Lỗi kết nối Database ban đầu
            e.printStackTrace();
        }
    }
}
