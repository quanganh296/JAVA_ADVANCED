package SS4.bai1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserValidatorTest {
    private final UserValidator validator = new UserValidator();

    @Test
    void testValidUsername() {
        assertTrue(validator.isValidUsername("user123"));
    }

    @Test
    void testTooShort() {
        assertFalse(validator.isValidUsername("abc"));
    }

    @Test
    void testContainsSpace() {
        assertFalse(validator.isValidUsername("user name"));
    }
}
