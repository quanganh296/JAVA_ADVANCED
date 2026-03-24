package SS4.Bai3;

public class UserProcessor {

    public String processEmail(String email) {
        // 1. Kiểm tra email có null hoặc rỗng không
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email không được để trống");
        }

        // 2. Kiểm tra định dạng (phải có '@' và có ký tự sau '@')
        int atIndex = email.indexOf('@');
        if (atIndex == -1 || atIndex == email.length() - 1) {
            throw new IllegalArgumentException("Định dạng email không hợp lệ");
        }

        // 3. Chuẩn hóa về chữ thường (lowercase)
        return email.toLowerCase();
    }
}