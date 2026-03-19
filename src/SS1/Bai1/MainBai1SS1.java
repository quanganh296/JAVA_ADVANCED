package SS1.Bai1;

import java.util.Scanner;
import java.time.Year;

public class MainBai1SS1 {
    public static void main(String[] args) {
        // Khởi tạo Scanner để nhận dữ liệu từ bàn phím
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Vui lòng nhập năm sinh của bạn: ");
            String input = scanner.nextLine();

            // Cố gắng chuyển đổi chuỗi sang số nguyên
            int namSinh = Integer.parseInt(input);

            // Tính tuổi (Lấy năm hiện tại - năm sinh)
            int namHienTai = Year.now().getValue();
            int tuoi = namHienTai - namSinh;

            if (tuoi < 0) {
                System.out.println("Năm sinh không thể lớn hơn năm hiện tại!");
            } else {
                System.out.println("Tuổi của bạn là: " + tuoi);
            }

        } catch (NumberFormatException e) {
            // Bắt lỗi nếu người dùng nhập chữ thay vì số
            System.out.println("=> Lỗi: Định dạng không hợp lệ! Vui lòng chỉ nhập số nguyên (Ví dụ: 1995).");

        } finally {
            // Luôn thực thi dù có lỗi hay không
            System.out.println("Thực hiện dọn dẹp tài nguyên trong finally...");
            scanner.close();
        }
    }
}
