package SS7.Bai1;

import java.util.List;
import java.util.ArrayList;

public class MainBai1SS7 {

    // Thêm static vào trước mỗi class
    static class Product {
        private String id;
        private String name;
        private double price;

        public Product(String id, String name, double price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
        public String getName() { return name; }
        public double getPrice() { return price; }
        public String getId() { return id; }
    }

    static class Customer {
        private String name;
        private String email;

        public Customer(String name, String email) {
            this.name = name;
            this.email = email;
        }
        public String getName() { return name; }
        public String getEmail() { return email; }
    }

    static class Order {
        private String orderId;
        private Customer customer;
        private List<Product> products = new ArrayList<>();

        public Order(String orderId, Customer customer) {
            this.orderId = orderId;
            this.customer = customer;
        }

        public void addProduct(Product product) {
            products.add(product);
            System.out.println("Đã thêm sản phẩm " + product.getId());
        }

        public String getOrderId() { return orderId; }
        public Customer getCustomer() { return customer; }
        public List<Product> getProducts() { return products; }
    }

    static class OrderCalculator {
        public double calculateTotal(Order order) {
            double total = 0;
            for (Product p : order.getProducts()) {
                total += p.getPrice();
            }
            return total;
        }
    }

    static class OrderRepository {
        public void save(Order order) {
            System.out.println("Đã lưu đơn hàng " + order.getOrderId() + " vào hệ thống.");
        }
    }

    static class EmailService {
        public void sendConfirmation(Order order) {
            System.out.println("Đã gửi email đến " + order.getCustomer().getEmail()
                    + ": Đơn hàng " + order.getOrderId() + " đã được tạo thành công.");
        }
    }

    // Đưa hàm main ra ngoài hoặc để trực tiếp trong MainBai1SS7
    public static void main(String[] args) {
        // 1. Tạo sản phẩm
        Product sp01 = new Product("SP01", "Laptop", 15000000);
        Product sp02 = new Product("SP02", "Chuột", 300000);

        // 2. Tạo khách hàng
        Customer customer = new Customer("Nguyễn Văn A", "a@example.com");
        System.out.println("Đã thêm khách hàng: " + customer.getName());

        // 3. Tạo đơn hàng và thêm sản phẩm
        Order order = new Order("ORD001", customer);
        order.addProduct(sp01);
        order.addProduct(sp02);
        order.addProduct(sp02);
        System.out.println("Đơn hàng " + order.getOrderId() + " được tạo.");

        // 4. Tính tổng tiền
        OrderCalculator calculator = new OrderCalculator();
        double total = calculator.calculateTotal(order);
        System.out.println("Tổng tiền: " + String.format("%.0f", total));

        // 5. Lưu đơn hàng
        OrderRepository repository = new OrderRepository();
        repository.save(order);

        // 6. Gửi email
        EmailService emailService = new EmailService();
        emailService.sendConfirmation(order);
    }
}