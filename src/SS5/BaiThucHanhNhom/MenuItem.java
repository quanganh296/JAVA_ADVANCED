package SS5.BaiThucHanhNhom;

public class MenuItem {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String category;

    // Constructors
    public MenuItem() {}

    public MenuItem(int id, String name, double price, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    @Override
    public String toString() {
        return String.format("| %-5d | %-20s | %-10.2f | %-8d | %-12s |",
                id, name, price, quantity, category);
    }
}