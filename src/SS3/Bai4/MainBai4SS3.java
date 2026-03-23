package SS3.Bai4;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MainBai4SS3 {



        // Tạo record User
        public record User(String username, String email) {}

        public static void main(String[] args) {
            // 1. Tạo danh sách có chứa các username bị trùng lặp
            List<User> users = List.of(
                    new User("alice", "alice@gmail.com"),
                    new User("bob", "bob@yahoo.com"),
                    new User("alice", "alice_new@example.com"), // Bị trùng username "alice"
                    new User("charlie", "charlie@gmail.com"),
                    new User("bob", "bob_work@domain.com")      // Bị trùng username "bob"
            );

            System.out.println("--- Danh sách sau khi loại bỏ trùng lặp theo Username ---");

            // 2. Dùng Stream và Collectors.toMap để lọc trùng
            Collection<User> uniqueUsers = users.stream()
                    .collect(Collectors.toMap(
                            User::username,                     // Key của Map là username
                            user -> user,                       // Value của Map là chính đối tượng User đó
                            (existing, replacement) -> existing // Nếu trùng Key, quyết định giữ lại User cũ (existing)
                    ))
                    .values();                              // Chỉ lấy danh sách các Value (bỏ Key đi)

            // 3. In danh sách kết quả
            uniqueUsers.forEach(System.out::println);
        }

}
