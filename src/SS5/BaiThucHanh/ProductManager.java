package SS5.BaiThucHanh;
import java.util.*;
import java.util.stream.Collectors;

public class ProductManager {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product p) throws InvalidProductException {
        boolean exists = products.stream().anyMatch(prod -> prod.getId() == p.getId());
        if(exists) {
            throw new InvalidProductException("Lỗi: Sản phẩm với ID " + p.getId() + " đã tồn tại!");
        }
        products.add(p);
        System.out.println("Sản phẩm đã được thêm thành công!");
    }

    public void displayAll() {
        if(products.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống!");
            return;
        }
        System.out.println("| ID    | Tên sản phẩm     | Giá       | Số lượng | Danh mục      |");
        System.out.println("-------------------------------------------------------------------");
        products.forEach(System.out::println);
        System.out.println("---------------------------------------------------------------");
    }

    public void updateQuantity(int id, int newQty) throws InvalidProductException {
        Optional<Product> opt = products.stream().filter(p -> p.getId() == id).findFirst();
        if(opt.isPresent()) {
            opt.get().setQuantity(newQty);
            System.out.println("Số lượng sản phẩm đã được cập nhật!");
        } else {
            throw new InvalidProductException("Lỗi: Không tìm thấy sản phẩm với ID " + id);
        }
    }

    public void deleteOutOfStock() {
        int initialSize = products.size();
        products.removeIf(p -> p.getQuantity() == 0);
        System.out.println("Đã xóa " + (initialSize - products.size()) + " sản phẩm hết hàng.");
    }
}