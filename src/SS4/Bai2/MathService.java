package SS4.Bai2;

public class MathService {
    // Phép cộng cơ bản
    public int add(int a, int b) {
        return a + b;
    }

    // Phép chia có kiểm tra lỗi chia cho 0
    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Không thể chia cho số 0");
        }
        return (double) a / b;
    }
}