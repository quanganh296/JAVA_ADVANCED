package SS9.BaiThucHanh;

public class DigitalProduct extends Product {
    private double sizeInMB; // Thuộc tính riêng của sản phẩm kỹ thuật số

    public DigitalProduct(String id, String name, double price, double sizeInMB) {
        super(id, name, price);
        this.sizeInMB = sizeInMB;
    }

    public double getSizeInMB() { return sizeInMB; }
    public void setSizeInMB(double sizeInMB) { this.sizeInMB = sizeInMB; }

    @Override
    public void displayInfo() { //
        System.out.println("Digital Product - ID: " + id + ", Name: " + name +
                ", Price: $" + price + ", Size: " + sizeInMB + " MB");
    }
}