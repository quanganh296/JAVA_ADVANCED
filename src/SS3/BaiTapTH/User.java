package SS3.BaiTapTH;

import java.time.LocalDate;

public record User(String id, String email, String password, boolean verified, LocalDate createdAt) implements Authenticatable {
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{id='%s', email='%s', verified=%s, createdAt=%s}".formatted(id, email, verified, createdAt);
    }
}
