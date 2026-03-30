package SS8.Bai1;




import java.util.Scanner;

// --- 1. SINGLETON PATTERN: Quản lý kết nối phần cứng ---
class HardwareConnection {
    private static HardwareConnection instance;

    // Private constructor để ngăn chặn khởi tạo từ bên ngoài
    private HardwareConnection() {
        System.out.println("\n>>> HardwareConnection: Đang thiết lập kết nối vật lý...");
        System.out.println(">>> HardwareConnection: Đã kết nối phần cứng thành công! (Chỉ khởi tạo 1 lần)");
    }

    public static HardwareConnection getInstance() {
        if (instance == null) {
            instance = new HardwareConnection();
        }
        return instance;
    }

    public void status() {
        System.out.println(">>> Trạng thái: Hệ thống đang sử dụng kết nối duy nhất hiện tại.");
    }
}

// --- 2. FACTORY METHOD PATTERN: Giao diện sản phẩm (Device) ---
interface Device {
    void turnOn();
    void turnOff();
}

// Các lớp thiết bị cụ thể (Concrete Products)
class Light implements Device {
    @Override
    public void turnOn() { System.out.println("Kết quả -> Đèn: Bật sáng."); }
    @Override
    public void turnOff() { System.out.println("Kết quả -> Đèn: Đã tắt."); }
}

class Fan implements Device {
    @Override
    public void turnOn() { System.out.println("Kết quả -> Quạt: Đang quay vù vù."); }
    @Override
    public void turnOff() { System.out.println("Kết quả -> Quạt: Đã dừng."); }
}

class AirConditioner implements Device {
    @Override
    public void turnOn() { System.out.println("Kết quả -> Điều hòa: Đang làm mát 20°C."); }
    @Override
    public void turnOff() { System.out.println("Kết quả -> Điều hòa: Đã nghỉ."); }
}

// --- 3. FACTORY METHOD PATTERN: Lớp Factory trừu tượng ---
abstract class DeviceFactory {
    public abstract Device createDevice();
}

// Các Factory cụ thể (Concrete Factories)
class LightFactory extends DeviceFactory {
    @Override
    public Device createDevice() {
        System.out.println("LightFactory: Đang lắp đặt Đèn...");
        return new Light();
    }
}

class FanFactory extends DeviceFactory {
    @Override
    public Device createDevice() {
        System.out.println("FanFactory: Đang lắp đặt Quạt...");
        return new Fan();
    }
}

class AirConditionerFactory extends DeviceFactory {
    @Override
    public Device createDevice() {
        System.out.println("AirConditionerFactory: Đang lắp đặt Điều hòa...");
        return new AirConditioner();
    }
}

// --- 4. CHƯƠNG TRÌNH CHÍNH (CLIENT) ---
public class MainBai1SS8 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Device currentDevice = null;
        boolean isRunning = true;

        System.out.println("===== HỆ THỐNG ĐIỀU KHIỂN NHÀ THÔNG MINH =====");

        while (isRunning) {
            System.out.println("\n-------------------------------------------");
            System.out.println("MENU CHỨC NĂNG:");
            System.out.println("1. Kết nối phần cứng (Kiểm tra Singleton)");
            System.out.println("2. Tạo thiết bị mới (Sử dụng Factory)");
            System.out.println("3. Bật thiết bị vừa tạo");
            System.out.println("4. Tắt thiết bị vừa tạo");
            System.out.println("5. Thoát");
            System.out.print("Lựa chọn của bạn: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Thử lấy instance nhiều lần để kiểm tra Singleton
                    HardwareConnection connection = HardwareConnection.getInstance();
                    connection.status();
                    break;

                case 2:
                    System.out.println("\nChọn loại thiết bị muốn tạo:");
                    System.out.println("1. Đèn");
                    System.out.println("2. Quạt");
                    System.out.println("3. Điều hòa");
                    System.out.print("Chọn (1-3): ");
                    int type = scanner.nextInt();

                    DeviceFactory factory = null;
                    if (type == 1) {
                        factory = new LightFactory();
                    } else if (type == 2) {
                        factory = new FanFactory();
                    } else if (type == 3) {
                        factory = new AirConditionerFactory();
                    } else {
                        System.out.println("Lựa chọn không hợp lệ!");
                        break;
                    }

                    // Factory tạo thiết bị mà Client không cần dùng từ khóa 'new' trực tiếp cho Device
                    currentDevice = factory.createDevice();
                    System.out.println("=> Thông báo: Thiết bị đã sẵn sàng.");
                    break;

                case 3:
                    if (currentDevice != null) {
                        currentDevice.turnOn();
                    } else {
                        System.out.println("Lỗi: Bạn chưa tạo thiết bị nào!");
                    }
                    break;

                case 4:
                    if (currentDevice != null) {
                        currentDevice.turnOff();
                    } else {
                        System.out.println("Lỗi: Bạn chưa tạo thiết bị nào!");
                    }
                    break;

                case 5:
                    System.out.println("Đang thoát hệ thống... Tạm biệt!");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Lựa chọn không đúng, vui lòng thử lại.");
            }
        }
        scanner.close();
    }
}
