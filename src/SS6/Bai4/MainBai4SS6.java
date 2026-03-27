package SS6.Bai4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainBai4SS6 {

    // 1. Lớp Ticket đại diện cho một chiếc vé
    static class Ticket {
        private String ticketId;
        private String roomName;
        private boolean isSold;

        public Ticket(String ticketId, String roomName) {
            this.ticketId = ticketId;
            this.roomName = roomName;
            this.isSold = false; // Mặc định khi tạo ra là chưa bán
        }

        public String getTicketId() { return ticketId; }
        public boolean isSold() { return isSold; }
        public void setSold(boolean sold) { isSold = sold; }
    }

    // 2. Lớp TicketPool quản lý danh sách vé của một phòng
    static class TicketPool {
        private String roomName;
        private List<Ticket> tickets;

        public TicketPool(String roomName, int count) {
            this.roomName = roomName;
            tickets = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                // Tạo mã vé format dạng A-001, A-002...
                tickets.add(new Ticket(String.format("%s-%03d", roomName, i), roomName));
            }
        }

        // Từ khóa synchronized đảm bảo tại 1 thời điểm chỉ 1 quầy được lấy vé từ rổ này
        public synchronized Ticket sellTicket() {
            for (Ticket t : tickets) {
                if (!t.isSold()) {
                    t.setSold(true); // Đánh dấu đã bán
                    return t;        // Trả về vé lấy được
                }
            }
            return null; // Hết vé
        }

        // Hàm hỗ trợ kiểm tra xem phòng còn vé không
        public synchronized boolean hasTickets() {
            for (Ticket t : tickets) {
                if (!t.isSold()) return true;
            }
            return false;
        }

        // Hàm hỗ trợ đếm số vé còn lại
        public synchronized int getRemainingTickets() {
            int count = 0;
            for (Ticket t : tickets) {
                if (!t.isSold()) count++;
            }
            return count;
        }
    }

    // 3. Lớp BookingCounter đại diện cho Quầy bán vé (Thread)
    static class BookingCounter implements Runnable {
        private String counterName;
        private TicketPool roomA;
        private TicketPool roomB;
        private int soldCount;
        private Random random;

        public BookingCounter(String counterName, TicketPool roomA, TicketPool roomB) {
            this.counterName = counterName;
            this.roomA = roomA;
            this.roomB = roomB;
            this.soldCount = 0;
            this.random = new Random();
        }

        @Override
        public void run() {
            // Chạy cho đến khi cả 2 phòng đều hết vé
            while (roomA.hasTickets() || roomB.hasTickets()) {
                // Chọn ngẫu nhiên một phòng để bán
                TicketPool selectedPool = random.nextBoolean() ? roomA : roomB;
                Ticket ticket = selectedPool.sellTicket();

                // Nếu phòng vừa chọn tình cờ đã hết vé, ta nhảy sang phòng còn lại lấy vé
                if (ticket == null) {
                    selectedPool = (selectedPool == roomA) ? roomB : roomA;
                    ticket = selectedPool.sellTicket();
                }

                // Nếu lấy được vé thành công
                if (ticket != null) {
                    soldCount++;
                    System.out.println(counterName + " đã bán vé " + ticket.getTicketId());

                    // Ngủ một chút (50ms) để mô phỏng thời gian in vé cho khách
                    // Việc này cũng giúp 2 Thread nhường nhịn nhau, tránh việc 1 quầy bán cái vèo hết sạch vé
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }

        public int getSoldCount() {
            return soldCount;
        }
    }

    // 4. Hàm main chạy chương trình
    public static void main(String[] args) {
        // Khởi tạo rổ vé cho 2 phòng, mỗi phòng 10 vé
        TicketPool roomA = new TicketPool("A", 10);
        TicketPool roomB = new TicketPool("B", 10);

        // Khởi tạo 2 Quầy bán vé
        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, roomB);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomA, roomB);

        // Tạo 2 Thread
        Thread t1 = new Thread(counter1);
        Thread t2 = new Thread(counter2);

        // Bắt đầu chạy
        t1.start();
        t2.start();

        // Chờ cả 2 quầy bán xong hết sạch vé mới đi tiếp
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // In tổng kết
        System.out.println("--- Kết thúc chương trình ---");
        System.out.println("Quầy 1 bán được: " + counter1.getSoldCount() + " vé");
        System.out.println("Quầy 2 bán được: " + counter2.getSoldCount() + " vé");
        System.out.println("Vé còn lại phòng A: " + roomA.getRemainingTickets());
        System.out.println("Vé còn lại phòng B: " + roomB.getRemainingTickets());
    }
}