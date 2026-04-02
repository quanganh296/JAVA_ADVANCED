package SS3.BaiTapTH;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    void userRecordContainsAllRequiredFields() {
        LocalDate createdAt = LocalDate.of(2025, 1, 15);
        User user = new User("U001", "john@example.com", "pass123", true, createdAt);
        
        assertEquals("U001", user.id());
        assertEquals("john@example.com", user.email());
        assertEquals("pass123", user.password());
        assertTrue(user.verified());
        assertEquals(createdAt, user.createdAt());
    }

    @Test
    void userToStringFormatExcludesPassword() {
        LocalDate createdAt = LocalDate.of(2025, 1, 15);
        User user = new User("U001", "john@example.com", "secret123", true, createdAt);
        
        String toString = user.toString();
        assertFalse(toString.contains("secret123"));
        assertTrue(toString.contains("U001"));
        assertTrue(toString.contains("john@example.com"));
    }

    @Test
    void userImplementsAuthenticatable() {
        User user = new User("U001", "john@example.com", "pass123", true, LocalDate.now());
        assertInstanceOf(Authenticatable.class, user);
    }

    @Test
    void userWithVerifiedTrue() {
        User user = new User("U001", "john@example.com", "pass123", true, LocalDate.now());
        assertTrue(user.verified());
    }

    @Test
    void userWithVerifiedFalse() {
        User user = new User("U001", "john@example.com", "pass123", false, LocalDate.now());
        assertFalse(user.verified());
    }

    @Test
    void userCanBeCreatedWithHistoricalDate() {
        LocalDate pastDate = LocalDate.of(2020, 6, 30);
        User user = new User("U001", "john@example.com", "pass123", true, pastDate);
        assertEquals(pastDate, user.createdAt());
    }
}

