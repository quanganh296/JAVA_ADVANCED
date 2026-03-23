package SS3.BaiTapTH;

@FunctionalInterface
public interface Authenticatable {
    String getPassword();

    default boolean isAuthenticated() {
        return getPassword() != null && !getPassword().isEmpty();
    }

    static String encrypt(String rawPassword) {
        // Mock encryption
        return "encrypted:" + rawPassword;
    }
}
