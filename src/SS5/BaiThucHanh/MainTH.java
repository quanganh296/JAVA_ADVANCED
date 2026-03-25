package SS5.BaiThucHanh;
import java.util.Scanner;


public class MainTH {
        public static void main(String[] args) {
            ProductManager manager = new ProductManager();
            Scanner sc = new Scanner(System.in);
            int choice;

            do {
                System.out.println("\n========= PRODUCT MANAGEMENT SYSTEM =========");
                System.out.println("1. Thêm sản phẩm mới");
                System.out.println("2. Hiển thị danh sách sản phẩm");
                System.out.println("3. Cập nhật số lượng theo ID");
                System.out.println("4. Xóa sản phẩm đã hết hàng");
                System.out.println("5. Thoát chương trình");
                System.out.println("=============================================");
                System.out.print("Lựa chọn của bạn: ");

                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    choice = 0;
                }

                try {
                    switch (choice) {
                        case 1:
                            System.out.print("Nhập ID: "); int id = Integer.parseInt(sc.nextLine());
                            System.out.print("Nhập tên: "); String name = sc.nextLine();
                            System.out.print("Nhập giá: "); double price = Double.parseDouble(sc.nextLine());
                            System.out.print("Nhập số lượng: "); int qty = Integer.parseInt(sc.nextLine());
                            System.out.print("Nhập danh mục: "); String cat = sc.nextLine();
                            manager.addProduct(new Product(id, name, price, qty, cat));
                            break;
                        case 2:
                            manager.displayAll();
                            break;
                        case 3:
                            System.out.print("Nhập ID cần cập nhật: "); int upId = Integer.parseInt(sc.nextLine());
                            System.out.print("Nhập số lượng mới: "); int upQty = Integer.parseInt(sc.nextLine());
                            manager.updateQuantity(upId, upQty);
                            break;
                        case 4:
                            manager.deleteOutOfStock();
                            break;
                        case 5:
                            System.out.println("Tạm biệt!");
                            break;
                        default:
                            System.out.println("Lựa chọn không hợp lệ!");
                    }
                } catch (InvalidProductException e) {
                    System.err.println(e.getMessage());
                } catch (Exception e) {
                    System.err.println("Lỗi nhập liệu, vui lòng thử lại!");
                }
            } while (choice != 5);
        }
    }

