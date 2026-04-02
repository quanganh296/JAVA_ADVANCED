package SS3.BaiTapTH;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticatableTest {

    @Test
    void isAuthenticatedWithValidPassword() {
        User user = new User("U001", "user@example.com", "password123", true, LocalDate.now());
        assertTrue(user.isAuthenticated());
    }

    @Test
    void isAuthenticatedWithEmptyPassword() {
        User user = new User("U001", "user@example.com", "", true, LocalDate.now());
        assertFalse(user.isAuthenticated());
    }

    @Test
    void isAuthenticatedWithNullPassword() {
        User user = new User("U001", "user@example.com", null, true, LocalDate.now());
        assertFalse(user.isAuthenticated());
    }

    @Test
    void encryptPasswordPrependsEncryptedPrefix() {
        String encrypted = Authenticatable.encrypt("mypassword");
        assertTrue(encrypted.startsWith("encrypted:"));
    }

    @Test
    void encryptPasswordIncludesOriginalPassword() {
        String originalPassword = "mypassword";
        String encrypted = Authenticatable.encrypt(originalPassword);
        assertEquals("encrypted:" + originalPassword, encrypted);
    }

    @Test
    void encryptPasswordWithSpecialCharacters() {
        String encrypted = Authenticatable.encrypt("p@ss!w0rd#123");
        assertEquals("encrypted:p@ss!w0rd#123", encrypted);
    }

    @Test
    void encryptEmptyPassword() {
        String encrypted = Authenticatable.encrypt("");
        assertEquals("encrypted:", encrypted);
    }

    @Test
    void getPasswordReturnsCorrectPassword() {
        User user = new User("U001", "user@example.com", "testpass", true, LocalDate.now());
        assertEquals("testpass", user.getPassword());
    }
}

