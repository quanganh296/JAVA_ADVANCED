package SS7.Bai3;

interface PaymentMethod {
    void process(double amount);
}

// Các interface chuyên biệt (nếu cần mở rộng các tính năng đặc thù sau này)
interface CODPayable extends PaymentMethod {}
interface CardPayable extends PaymentMethod {}
interface EWalletPayable extends PaymentMethod {}

// =========================================================
// 2. CÁC LỚP TRIỂN KHAI (IMPLEMENTATIONS)
// =========================================================

class CODPayment implements CODPayable {
    @Override
    public void process(double amount) {
        System.out.println("Xử lý thanh toán COD: " + String.format("%,.0f", amount) + " - Thành công");
    }
}

class CreditCardPayment implements CardPayable {
    @Override
    public void process(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng: " + String.format("%,.0f", amount) + " - Thành công");
    }
}

class MomoPayment implements EWalletPayable {
    @Override
    public void process(double amount) {
        System.out.println("Xử lý thanh toán MoMo: " + String.format("%,.0f", amount) + " - Thành công");
    }
}

// =========================================================
// 3. LỚP XỬ LÝ THANH TOÁN (PAYMENT PROCESSOR)
// Phụ thuộc vào interface tổng quát (PaymentMethod)
// =========================================================

class PaymentProcessor {
    private PaymentMethod paymentMethod;

    public PaymentProcessor(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void execute(double amount) {
        paymentMethod.process(amount);
    }
}

// =========================================================
// 4. CHƯƠNG TRÌNH CHÍNH (MAIN)
// =========================================================

public class MainBai3SS7 {
    public static void main(String[] args) {
        System.out.println("--- HỆ THỐNG THANH TOÁN (ISP & LSP) ---");

        // 1. Xử lý COD
        PaymentProcessor processor = new PaymentProcessor(new CODPayment());
        processor.execute(500000);

        // 2. Xử lý Thẻ tín dụng
        processor.setPaymentMethod(new CreditCardPayment());
        processor.execute(1000000);

        // 3. Xử lý Ví MoMo
        processor.setPaymentMethod(new MomoPayment());
        processor.execute(750000);

        // -----------------------------------------------------
        // 4. CHỨNG MINH LSP (Liskov Substitution Principle)
        // -----------------------------------------------------
        System.out.println("\n--- Kiểm tra tính thay thế (LSP) ---");

        // Tạo một biến thuộc interface tổng quát
        PaymentMethod generalMethod;

        // Gán bằng CreditCardPayment
        generalMethod = new CreditCardPayment();
        System.out.print("Đang dùng Thẻ: ");
        generalMethod.process(100000);

        // THAY THẾ bằng MomoPayment mà không cần đổi kiểu dữ liệu hay logic gọi hàm
        // Chương trình vẫn chạy đúng với hành vi mới của lớp thay thế
        generalMethod = new MomoPayment();
        System.out.print("Thay thế bằng MoMo: ");
        generalMethod.process(100000);

        System.out.println("\n=> Kết luận: Các lớp con đã thay thế hoàn hảo cho interface cha mà không làm hỏng chương trình.");
    }
}