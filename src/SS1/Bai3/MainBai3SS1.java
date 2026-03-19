package SS1.Bai3;


class User {
    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    // Phương thức setter với "lưới lọc" dữ liệu
    public void setAge(int age) {
        if (age < 0) {
            // Chủ động ném ngoại lệ nếu dữ liệu vi phạm logic
            throw new IllegalArgumentException("Lỗi nghiệp vụ: Tuổi không thể âm (" + age + ")!");
        }
        this.age = age;
        System.out.println("=> Cập nhật tuổi thành công: " + age);
    }

    public int getAge() {
        return age;
    }
}
public class MainBai3SS1 {
    // Lớp User thực hiện quy tắc nghiệp vụ


    // Lớp kiểm thử (Main)

        public static void main(String[] args) {
            User user = new User();
            user.setName("Nguyễn Văn A");

            try {
                System.out.println("--- Thử nghiệm 1: Nhập tuổi hợp lệ ---");
                user.setAge(25);

                System.out.println("\n--- Thử nghiệm 2: Nhập tuổi âm ---");
                user.setAge(-5); // Dòng này sẽ kích hoạt lệnh throw

            } catch (IllegalArgumentException e) {
                // Bắt ngoại lệ do chính chúng ta ném ra
                System.err.println("Cảnh báo hệ thống: " + e.getMessage());
            }

            System.out.println("\nChương trình kết thúc an toàn.");
        }

}
