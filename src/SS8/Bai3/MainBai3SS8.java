package SS8.Bai3;

import java.util.*;

// ==========================================
// 1. COMMAND INTERFACE
// ==========================================
interface Command {
    void execute();
    void undo();
}

// ==========================================
// 2. RECEIVERS (Các thiết bị thực tế)
// ==========================================
class Light {
    void on() { System.out.println("Đèn: Bật"); }
    void off() { System.out.println("Đèn: Tắt"); }
}

class AirConditioner {
    private int temperature = 25; // Nhiệt độ mặc định

    void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println("Điều hòa: Nhiệt độ = " + temperature);
    }

    int getTemperature() { return temperature; }
}

// ==========================================
// 3. CONCRETE COMMANDS
// ==========================================

// Lệnh bật đèn
class LightOnCommand implements Command {
    private Light light;
    public LightOnCommand(Light light) { this.light = light; }

    @Override
    public void execute() { light.on(); }

    @Override
    public void undo() { light.off(); }
}

// Lệnh tắt đèn
class LightOffCommand implements Command {
    private Light light;
    public LightOffCommand(Light light) { this.light = light; }

    @Override
    public void execute() { light.off(); }

    @Override
    public void undo() { light.on(); }
}

// Lệnh set nhiệt độ điều hòa (Có lưu trạng thái cũ để Undo)
class ACSetTemperatureCommand implements Command {
    private AirConditioner ac;
    private int prevTemperature;
    private int newTemperature;

    public ACSetTemperatureCommand(AirConditioner ac, int newTemp) {
        this.ac = ac;
        this.newTemperature = newTemp;
    }

    @Override
    public void execute() {
        prevTemperature = ac.getTemperature(); // Lưu lại nhiệt độ trước khi đổi
        ac.setTemperature(newTemperature);
    }

    @Override
    public void undo() {
        System.out.print("Undo: ");
        ac.setTemperature(prevTemperature);
    }
}

// ==========================================
// 4. INVOKER (Remote Control)
// ==========================================
class RemoteControl {
    private Map<Integer, Command> buttons = new HashMap<>();
    private Stack<Command> history = new Stack<>();

    public void setCommand(int slot, Command command) {
        buttons.put(slot, command);
        System.out.println("Hệ thống: Đã gán lệnh vào nút số " + slot);
    }

    public void pressButton(int slot) {
        if (buttons.containsKey(slot)) {
            Command cmd = buttons.get(slot);
            cmd.execute();
            history.push(cmd); // Lưu vào lịch sử để undo
        } else {
            System.out.println("Nút này chưa được gán lệnh!");
        }
    }

    public void pressUndo() {
        if (!history.isEmpty()) {
            Command lastCommand = history.pop();
            lastCommand.undo();
        } else {
            System.out.println("Không có lệnh nào để Undo.");
        }
    }
}

// ==========================================
// 5. CHƯƠNG TRÌNH CHÍNH
// ==========================================
public class MainBai3SS8 {
    public static void main(String[] args) {
        // Khởi tạo thiết bị
        Light livingRoomLight = new Light();
        AirConditioner samsungAC = new AirConditioner();

        // Khởi tạo Remote
        RemoteControl remote = new RemoteControl();

        System.out.println("===== THIẾT LẬP REMOTE =====");

        // 1. Gán nút 1: Bật đèn
        remote.setCommand(1, new LightOnCommand(livingRoomLight));

        // 2. Gán nút 2: Tắt đèn
        remote.setCommand(2, new LightOffCommand(livingRoomLight));

        // 3. Gán nút 3: Set điều hòa 26°C
        remote.setCommand(3, new ACSetTemperatureCommand(samsungAC, 26));

        System.out.println("\n===== VẬN HÀNH =====");

        System.out.print("Nhấn nút 1: "); remote.pressButton(1);
        System.out.print("Nhấn nút 2: "); remote.pressButton(2);

        System.out.print("Nhấn Undo: "); remote.pressUndo(); // Sẽ bật lại đèn

        System.out.print("Nhấn nút 3: "); remote.pressButton(3);
        System.out.print("Nhấn nút 3 (lần nữa lên 28°C): ");
        remote.setCommand(4, new ACSetTemperatureCommand(samsungAC, 28));
        remote.pressButton(4);

        System.out.print("Nhấn Undo: "); remote.pressUndo(); // Quay về 26°C
    }
}


