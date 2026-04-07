package S14;

import java.sql.*;

public class SafeTransferApp {
    // Thông tin kết nối từ yêu cầu của bạn
    private static final String URL = "jdbc:mysql://localhost:3306/JavaAdvanced_DB";
    private static final String USER = "root";
    private static final String PASS = "QAyeuHutaodenchet02092006";

    public static void main(String[] args) {
        String senderId = "ACC01";
        String receiverId = "ACC02";
        double transferAmount = 1000.0;

        transferMoney(senderId, receiverId, transferAmount);
    }

    public static void transferMoney(String fromId, String toId, double amount) {
        // Sử dụng try-with-resources để tự động đóng kết nối
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

            // 1. Cấu hình Transaction: Tắt chế độ Auto-Commit [cite: 34, 35, 44]
            conn.setAutoCommit(false);

            try {
                // 2. Kiểm tra tài khoản và số dư bằng PreparedStatement
                String checkSql = "SELECT Balance FROM Accounts WHERE AccountId = ?";
                double senderBalance = 0;
                boolean senderExists = false;

                try (PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
                    pstmt.setString(1, fromId);
                    ResultSet rs = pstmt.executeQuery();
                    if (rs.next()) {
                        senderExists = true;
                        senderBalance = rs.getDouble("Balance");
                    }
                }

                if (!senderExists || senderBalance < amount) {
                    throw new SQLException("Tài khoản không tồn tại hoặc không đủ số dư!");
                }

                // 3. Thực thi nghiệp vụ bằng CallableStatement gọi Stored Procedure
                String procedureSql = "{call sp_UpdateBalance(?, ?)}";
                try (CallableStatement cstmt = conn.prepareCall(procedureSql)) {
                    // Lần 1: Trừ tiền người gửi (amount âm)
                    cstmt.setString(1, fromId);
                    cstmt.setDouble(2, -amount);
                    cstmt.execute();

                    // Lần 2: Cộng tiền người nhận (amount dương)
                    cstmt.setString(1, toId);
                    cstmt.setDouble(2, amount);
                    cstmt.execute();
                }

                // 4. Xác nhận giao dịch thành công
                conn.commit();
                System.out.println("Giao dịch chuyển khoản thành công!");

                // 5. Hiển thị kết quả cuối cùng bằng PreparedStatement
                displayResults(conn, fromId, toId);

            } catch (SQLException e) {
                // 6. Rollback nếu có bất kỳ lỗi nào xảy ra
                conn.rollback();
                System.err.println("Giao dịch thất bại. Đã Rollback. Lỗi: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayResults(Connection conn, String id1, String id2) throws SQLException {
        String query = "SELECT * FROM Accounts WHERE AccountId IN (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id1);
            pstmt.setString(2, id2);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("\n--- BẢNG ĐỐI SOÁT KẾT QUẢ ---");
            System.out.printf("%-10s | %-15s | %-10s%n", "ID", "Họ Tên", "Số Dư");
            System.out.println("------------------------------------------");
            while (rs.next()) {
                System.out.printf("%-10s | %-15s | %-10.2f%n",
                        rs.getString("AccountId"),
                        rs.getString("FullName"),
                        rs.getDouble("Balance"));
            }
        }
    }
}