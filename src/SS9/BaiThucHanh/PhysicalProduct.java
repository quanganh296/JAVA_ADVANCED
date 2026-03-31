package SS9.BaiThucHanh;

public class PhysicalProduct extends Product {
    private double weight; // Thuộc tính riêng của sản phẩm vật lý

    public PhysicalProduct(String id, String name, double price, double weight) {
        super(id, name, price);
        this.weight = weight;
    }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    @Override
    public void displayInfo() { //
        System.out.println("Physical Product - ID: " + id + ", Name: " + name +
                ", Price: $" + price + ", Weight: " + weight + " kg");
    }
}