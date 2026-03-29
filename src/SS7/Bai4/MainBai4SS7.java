package SS7.Bai4;

import java.util.ArrayList;
import java.util.List;


class Order {
    private String orderId;
    public Order(String orderId) { this.orderId = orderId; }
    public String getOrderId() { return orderId; }
}

// Interface cho việc lưu trữ
interface OrderRepository {
    void save(Order order);
    List<Order> findAll();
}

// Interface cho việc thông báo
interface NotificationService {
    void send(String message, String recipient);
}

// =========================================================
// 2. LOW-LEVEL MODULES (Các triển khai cụ thể)
// =========================================================

// --- Triển khai Repository ---
class FileOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {
        System.out.println("Lưu đơn hàng vào FILE: " + order.getOrderId());
    }
    @Override
    public List<Order> findAll() { return new ArrayList<>(); }
}

class DatabaseOrderRepository implements OrderRepository {
    @Override
    public void save(Order order) {
        System.out.println("Lưu đơn hàng vào DATABASE: " + order.getOrderId());
    }
    @Override
    public List<Order> findAll() { return new ArrayList<>(); }
}

// --- Triển khai Notification ---
class EmailService implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Gửi EMAIL đến " + recipient + ": " + message);
    }
}

class SMSNotification implements NotificationService {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Gửi SMS đến " + recipient + ": " + message);
    }
}

// =========================================================
// 3. HIGH-LEVEL MODULE (Lớp cấp cao - OrderService)
// TUÂN THỦ DIP: Không phụ thuộc vào class cụ thể, chỉ phụ thuộc Interface
// =========================================================

class OrderService {
    private final OrderRepository repository;
    private final NotificationService notificationService;

    // Dependency Injection qua Constructor
    public OrderService(OrderRepository repository, NotificationService notificationService) {
        this.repository = repository;
        this.notificationService = notificationService;
    }

    public void createOrder(Order order, String contact) {
        repository.save(order);
        notificationService.send("Đơn hàng " + order.getOrderId() + " đã được tạo", contact);
    }
}

// =========================================================
// 4. CHƯƠNG TRÌNH CHÍNH (MAIN)
// =========================================================

public class MainBai4SS7 {
    public static void main(String[] args) {
        System.out.println("--- Cấu hình 1: File + Email ---");
        // Inject File và Email vào Service
        OrderRepository fileRepo = new FileOrderRepository();
        NotificationService emailSvc = new EmailService();
        OrderService service1 = new OrderService(fileRepo, emailSvc);

        service1.createOrder(new Order("ORD001"), "customer@example.com");

        System.out.println("\n--- Cấu hình 2: Database + SMS (Không sửa code OrderService) ---");
        // Chỉ cần thay đổi đối tượng truyền vào (Plug & Play)
        OrderRepository dbRepo = new DatabaseOrderRepository();
        NotificationService smsSvc = new SMSNotification();
        OrderService service2 = new OrderService(dbRepo, smsSvc);

        service2.createOrder(new Order("ORD002"), "0901234567");
    }
}