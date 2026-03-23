package SS3.Bai3;
import java.util.List;
import java.util.Optional;

public class MainBai3SS3 {

        // 1. Tái sử dụng record User
        public record User(String username, String email) {}

        // 2. Viết class UserRepository
        public static class UserRepository {
            // Danh sách người dùng (dùng non-static)
            private final List<User> users = List.of(
                    new User("alice", "alice@gmail.com"),
                    new User("bob", "bob@yahoo.com"),
                    new User("charlie", "charlie@gmail.com")
            );

            // Hàm tìm kiếm trả về Optional
            public Optional<User> findUserByUsername(String username) {
                return users.stream()
                        // Lọc những user có username trùng khớp
                        .filter(user -> user.username().equals(username))
                        // Trả về phần tử đầu tiên tìm thấy (được bọc trong Optional)
                        .findFirst();
            }
        }

        public static void main(String[] args) {
            UserRepository repo = new UserRepository();

            System.out.println("--- Kịch bản 1: Tìm user có tồn tại (alice) ---");
            // Cách 1: Dùng map() và orElse() (Rất gọn gàng để xử lý chuỗi)
            String welcomeMessage = repo.findUserByUsername("alice")
                    .map(user -> "Welcome " + user.username())
                    .orElse("Guest login");
            System.out.println(welcomeMessage);


            System.out.println("\n--- Kịch bản 2: Tìm user KHÔNG tồn tại (david) ---");
            // Cách 2: Dùng ifPresentOrElse (Từ Java 9+ - Cực kỳ phù hợp cho logic thực thi hành động)
            repo.findUserByUsername("david").ifPresentOrElse(
                    user -> System.out.println("Welcome " + user.username()),
                    () -> System.out.println("Guest login") // Hành động khi không có dữ liệu
            );

        /* Ghi chú thêm về ifPresent (Chỉ chạy nếu có dữ liệu, bỏ qua nếu trống)
        repo.findUserByUsername("alice").ifPresent(user -> {
            System.out.println("Chỉ in ra nếu tồn tại: Chào mừng " + user.username());
        });
        */
        }

}
