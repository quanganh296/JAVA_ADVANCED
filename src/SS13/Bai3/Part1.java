package SS13.Bai3;

import java.sql.*;

     public class Part1 {

        public void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
            Connection conn = null;
            PreparedStatement psSelect = null;
            PreparedStatement psUpdateWallet = null;
            PreparedStatement psUpdateBed = null;
            PreparedStatement psUpdatePatient = null;

            try {
                conn = Part2.DatabaseManager.getConnection();
                // Bắt đầu Transaction
                conn.setAutoCommit(false);

                // --- BẪY 1: KIỂM TRA SỐ DƯ (LOGIC NGHIỆP VỤ) ---
                String sqlCheckBalance = "SELECT balance FROM Patient_Wallet WHERE patient_id = ?";
                psSelect = conn.prepareStatement(sqlCheckBalance);
                psSelect.setInt(1, maBenhNhan);
                ResultSet rs = psSelect.executeQuery();

                if (rs.next()) {
                    double currentBalance = rs.getDouble("balance");
                    if (currentBalance < tienVienPhi) {
                        // Chủ động ném lỗi nếu không đủ tiền
                        throw new SQLException("Lỗi Bẫy 1: Số dư tạm ứng không đủ để thanh toán viện phí.");
                    }
                } else {
                    throw new SQLException("Lỗi: Không tìm thấy thông tin ví của bệnh nhân ID: " + maBenhNhan);
                }

                // --- THAO TÁC 1: TRỪ TIỀN VIỆN PHÍ ---
                String sql1 = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
                psUpdateWallet = conn.prepareStatement(sql1);
                psUpdateWallet.setDouble(1, tienVienPhi);
                psUpdateWallet.setInt(2, maBenhNhan);
                int rows1 = psUpdateWallet.executeUpdate();
                // BẪY 2: Kiểm tra dữ liệu ảo
                if (rows1 == 0) throw new SQLException("Lỗi Bẫy 2: Cập nhật ví thất bại (ID bệnh nhân sai).");

                // --- THAO TÁC 2: GIẢI PHÓNG GIƯỜNG BỆNH ---
                // Giả sử bảng Patients có lưu bed_id để tìm giường tương ứng
                String sql2 = "UPDATE Beds SET status = 'EMPTY' WHERE bed_id = (SELECT bed_id FROM Patients WHERE patient_id = ?)";
                psUpdateBed = conn.prepareStatement(sql2);
                psUpdateBed.setInt(1, maBenhNhan);
                int rows2 = psUpdateBed.executeUpdate();
                // BẪY 2: Kiểm tra dữ liệu ảo
                if (rows2 == 0) throw new SQLException("Lỗi Bẫy 2: Không thể giải phóng giường (Bệnh nhân chưa được gán giường).");

                // --- THAO TÁC 3: CẬP NHẬT TRẠNG THÁI XUẤT VIỆN ---
                String sql3 = "UPDATE Patients SET status = 'DISCHARGED' WHERE patient_id = ?";
                psUpdatePatient = conn.prepareStatement(sql3);
                psUpdatePatient.setInt(1, maBenhNhan);
                int rows3 = psUpdatePatient.executeUpdate();
                // BẪY 2: Kiểm tra dữ liệu ảo
                if (rows3 == 0) throw new SQLException("Lỗi Bẫy 2: Không thể cập nhật trạng thái xuất viện.");

                // CHỐT DỮ LIỆU: Nếu chạy đến đây tức là mọi bước trên đều thành công
                conn.commit();
                System.out.println("Chúc mừng! Bệnh nhân " + maBenhNhan + " đã hoàn tất thủ tục xuất viện an toàn.");

            } catch (SQLException e) {
                // ROLLBACK: Hoàn tác toàn bộ nếu có bất kỳ lỗi nào (kể cả lỗi logic bẫy 1, 2)
                System.err.println("GIAO DỊCH THẤT BẠI: " + e.getMessage());
                try {
                    if (conn != null) {
                        conn.rollback();
                        System.err.println("Hệ thống đã thực hiện Rollback. Không có dữ liệu nào bị thay đổi.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            } finally {
                // Đóng kết nối để tránh rò rỉ tài nguyên
                try {
                    if (psSelect != null) psSelect.close();
                    if (psUpdateWallet != null) psUpdateWallet.close();
                    if (psUpdateBed != null) psUpdateBed.close();
                    if (psUpdatePatient != null) psUpdatePatient.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//Sử dụng đối tượng Connection trong JDBC để kiểm soát luồng dữ liệu.
// Ý tưởng chủ đạo là chuyển setAutoCommit(false) ngay từ đầu để mở một "phiên làm việc tạm thời".
// Xử lý Bẫy 1: Thực hiện một câu lệnh SELECT để kiểm tra điều kiện $balance < tienVienPhi$ trước khi thực hiện bất kỳ lệnh UPDATE nào.
// Xử lý Bẫy 2: Sau mỗi lệnh executeUpdate(), ta kiểm tra giá trị trả về.
// Nếu bằng 0, ta chủ động ném (throw) một SQLException để kích hoạt khối catch thực hiện rollback().
