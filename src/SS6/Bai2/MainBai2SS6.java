package SS6.Bai2;

public class MainBai2SS6 {

    // 1. Các class hỗ trợ (để static là rất chuẩn)
    static class TicketPool {
        private final String roomId;
        private int tickets;
        private int ticketCounter = 0;

        public TicketPool(String roomId, int initialTickets) {
            this.roomId = roomId;
            this.tickets = initialTickets;
        }

        public synchronized String sellTicket(String counterName) {
            while (tickets <= 0) {
                System.out.println(counterName + ": Hết vé phòng " + roomId + ", đang chờ...");
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Luồng bị ngắt khi đang chờ.");
                    return null;
                }
            }

            tickets--;
            ticketCounter++;
            return String.format("%s-%03d", roomId, ticketCounter);
        }

        public synchronized void addTickets(int amount, String supplierName) {
            tickets += amount;
            System.out.println(supplierName + ": Đã thêm " + amount + " vé vào phòng " + roomId);
            notifyAll();
        }
    }

    static class BookingCounter implements Runnable {
        private final String counterName;
        private final TicketPool pool;
        private final int ticketsToSell;

        public BookingCounter(String counterName, TicketPool pool, int ticketsToSell) {
            this.counterName = counterName;
            this.pool = pool;
            this.ticketsToSell = ticketsToSell;
        }

        @Override
        public void run() {
            for (int i = 0; i < ticketsToSell; i++) {
                String ticket = pool.sellTicket(counterName);
                if (ticket != null) {
                    System.out.println(counterName + " bán vé " + ticket);
                }
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            }
        }
    }

    static class TicketSupplier implements Runnable {
        private final TicketPool pool;

        public TicketSupplier(TicketPool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                pool.addTickets(3, "Nhà cung cấp");
            } catch (InterruptedException ignored) {}
        }
    }

    // 2. Hàm MAIN đặt trực tiếp ở class MainBai2SS6
    public static void main(String[] args) {
        System.out.println("--- HỆ THỐNG BÁN VÉ THÔNG MINH BẮT ĐẦU ---");

        // Khởi tạo phòng A chỉ có 2 vé ban đầu
        TicketPool roomA = new TicketPool("A", 2);

        // Khởi tạo Quầy 1 bán 5 vé phòng A
        Thread quay1 = new Thread(new BookingCounter("Quầy 1", roomA, 5));

        // Khởi tạo Nhà cung cấp vé (bơm thêm vé sau 3 giây)
        Thread nhaCungCap = new Thread(new TicketSupplier(roomA));

        // Bắt đầu chạy các luồng
        quay1.start();
        nhaCungCap.start();
    }
}