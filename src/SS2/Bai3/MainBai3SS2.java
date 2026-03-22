package SS2.Bai3;

public class MainBai3SS2    {
    @FunctionalInterface
    interface Authenticatable {

        // 1. Phương thức trừu tượng
        String getPassword();

        // 2. Phương thức mặc định (Default method)
        default boolean isAuthenticated() {
            String password = getPassword();
            return password != null && !password.trim().isEmpty();
        }

        // 3. Phương thức tĩnh (Static method)
        static String encrypt(String rawPassword) {
            return "ENCRYPTED_" + rawPassword + "_SAFE"; // Mô phỏng mã hóa
        }
    }

    static void main(String[] args) {
    }
}
