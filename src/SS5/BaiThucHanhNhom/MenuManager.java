package SS5.BaiThucHanhNhom;

import java.util.*;
import java.util.stream.Collectors;

public class MenuManager {
    private List<MenuItem> menuList = new ArrayList<>(); // Sử dụng ArrayList quản lý danh sách [cite: 23]

    // 1. Thêm mới: Kiểm tra trùng ID bằng Stream anyMatch [cite: 27]
    public void addMenuItem(MenuItem item) throws InvalidMenuItemException {
        if (menuList.stream().anyMatch(i -> i.getId() == item.getId())) {
            throw new InvalidMenuItemException("Lỗi: ID " + item.getId() + " đã tồn tại trong hệ thống! ");
        }
        menuList.add(item);
        System.out.println("Thêm món ăn thành công.");
    }

    // 2. Hiển thị danh sách dạng bảng dùng forEach [cite: 28]
    public void displayAll() {
        if (menuList.isEmpty()) {
            System.out.println("Danh sách menu đang trống.");
            return;
        }
        printHeader();
        menuList.forEach(System.out::println);
    }

    // 3. Cập nhật số lượng dùng Optional và findFirst [cite: 29, 30]
    public void updateQuantity(int id, int newQty) throws InvalidMenuItemException {
        Optional<MenuItem> itemOpt = menuList.stream()
                .filter(i -> i.getId() == id)
                .findFirst();

        if (itemOpt.isPresent()) {
            itemOpt.get().setQuantity(newQty);
            System.out.println("Cập nhật số lượng thành công.");
        } else {
            throw new InvalidMenuItemException("Lỗi: Không tìm thấy món ăn có ID " + id + " để cập nhật!");
        }
    }

    // 4. Xóa món hết hàng dùng removeIf [cite: 31]
    public void deleteOutOfStock() {
        menuList.removeIf(i -> i.getQuantity() == 0);
        System.out.println("Đã dọn dẹp các món có số lượng bằng 0.");
    }

    // 5. Tìm kiếm theo tên (Sử dụng Stream Filter)
    public void searchByName(String keyword) {
        List<MenuItem> results = menuList.stream()
                .filter(i -> i.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());

        if (results.isEmpty()) System.out.println("Không tìm thấy món nào chứa từ khóa: " + keyword);
        else {
            printHeader();
            results.forEach(System.out::println);
        }
    }

    // 6. Tìm kiếm theo khoảng giá (Sử dụng Stream Filter)
    public void searchByPriceRange(double min, double max) {
        List<MenuItem> results = menuList.stream()
                .filter(i -> i.getPrice() >= min && i.getPrice() <= max)
                .collect(Collectors.toList());

        if (results.isEmpty()) System.out.println("Không có món nào trong tầm giá này.");
        else {
            printHeader();
            results.forEach(System.out::println);
        }
    }

    private void printHeader() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("| ID    | Tên Món Ăn           | Giá        | Số Lượng | Danh Mục     |");
        System.out.println("-----------------------------------------------------------------------");
    }
}