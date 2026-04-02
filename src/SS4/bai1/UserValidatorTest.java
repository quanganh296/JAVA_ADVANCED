package SS4.bai1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidatorTest {
    private final UserValidator validator = new UserValidator();

    @Test
    void validUsernameWithNumbers() {
        assertTrue(validator.isValidUsername("user123"));
    }

    @Test
    void validUsernameMinimumLength() {
        assertTrue(validator.isValidUsername("user12"));
    }

    @Test
    void validUsernameMaximumLength() {
        assertTrue(validator.isValidUsername("user1234567890123abc"));
    }

    @Test
    void validUsernameWithOnlyLetters() {
        assertTrue(validator.isValidUsername("username"));
    }

    @Test
    void usernameBeforeMinimumLength() {
        assertFalse(validator.isValidUsername("abc"));
    }

    @Test
    void usernameExceedsMaximumLength() {
        assertFalse(validator.isValidUsername("user123456789012345ab"));
    }

    @Test
    void usernameContainsSpace() {
        assertFalse(validator.isValidUsername("user name"));
    }

    @Test
    void usernameWithLeadingSpace() {
        assertFalse(validator.isValidUsername(" user123"));
    }

    @Test
    void usernameWithTrailingSpace() {
        assertFalse(validator.isValidUsername("user123 "));
    }

    @Test
    void usernameIsNull() {
        assertFalse(validator.isValidUsername(null));
    }

    @Test
    void usernameIsEmpty() {
        assertFalse(validator.isValidUsername(""));
    }

    @Test
    void usernameWithSpecialCharacters() {
        assertFalse(validator.isValidUsername("user@123"));
    }
}
