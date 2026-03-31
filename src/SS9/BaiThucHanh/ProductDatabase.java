package SS9.BaiThucHanh;

import java.util.ArrayList;
import java.util.List;

public class ProductDatabase {
    // Biến instance kiểu static và private
    private static volatile ProductDatabase instance;

    // Danh sách List<Product>
    private List<Product> products;

    // Constructor private
    private ProductDatabase() {
        products = new ArrayList<>();
    }

    // Xử lý Thread-safe với block synchronized
    public static ProductDatabase getInstance() {
        if (instance == null) {
            synchronized (ProductDatabase.class) {
                if (instance == null) {
                    instance = new ProductDatabase();
                }
            }
        }
        return instance;
    }

    // Các phương thức CRUD
    public void addProduct(Product product) {
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProductById(String id) {
        for (Product p : products) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public boolean deleteProduct(String id) {
        Product p = getProductById(id);
        if (p != null) {
            products.remove(p); //
            return true;
        }
        return false;
    }
}