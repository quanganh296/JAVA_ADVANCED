package SS4.Bai2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MathServiceTest {
    private final MathService service = new MathService();

    @Test
    void testAddPositiveNumbers() {
        // Kiểm tra 5 + 5 phải bằng 10
        assertEquals(10, service.add(5, 5), "Phép cộng 5+5 phải ra 10");
    }

    @Test
    void testDivideSuccessfully() {
        // Kiểm tra 10 / 2 phải bằng 5.0
        assertEquals(5.0, service.divide(10, 2));
    }

    @Test
    void testDivideByZero() {
        // Kiểm tra xem hệ thống có ném ra lỗi ArithmeticException khi chia cho 0 không
        assertThrows(ArithmeticException.class, () -> {
            service.divide(10, 0);
        });
    }
}