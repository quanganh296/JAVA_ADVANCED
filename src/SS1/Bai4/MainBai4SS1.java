package SS1.Bai4;


import java.io.IOException;
public class MainBai4SS1 {
    public static void main(String[] args) {
        System.out.println("Hệ thống: Bắt đầu tiến trình lưu dữ liệu...");

        try {
            // Gọi hàm B
            processUserData();
            System.out.println("Hệ thống: Lưu dữ liệu thành công!");
        } catch (IOException e) {
            // Xử lý dứt điểm lỗi tại đây
            System.err.println("=> [Hàm Main] Đã bắt được lỗi: " + e.getMessage());
            System.err.println("=> Giải pháp: Yêu cầu người dùng kiểm tra lại kết nối ổ cứng.");
        }

        System.out.println("Hệ thống: Kết thúc chương trình an toàn.");
    }

    // --- METHOD B: Xử lý nghiệp vụ (Chuyển giao trách nhiệm) ---
    // Hàm này gọi C, nhưng không dùng try-catch mà dùng 'throws' để đẩy lỗi lên A
    public static void processUserData() throws IOException {
        System.out.println("[Hàm B] Đang chuẩn bị dữ liệu để ghi vào file...");
        saveToFile();
        System.out.println("[Hàm B] Hoàn tất xử lý."); // Dòng này sẽ không chạy nếu có lỗi
    }

    // --- METHOD C: Thao tác hệ thống (Nơi phát sinh lỗi) ---
    // Khai báo 'throws' vì IOException là một Checked Exception (bắt buộc phải xử lý hoặc khai báo)
    public static void saveToFile() throws IOException {
        System.out.println("[Hàm C] Đang kết nối với ổ đĩa cứng...");

        // Giả lập một lỗi nhập xuất dữ liệu (ví dụ: ổ đĩa bị rút ra đột ngột)
        boolean isDriveReady = false;
        if (!isDriveReady) {
            throw new IOException("Ổ đĩa không phản hồi (Disk Not Ready).");
        }
    }
}
