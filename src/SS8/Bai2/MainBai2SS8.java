package SS8.Bai2;


import java.util.Scanner;

// ==========================================
// PART 1: ADAPTER PATTERN (Tích hợp cảm biến)
// ==========================================

// Interface mới mà hệ thống đang sử dụng
interface TemperatureSensor {
    double getTemperatureCelsius();
}

// Lớp cảm biến cũ (không được sửa code lớp này)
class OldThermometer {
    public int getTemperatureFahrenheit() {
        return 78; // Giả lập trả về 78 độ F
    }
}

// Lớp Adapter để kết nối cũ và mới
class ThermometerAdapter implements TemperatureSensor {
    private OldThermometer oldThermometer;

    public ThermometerAdapter(OldThermometer oldThermometer) {
        this.oldThermometer = oldThermometer;
    }

    @Override
    public double getTemperatureCelsius() {
        int f = oldThermometer.getTemperatureFahrenheit();
        // Công thức: C = (F - 32) * 5 / 9
        double c = (f - 32) * 5.0 / 9.0;
        return Math.round(c * 10.0) / 10.0; // Làm tròn 1 chữ số thập phân
    }
}

// ==========================================
// PART 2: CÁC THIẾT BỊ HỆ THỐNG (Subsystems)
// ==========================================

class Light {
    void off() { System.out.println("FACADE: Tắt đèn"); }
}

class Fan {
    void off() { System.out.println("FACADE: Tắt quạt"); }
    void setLowSpeed() { System.out.println("FACADE: Quạt chạy tốc độ thấp"); }
}

class AirConditioner {
    void off() { System.out.println("FACADE: Tắt điều hòa"); }
    void setTemperature(int temp) { System.out.println("FACADE: Điều hòa set " + temp + "°C"); }
}

// ==========================================
// PART 3: FACADE PATTERN (Điều khiển tập trung)
// ==========================================

class SmartHomeFacade {
    private Light light;
    private Fan fan;
    private AirConditioner ac;
    private TemperatureSensor sensor;

    public SmartHomeFacade(Light light, Fan fan, AirConditioner ac, TemperatureSensor sensor) {
        this.light = light;
        this.fan = fan;
        this.ac = ac;
        this.sensor = sensor;
    }

    public void getCurrentTemperature() {
        double temp = sensor.getTemperatureCelsius();
        // Lấy giá trị F để in ra giống yêu cầu bài tập (chỉ dùng minh họa)
        System.out.println("Nhiệt độ hiện tại: " + temp + "°C (chuyển đổi từ 78°F)");
    }

    public void leaveHome() {
        System.out.println("\n--- Đang kích hoạt chế độ Rời Nhà ---");
        light.off();
        fan.off();
        ac.off();
    }

    public void sleepMode() {
        System.out.println("\n--- Đang kích hoạt chế độ Ngủ ---");
        light.off();
        ac.setTemperature(28);
        fan.setLowSpeed();
    }
}

// ==========================================
// PART 4: CHƯƠNG TRÌNH CHÍNH (Client)
// ==========================================

public class MainBai2SS8 {
    public static void main(String[] args) {
        // Khởi tạo các thành phần
        OldThermometer oldSensor = new OldThermometer();
        TemperatureSensor adapter = new ThermometerAdapter(oldSensor);

        Light light = new Light();
        Fan fan = new Fan();
        AirConditioner ac = new AirConditioner();

        // Khởi tạo Facade
        SmartHomeFacade smartHome = new SmartHomeFacade(light, fan, ac, adapter);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n-------------------------------------------");
            System.out.println("BẢNG ĐIỀU KHIỂN THÔNG MINH (ADAPTER & FACADE)");
            System.out.println("1. Xem nhiệt độ hiện tại (Dùng Adapter)");
            System.out.println("2. Chế độ Rời nhà (Dùng Facade)");
            System.out.println("3. Chế độ Ngủ (Dùng Facade)");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    smartHome.getCurrentTemperature();
                    break;
                case 2:
                    smartHome.leaveHome();
                    break;
                case 3:
                    smartHome.sleepMode();
                    break;
                case 4:
                    System.out.println("Kết thúc chương trình.");
                    running = false;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
        scanner.close();
    }
}


