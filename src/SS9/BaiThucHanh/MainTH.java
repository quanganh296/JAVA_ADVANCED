package SS9.BaiThucHanh;

import java.util.List;
import java.util.Scanner;

public class MainTH {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductDatabase db = ProductDatabase.getInstance();
        boolean running = true;

        while (running) { // Vòng lặp kết hợp Scanner
            System.out.println("== ---------------------- QUẢN LÝ SẢN PHẨM ---------------------- =="); //
            System.out.println("1. Thêm mới sản phẩm"); //
            System.out.println("2. Xem danh sách sản phẩm"); //
            System.out.println("3. Cập nhật thông tin sản phẩm"); //
            System.out.println("4. Xoá sản phẩm"); //
            System.out.println("5. Thoát"); //
            System.out.println("-----------------------------------------------------------------------"); //
            System.out.print("Lựa chọn của bạn: "); //

            String choice = scanner.nextLine(); // Chống lỗi trôi lệnh

            switch (choice) {
                case "1":
                    // Create
                    System.out.print("Chọn loại sản phẩm (1: Vật lý, 2: Kỹ thuật số): ");
                    String type = scanner.nextLine();
                    if (!type.equals("1") && !type.equals("2")) {
                        System.out.println("Loại sản phẩm không hợp lệ!");
                        break;
                    }

                    System.out.print("Nhập ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Nhập Tên: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập Giá: ");
                    double price = Double.parseDouble(scanner.nextLine());

                    double specificAttr = 0;
                    if (type.equals("1")) {
                        System.out.print("Nhập Trọng lượng (kg): ");
                        specificAttr = Double.parseDouble(scanner.nextLine());
                    } else {
                        System.out.print("Nhập Dung lượng (MB): ");
                        specificAttr = Double.parseDouble(scanner.nextLine());
                    }

                    // Dùng ProductFactory để tạo đối tượng
                    Product newProduct = ProductFactory.createProduct(type, id, name, price, specificAttr);
                    if (newProduct != null) {
                        db.addProduct(newProduct);
                        System.out.println("Đã thêm sản phẩm thành công!");
                    }
                    break;

                case "2":
                    // Read
                    List<Product> list = db.getAllProducts();
                    if (list.isEmpty()) {
                        System.out.println("Danh sách trống!");
                    } else {
                        System.out.println("--- Danh sách sản phẩm ---");
                        for (Product p : list) {
                            p.displayInfo(); // Thể hiện tính đa hình
                        }
                    }
                    break;

                case "3":

                    System.out.print("Nhập ID sản phẩm cần cập nhật: ");
                    String updateId = scanner.nextLine();
                    Product pToUpdate = db.getProductById(updateId);

                    if (pToUpdate != null) {
                        System.out.print("Nhập Tên mới (Bỏ qua để giữ nguyên): ");
                        String newName = scanner.nextLine();
                        if (!newName.trim().isEmpty()) pToUpdate.setName(newName);

                        System.out.print("Nhập Giá mới (Nhập -1 để giữ nguyên): ");
                        double newPrice = Double.parseDouble(scanner.nextLine());
                        if (newPrice != -1) pToUpdate.setPrice(newPrice);

                        // Sử dụng instanceof để cập nhật thuộc tính riêng
                        if (pToUpdate instanceof PhysicalProduct) {
                            System.out.print("Nhập Trọng lượng mới (Nhập -1 để giữ nguyên): ");
                            double newWeight = Double.parseDouble(scanner.nextLine());
                            if (newWeight != -1) ((PhysicalProduct) pToUpdate).setWeight(newWeight);
                        } else if (pToUpdate instanceof DigitalProduct) {
                            System.out.print("Nhập Dung lượng mới (Nhập -1 để giữ nguyên): ");
                            double newSize = Double.parseDouble(scanner.nextLine());
                            if (newSize != -1) ((DigitalProduct) pToUpdate).setSizeInMB(newSize);
                        }
                        System.out.println("Cập nhật thành công!");
                    } else {
                        System.out.println("Không tìm thấy sản phẩm với ID này!");
                    }
                    break;

                case "4":
                    // Delete
                    System.out.print("Nhập ID sản phẩm cần xóa: ");
                    String deleteId = scanner.nextLine();
                    boolean isDeleted = db.deleteProduct(deleteId); // Xóa khỏi danh sách

                    if (isDeleted) {
                        System.out.println("Xóa thành công!");
                    } else {
                        System.out.println("Không tìm thấy sản phẩm! Xóa thất bại."); //
                    }
                    break;

                case "5":
                    running = false;
                    System.out.println("Đã thoát chương trình."); //
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng thử lại!");
            }
            System.out.println();
        }
        scanner.close();
    }
}
