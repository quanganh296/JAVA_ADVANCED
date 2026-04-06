package SS13.bai4;

import java.sql.*;
import java.util.*;

public class Part1 {

    public List<BenhNhanDTO> getDashboardCapCuu() {
        // Sử dụng LinkedHashMap để giữ đúng thứ tự bệnh nhân từ Database trả về
        Map<Integer, BenhNhanDTO> patientMap = new LinkedHashMap<>();

        // SQL sử dụng LEFT JOIN để xử lý BẪY 2 (BN chưa có dịch vụ vẫn hiện tên)
        String sql = "SELECT p.id, p.ho_ten, p.so_giuong, " +
                "s.id AS dich_vu_id, s.ten_dich_vu, s.gio_dung " +
                "FROM BenhNhan p " +
                "LEFT JOIN DichVuSuDung s ON p.id = s.ma_benh_nhan " +
                "WHERE p.trang_thai = 'DANG_DIEU_TRI' " +
                "ORDER BY p.id ASC"; // Order by rất quan trọng để gom nhóm dữ liệu

        /* * LƯU Ý: Chỗ "DatabaseManager.getConnection()" bên dưới,
         * bạn hãy thay bằng class kết nối DB thực tế trong project của bạn.
         * (Ví dụ theo code cũ của bạn là SS13.Bai3.DatabaseManager.getConnection())
         */
        try (Connection conn = SS13.Bai3.DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int pId = rs.getInt("id");

                // Bước 1: Kiểm tra xem bệnh nhân đã tồn tại trong Map chưa
                BenhNhanDTO patient = patientMap.get(pId);
                if (patient == null) {
                    patient = new BenhNhanDTO();
                    patient.setId(pId);
                    patient.setHoTen(rs.getString("ho_ten"));
                    patient.setSoGiuong(rs.getString("so_giuong"));
                    patient.setDsDichVu(new ArrayList<>()); // Khởi tạo mảng rỗng

                    patientMap.put(pId, patient); // Lưu vào map
                }

                // Bước 2: XỬ LÝ BẪY 2 - Mất mát dữ liệu do JOIN
                // Nếu BN vừa nhập viện, chưa có dịch vụ -> dich_vu_id sẽ là NULL
                int dvId = rs.getInt("dich_vu_id");

                if (!rs.wasNull()) { // wasNull() = false tức là có dữ liệu thật
                    DichVuDTO dv = new DichVuDTO();
                    dv.setId(dvId);
                    dv.setTenDichVu(rs.getString("ten_dich_vu"));
                    dv.setGioDung(rs.getTimestamp("gio_dung"));

                    // Thêm dịch vụ vào danh sách của bệnh nhân
                    patient.getDsDichVu().add(dv);
                }
                // Nếu rs.wasNull() là TRUE -> Không add gì cả, danh sách dịch vụ giữ nguyên trạng thái rỗng
            }

        } catch (SQLException e) {
            System.out.println("Lỗi truy xuất Dashboard Y tá: " + e.getMessage());
            e.printStackTrace();
        }

        // Chuyển kết quả từ dạng Map sang List để trả về
        return new ArrayList<>(patientMap.values());
    }
}