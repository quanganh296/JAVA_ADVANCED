package SS9.BaiThucHanh;

public class ProductFactory {
    // Dựa vào tham số để trả về đối tượng tương ứng
        public static Product createProduct(String type, String id, String name, double price, double specificAttribute) { //
            if (type.equals("1")) { //
                return new PhysicalProduct(id, name, price, specificAttribute);
            } else if (type.equals("2")) { //
                return new DigitalProduct(id, name, price, specificAttribute);
            }
            return null;
        }
    }

