package SS6.Bai3;

public class MainBai3SS6 {

    // 1. Lớp TicketPool quản lý vé của từng phòng
    static class TicketPool {
        private final String roomId;
        private int availableTickets;
        private int ticketCounter = 0; // Bộ đếm để in mã vé (A-001, A-002...)

        public TicketPool(String roomId, int initialTickets) {
            this.roomId = roomId;
            this.availableTickets = initialTickets;
        }

        // Bổ sung phương thức thêm vé mới (được gọi bởi TicketSupplier)
        public synchronized void addTickets(int count) {
            availableTickets += count;
        }

        // Phương thức bán vé
        public synchronized String sellTicket() {
            if (availableTickets > 0) {
                availableTickets--;
                ticketCounter++;
                return String.format("%s-%03d", roomId, ticketCounter);
            }
            return null; // Hết vé
        }

        public int getRemainingTickets() {
            return availableTickets;
        }

        public String getRoomId() {
            return roomId;
        }
    }

    // 2. Lớp TicketSupplier - Nhà cung cấp vé định kỳ
    static class TicketSupplier implements Runnable {
        private final TicketPool roomA;
        private final TicketPool roomB;
        private final int supplyCount;
        private final int interval;
        private final int rounds;

        public TicketSupplier(TicketPool roomA, TicketPool roomB, int supplyCount, int interval, int rounds) {
            this.roomA = roomA;
            this.roomB = roomB;
            this.supplyCount = supplyCount;
            this.interval = interval;
            this.rounds = rounds;
        }

        @Override
        public void run() {
            for (int i = 0; i < rounds; i++) {
                try {
                    // Ngủ định kỳ (ví dụ: 3 giây)
                    Thread.sleep(interval);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                // Sau khi tỉnh dậy, thêm vé vào cả 2 phòng
                roomA.addTickets(supplyCount);
                System.out.println(" Nhà cung cấp: Đã thêm " + supplyCount + " vé vào phòng A");

                roomB.addTickets(supplyCount);
                System.out.println(" Nhà cung cấp: Đã thêm " + supplyCount + " vé vào phòng B");
            }
        }
    }

    // 3. Lớp BookingCounter - Quầy bán vé
    static class BookingCounter implements Runnable {
        private final String counterName;
        private final TicketPool pool;
        private int ticketsSold = 0; // Theo dõi số vé đã bán được
        private final int totalAttempts; // Số lần thử bán vé

        public BookingCounter(String counterName, TicketPool pool, int totalAttempts) {
            this.counterName = counterName;
            this.pool = pool;
            this.totalAttempts = totalAttempts;
        }

        @Override
        public void run() {
            for (int i = 0; i < totalAttempts; i++) {
                String ticket = pool.sellTicket();
                if (ticket != null) {
                    ticketsSold++;
                    System.out.println(counterName + " đã bán vé " + ticket);
                }

                try {
                    // Nghỉ 500ms giữa mỗi lần thao tác để dễ quan sát log
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {}
            }
        }

        public int getTicketsSold() {
            return ticketsSold;
        }
    }

    // 4. Hàm main chạy chương trình
    public static void main(String[] args) {
        System.out.println("--- BẮT ĐẦU CHẠY HỆ THỐNG ---");

        // Khởi tạo phòng A và B với 5 vé ban đầu
        TicketPool roomA = new TicketPool("A", 5);
        TicketPool roomB = new TicketPool("B", 5);

        // Khởi tạo Nhà cung cấp: Thêm 3 vé, định kỳ 3000ms, chạy 2 vòng (tổng cộng thêm 6 vé mỗi phòng)
        Thread supplierThread = new Thread(new TicketSupplier(roomA, roomB, 3, 3000, 2));

        // Khởi tạo 2 Quầy bán vé: Mỗi quầy thử bán 20 lần
        BookingCounter counter1 = new BookingCounter("Quầy 1", roomA, 20);
        BookingCounter counter2 = new BookingCounter("Quầy 2", roomB, 20);

        Thread thread1 = new Thread(counter1);
        Thread thread2 = new Thread(counter2);

        // Bắt đầu chạy tất cả các luồng cùng lúc
        thread1.start();
        thread2.start();
        supplierThread.start();

        // Ép luồng main chờ 3 luồng kia chạy xong mới đi tiếp xuống dưới
        try {
            thread1.join();
            thread2.join();
            supplierThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Sau khi tất cả đã dừng, in báo cáo tổng kết
        System.out.println("\n--- KẾT THÚC CHƯƠNG TRÌNH ---");
        System.out.println("Quầy 1 bán được: " + counter1.getTicketsSold() + " vé");
        System.out.println("Quầy 2 bán được: " + counter2.getTicketsSold() + " vé");
        System.out.println("Vé còn lại phòng A: " + roomA.getRemainingTickets());
        System.out.println("Vé còn lại phòng B: " + roomB.getRemainingTickets());
    }
}