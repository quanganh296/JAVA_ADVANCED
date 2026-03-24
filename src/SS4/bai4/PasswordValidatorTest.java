package SS4.bai4;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordValidatorTest {
    private final PasswordValidator validator = new PasswordValidator();

    @Test
    void testPasswordStrengthLevels() {
        assertAll("Kiểm thử các cấp độ bảo mật của mật khẩu",
                // TC01: Mạnh
                () -> assertEquals("Mạnh", validator.evaluatePasswordStrength("Abc123!@"), "TC01 thất bại"),

                // TC02 - TC05: Trung bình (Thiếu 1 loại ký tự)
                () -> assertEquals("Trung bình", validator.evaluatePasswordStrength("abc123!@"), "TC02 thất bại"),
                () -> assertEquals("Trung bình", validator.evaluatePasswordStrength("ABC123!@"), "TC03 thất bại"),
                () -> assertEquals("Trung bình", validator.evaluatePasswordStrength("Abcdef!@"), "TC04 thất bại"),
                () -> assertEquals("Trung bình", validator.evaluatePasswordStrength("Abc12345"), "TC05 thất bại"),

                // TC06 - TC08: Yếu (Quá ngắn hoặc quá ít loại ký tự)
                () -> assertEquals("Yếu", validator.evaluatePasswordStrength("Ab1!"), "TC06 thất bại"),
                () -> assertEquals("Yếu", validator.evaluatePasswordStrength("password"), "TC07 thất bại"),
                () -> assertEquals("Yếu", validator.evaluatePasswordStrength("ABC12345"), "TC08 thất bại")
        );
    }
}