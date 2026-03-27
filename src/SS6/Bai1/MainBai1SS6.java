package SS6.Bai1;

public class MainBai1SS6 {
    public static void main(String[] args) {
        // Đổi giá trị này thành TRUE để xem cách sửa lỗi (Không bị treo), FALSE để xem Deadlock
        boolean SAFE_MODE = true;

        TicketPool roomA = new TicketPool("A", 5);
        TicketPool roomB = new TicketPool("B", 5);

        System.out.println("--- BẮT ĐẦU CHẠY (Chế độ An toàn: " + SAFE_MODE + ") ---");

        // Quầy 1: Truyền vào A trước, B sau
        Thread quay1 = new Thread(new BookingCounter("Quầy 1", roomA, roomB, SAFE_MODE));

        // Quầy 2: Truyền vào B trước, A sau (Cố tình ngược thứ tự)
        Thread quay2 = new Thread(new BookingCounter("Quầy 2", roomB, roomA, SAFE_MODE));

        quay1.start();
        quay2.start();
    }
}

// Tách riêng TicketPool ra
class TicketPool {
    private final String roomId;
    private int tickets;

    public TicketPool(String roomId, int tickets) {
        this.roomId = roomId;
        this.tickets = tickets;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean hasTicket() {
        return tickets > 0;
    }

    public String takeTicket() {
        if (hasTicket()) {
            tickets--;
            return roomId + "-00" + (10 - tickets); // Giả lập mã vé (vd: A-001)
        }
        return null;
    }
}

// Tách riêng BookingCounter ra
class BookingCounter implements Runnable {
    private final String counterName;
    private final TicketPool pool1;
    private final TicketPool pool2;
    private final boolean isSafeMode;

    public BookingCounter(String counterName, TicketPool pool1, TicketPool pool2, boolean isSafeMode) {
        this.counterName = counterName;
        this.pool1 = pool1;
        this.pool2 = pool2;
        this.isSafeMode = isSafeMode;
    }

    @Override
    public void run() {
        sellCombo();
    }

    private void sellCombo() {
        TicketPool lock1, lock2;

        // CƠ CHẾ CHỐNG DEADLOCK: Sắp xếp thứ tự khóa theo tên phòng (A < B)
        if (isSafeMode) {
            if (pool1.getRoomId().compareTo(pool2.getRoomId()) < 0) {
                lock1 = pool1; lock2 = pool2;
            } else {
                lock1 = pool2; lock2 = pool1;
            }
        } else {
            // CƠ CHẾ GÂY DEADLOCK: Khóa mù quáng theo thứ tự truyền vào
            lock1 = pool1; lock2 = pool2;
        }

        // Bắt đầu lấy khóa
        synchronized (lock1) {
            System.out.println(counterName + ": Đã khóa phòng " + lock1.getRoomId() + " - Đang lấy vé...");

            // Ép luồng ngủ một chút để đảm bảo luồng kia cũng kịp khóa phòng còn lại (chắc chắn xảy ra Deadlock nếu isSafeMode = false)
            try { Thread.sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }

            System.out.println(counterName + ": Đang chờ khóa phòng " + lock2.getRoomId() + "...");
            synchronized (lock2) {
                // Đã khóa được cả 2 phòng, tiến hành kiểm tra và bán vé
                if (pool1.hasTicket() && pool2.hasTicket()) {
                    String ticket1 = pool1.takeTicket();
                    String ticket2 = pool2.takeTicket();
                    System.out.println(  counterName + " bán combo THÀNH CÔNG: " + ticket1 + " & " + ticket2);
                } else {
                    System.out.println( counterName + " bán combo THẤT BẠI (Một trong hai phòng đã hết vé).");
                }
            }
        }
    }
}