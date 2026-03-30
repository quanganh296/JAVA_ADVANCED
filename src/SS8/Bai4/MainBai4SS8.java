package SS8.Bai4;
import java.util.ArrayList;
import java.util.List;





    // 1. Giao diện Observer
    interface Observer {
        void update(int temperature);
    }

    // 2. Giao diện Subject
    interface Subject {
        void attach(Observer o);
        void detach(Observer o);
        void notifyObservers();
    }

    // 3. Concrete Observer: Quạt
    class Fan implements Observer {
        public Fan() {
            System.out.println("Quạt: Đã đăng ký nhận thông báo");
        }

        @Override
        public void update(int temperature) {
            if (temperature < 20) {
                System.out.println("Quạt: Nhiệt độ thấp, tự động TẮT");
            } else if (temperature >= 20 && temperature <= 25) {
                System.out.println("Quạt: Nhiệt độ bình thường, chạy tốc độ trung bình");
            } else {
                System.out.println("Quạt: Nhiệt độ cao, chạy tốc độ mạnh");
            }
        }
    }

    // 4. Concrete Observer: Máy tạo ẩm
    class Humidifier implements Observer {
        public Humidifier() {
            System.out.println("Máy tạo ẩm: Đã đăng ký");
        }

        @Override
        public void update(int temperature) {
            System.out.println("Máy tạo ẩm: Điều chỉnh độ ẩm cho nhiệt độ " + temperature);
        }
    }

    // 5. Concrete Subject: Cảm biến nhiệt độ
    class TemperatureSensor implements Subject {
        private List<Observer> observers;
        private int temperature;

        public TemperatureSensor() {
            observers = new ArrayList<>();
        }

        @Override
        public void attach(Observer o) {
            if (!observers.contains(o)) {
                observers.add(o);
            }
        }

        @Override
        public void detach(Observer o) {
            observers.remove(o);
        }

        @Override
        public void notifyObservers() {
            for (Observer o : observers) {
                o.update(this.temperature);
            }
        }

        public void setTemperature(int temperature) {
            this.temperature = temperature;
            System.out.println("\nCảm biến: Nhiệt độ = " + temperature);
            notifyObservers(); // Tự động thông báo khi nhiệt độ đổi
        }
    }

    // 6. Chương trình chính
    public class MainBai4SS8 {
        public static void main(String[] args) {
            // Khởi tạo Subject (Cảm biến)
            TemperatureSensor sensor = new TemperatureSensor();

            // Khởi tạo và đăng ký các Observer (Thiết bị)
            Fan fan = new Fan();
            Humidifier humidifier = new Humidifier();

            sensor.attach(fan);
            sensor.attach(humidifier);

            // Giả lập kịch bản thay đổi nhiệt độ
            sensor.setTemperature(18);
            sensor.setTemperature(26);
        }
    }

