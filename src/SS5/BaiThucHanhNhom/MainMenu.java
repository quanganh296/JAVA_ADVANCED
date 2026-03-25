package SS5.BaiThucHanhNhom;

import java.util.Scanner;

public class MainMenu {
    public static void main(String[] args) {
        MenuManager manager = new MenuManager();
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {
            System.out.println("\n========= MENU MANAGEMENT SYSTEM =========");
            System.out.println("1. Thêm món ăn mới ");
            System.out.println("2. Hiển thị danh sách menu ");
            System.out.println("3. Cập nhật số lượng theo ID ");
            System.out.println("4. Xóa món đã hết hàng ");
            System.out.println("5. Tìm kiếm món theo tên");
            System.out.println("6. Tìm kiếm theo khoảng giá");
            System.out.println("0. Thoát chương trình ");
            System.out.println("==========================================");
            System.out.print("Lựa chọn của bạn: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1:
                        System.out.print("Nhập ID: "); int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập tên món: "); String name = sc.nextLine();
                        System.out.print("Nhập giá: "); double price = Double.parseDouble(sc.nextLine());
                        System.out.print("Nhập số lượng: "); int qty = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập danh mục: "); String cat = sc.nextLine();
                        manager.addMenuItem(new MenuItem(id, name, price, qty, cat));
                        break;
                    case 2:
                        manager.displayAll();
                        break;
                    case 3:
                        System.out.print("Nhập ID cần sửa: "); int upId = Integer.parseInt(sc.nextLine());
                        System.out.print("Nhập số lượng mới: "); int upQty = Integer.parseInt(sc.nextLine());
                        manager.updateQuantity(upId, upQty);
                        break;
                    case 4:
                        manager.deleteOutOfStock();
                        break;
                    case 5:
                        System.out.print("Nhập tên món cần tìm: ");
                        manager.searchByName(sc.nextLine());
                        break;
                    case 6:
                        System.out.print("Giá từ: "); double min = Double.parseDouble(sc.nextLine());
                        System.out.print("Đến: "); double max = Double.parseDouble(sc.nextLine());
                        manager.searchByPriceRange(min, max);
                        break;
                    case 0:
                        System.out.println("Hệ thống đã tắt.");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                }
            } catch (InvalidMenuItemException e) {
                System.err.println(e.getMessage());
            } catch (Exception e) {
                System.err.println("Lỗi nhập liệu! Vui lòng kiểm tra lại định dạng số.");
            }
        }
    }
}