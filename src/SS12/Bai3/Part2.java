package SS12.Bai3;


    import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.math.BigDecimal;

public class Part2 {

        public void printSurgeryFee(int surgeryId) {
            // Cấu trúc gọi procedure: {call Tên_Procedure(?, ?, ...)}
            String sql = "{call GET_SURGERY_FEE(?, ?)}";

            try (Connection conn = DatabaseConnection.getConnection();
                 CallableStatement cstmt = conn.prepareCall(sql)) {

                // 1. Truyền tham số đầu vào (IN parameter)
                cstmt.setInt(1, surgeryId);

                // 2. ĐĂNG KÝ tham số đầu ra (OUT parameter) - Bước quan trọng nhất
                // Chúng ta dùng Types.DECIMAL cho các giá trị liên quan đến tiền tệ
                cstmt.registerOutParameter(2, Types.DECIMAL);

                // 3. Thực thi Procedure
                cstmt.execute();

                // 4. Lấy kết quả từ tham số OUT (sử dụng index 2)
                // Lưu ý: Dùng getBigDecimal để đảm bảo độ chính xác cho tiền bạc
                BigDecimal totalCost = cstmt.getBigDecimal(2);

                if (totalCost != null) {
                    System.out.println("---------------------------------------");
                    System.out.println("Mã ca phẫu thuật: " + surgeryId);
                    System.out.println("Tổng chi phí cần thanh toán: " + totalCost + " VNĐ");
                    System.out.println("---------------------------------------");
                } else {
                    System.out.println("Không tìm thấy thông tin chi phí cho ca phẫu thuật này.");
                }

            } catch (SQLException e) {
                System.err.println("Lỗi khi gọi Stored Procedure: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
