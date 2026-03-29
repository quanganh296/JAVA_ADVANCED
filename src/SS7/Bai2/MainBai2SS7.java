package SS7.Bai2;

import java.text.DecimalFormat;

// =========================================================
// 1. INTERFACE (Hợp đồng chung)
// Đóng vai trò là "Open" (mở rộng thoải mái qua việc implement)
// =========================================================
interface DiscountStrategy {
    double applyDiscount(double totalAmount);
}

// =========================================================
// 2. CÁC CHIẾN LƯỢC GIẢM GIÁ CỤ THỂ
// =========================================================

// Giảm theo %
class PercentageDiscount implements DiscountStrategy {
    private double percentage;

    public PercentageDiscount(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * (1 - percentage / 100);
    }
}

// Giảm một số tiền cố định
class FixedDiscount implements DiscountStrategy {
    private double amount;

    public FixedDiscount(double amount) {
        this.amount = amount;
    }

    @Override
    public double applyDiscount(double totalAmount) {
        return Math.max(0, totalAmount - amount); // Không để tiền âm
    }
}

// Không giảm giá
class NoDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount;
    }
}

// MỞ RỘNG: Thêm loại giảm giá ngày lễ (Không cần sửa code của 3 class trên)
class HolidayDiscount implements DiscountStrategy {
    @Override
    public double applyDiscount(double totalAmount) {
        return totalAmount * 0.85; // Giảm 15%
    }
}

// =========================================================
// 3. LỚP XỬ LÝ (CONTEXT)
// Tuân thủ "Closed": Không bao giờ phải sửa file này khi thêm loại giảm giá mới
// =========================================================
class OrderCalculator {
    private DiscountStrategy discountStrategy;

    // Constructor nhận vào một chiến lược
    public OrderCalculator(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
    }

    // Setter để đổi chiến lược linh hoạt lúc đang chạy (Runtime)
    public void setDiscountStrategy(DiscountStrategy strategy) {
        this.discountStrategy = strategy;
    }

    public double calculateFinalAmount(double totalAmount) {
        return discountStrategy.applyDiscount(totalAmount);
    }
}

// =========================================================
// 4. CHƯƠNG TRÌNH CHÍNH (MAIN)
// =========================================================
public class MainBai2SS7 {
    public static void main(String[] args) {
        double totalAmount = 1000000;
        DecimalFormat df = new DecimalFormat("#,###"); // Định dạng số cho đẹp

        System.out.println("Đơn hàng gốc: " + df.format(totalAmount) + " VNĐ");
        System.out.println("--------------------------------------------");

        // 1. Áp dụng PercentageDiscount 10%
        OrderCalculator calculator = new OrderCalculator(new PercentageDiscount(10));
        printResult("Áp dụng PercentageDiscount 10%", calculator.calculateFinalAmount(totalAmount), df);

        // 2. Áp dụng FixedDiscount 50.000
        calculator.setDiscountStrategy(new FixedDiscount(50000));
        printResult("Áp dụng FixedDiscount 50.000", calculator.calculateFinalAmount(totalAmount), df);

        // 3. Áp dụng NoDiscount
        calculator.setDiscountStrategy(new NoDiscount());
        printResult("Áp dụng NoDiscount", calculator.calculateFinalAmount(totalAmount), df);

        // 4. THÊM MỚI: HolidayDiscount 15% (Minh chứng OCP)
        // Ta không hề sửa code trong OrderCalculator nhưng vẫn dùng được HolidayDiscount
        calculator.setDiscountStrategy(new HolidayDiscount());
        printResult("Thêm HolidayDiscount 15% (Không sửa code cũ)", calculator.calculateFinalAmount(totalAmount), df);
    }

    // Hàm hỗ trợ in kết quả cho gọn
    private static void printResult(String label, double result, DecimalFormat df) {
        System.out.println(label);
        System.out.println("==> Số tiền sau giảm: " + df.format(result) + " VNĐ");
        System.out.println();
    }
}