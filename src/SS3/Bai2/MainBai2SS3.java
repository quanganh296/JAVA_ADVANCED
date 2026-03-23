package SS3.Bai2;
import java.util.List;

public class MainBai2SS3 {



        // Tạo record User với 2 trường cơ bản: username và email
        public record User(String username, String email) {}

        public static void main(String[] args) {
            // 1. Tạo danh sách người dùng giả định
            List<User> users = List.of(
                    new User("alice", "alice@gmail.com"),
                    new User("bob", "bob@yahoo.com"),
                    new User("charlie", "charlie@gmail.com"),
                    new User("david", "david@outlook.com")
            );

            System.out.println("--- Những người dùng sử dụng Gmail ---");

            // 2. Dùng Stream để lọc và in ra username
            users.stream()
                    // Lọc những user có email kết thúc bằng "@gmail.com"
                    .filter(user -> user.email().endsWith("@gmail.com"))
                    // Lặp qua danh sách đã lọc và chỉ in ra thuộc tính username
                    .forEach(user -> System.out.println(user.username()));
        }

}
