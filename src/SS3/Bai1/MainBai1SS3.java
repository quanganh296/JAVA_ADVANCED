package SS3.Bai1;
import java.util.List;

public class MainBai1SS3 {

        // 1. Tạo Enum cho Status để quản lý trạng thái an toàn hơn
        public enum Status {
            ACTIVE, INACTIVE
        }

        // 2. Tạo record User với 3 trường: username, email, status
        public record User(String username, String email, Status status) {}

        public static void main(String[] args) {
            // 3. Tạo 3 User và đưa vào một danh sách (List)
            List<User> users = List.of(
                    new User("alice", "alice@example.com", Status.ACTIVE),
                    new User("bob", "bob@example.com", Status.INACTIVE),
                    new User("charlie", "charlie@example.com", Status.ACTIVE)
            );

            // 4. In danh sách người dùng ra màn hình bằng users.forEach(...)
            System.out.println("--- Danh sách người dùng ---");

            // Cách 1: Sử dụng Lambda Expression
            users.forEach(user -> System.out.println(user));

        /* Cách 2: Viết gọn hơn bằng Method Reference (Bạn có thể dùng dòng dưới thay cho dòng trên)
        users.forEach(System.out::println);
        */
        }
    }

