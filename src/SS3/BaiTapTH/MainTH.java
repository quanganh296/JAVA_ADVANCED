package SS3.BaiTapTH;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MainTH {
    public static void main(String[] args) {
        // Bước 1: Tạo danh sách User
        List<User> users = List.of(
                new User("1", "alice@example.com", "password1", true, LocalDate.now().minusMonths(30)),
                new User("2", "bob@example.com", "password2", false, LocalDate.now().minusMonths(15)),
                new User("3", "charlie@example.com", "password3", true, LocalDate.now().minusMonths(20)),
                new User("4", "diana@example.com", "password4", false, LocalDate.now().minusMonths(5)),
                new User("5", "eve@example.com", "password5", true, LocalDate.now().minusMonths(25))
        );

        UserService service = new UserService();

        // Bước 2: Lọc người dùng đã xác thực
        List<User> verifiedUsers = service.getVerifiedUsers(users);

        // Bước 3: Chuyển dữ liệu List<User> -> List<PublicUser>
        List<PublicUser> publicUsers = verifiedUsers.stream()
                .map(user -> {
                    long months = ChronoUnit.MONTHS.between(user.createdAt(), LocalDate.now());
                    Tier tier = service.classifyTier(months);
                    return new PublicUser(user.id(), user.email(), tier);
                })
                .toList();

        // Bước 4: In báo cáo, sử dụng Text block để format báo cáo đơn giản
        String report = """
                Verified Users Report:
                %s
                """.formatted(publicUsers.stream()
                        .map(PublicUser::toString)
                        .reduce("", (a, b) -> a + "\n" + b));

        System.out.println(report);
    }
}
