package SS1.Bai2;

import java.util.Scanner;
public class MainBai2SS1 {
        public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);

            try {
                // Bước 1: Nhập dữ liệu
                System.out.print("Nhập tổng số người dùng: ");
                int tongNguoi = Integer.parseInt(scanner.nextLine());

                System.out.print("Nhập số lượng nhóm muốn chia: ");
                int soNhom = Integer.parseInt(scanner.nextLine());

                // Bước 2: Vùng nguy hiểm - Phép toán chia
                // Nếu soNhom = 0, JVM sẽ ném ra một ArithmeticException tại đây
                int soNguoiMoiNhom = tongNguoi / soNhom;

                System.out.println("=> Kết quả: Mỗi nhóm có " + soNguoiMoiNhom + " người.");

            } catch (NumberFormatException e) {
                // Xử lý khi người dùng nhập chữ thay vì số
                System.out.println("Lỗi: Vui lòng nhập định dạng số nguyên hợp lệ!");

            } catch (ArithmeticException e) {
                // Xử lý lỗi logic toán học (chia cho 0)
                System.out.println("Lỗi: Không thể chia cho 0! Vui lòng nhập số nhóm lớn hơn 0.");

            } finally {
                // Luôn dọn dẹp tài nguyên
                System.out.println("Thực hiện dọn dẹp tài nguyên trong finally...");
                scanner.close();
            }

            // Kiểm chứng: Chương trình vẫn tiếp tục chạy luồng phía sau
            System.out.println("--- Hệ thống vẫn đang vận hành bình thường ---");
        }
}
