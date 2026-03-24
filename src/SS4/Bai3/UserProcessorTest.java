package SS4.Bai3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserProcessorTest {
    private UserProcessor processor;

    @BeforeEach
    void setUp() {
        // Khởi tạo đối tượng mới trước mỗi phương thức @Test
        processor = new UserProcessor();
    }

    @Test
    void testValidEmail() {
        // Kiểm tra email hợp lệ và đúng giá trị trả về
        String result = processor.processEmail("user@gmail.com");
        assertEquals("user@gmail.com", result);
    }

    @Test
    void testEmailMissingAt() {
        // Kiểm tra email thiếu ký tự '@' phải ném ra ngoại lệ
        assertThrows(IllegalArgumentException.class, () -> {
            processor.processEmail("usergmail.com");
        });
    }

    @Test
    void testEmailMissingDomain() {
        // Kiểm tra email có '@' nhưng thiếu tên miền (ví dụ: "user@")
        assertThrows(IllegalArgumentException.class, () -> {
            processor.processEmail("user@");
        });
    }

    @Test
    void testEmailNormalization() {
        // Kiểm tra tính năng chuyển đổi chữ hoa thành chữ thường
        String result = processor.processEmail("Example@Gmail.com");
        assertEquals("example@gmail.com", result, "Email phải được chuyển về chữ thường");
    }
}