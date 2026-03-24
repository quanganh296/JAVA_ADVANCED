package SS4.bai4;

public class PasswordValidator {

    public String evaluatePasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            return "Yếu";
        }

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        // Đếm số lượng tiêu chí đạt được
        int criteriaMet = 0;
        if (hasUpper) criteriaMet++;
        if (hasLower) criteriaMet++;
        if (hasDigit) criteriaMet++;
        if (hasSpecial) criteriaMet++;

        if (criteriaMet == 4) {
            return "Mạnh";
        } else if (criteriaMet == 3) {
            return "Trung bình";
        } else {
            return "Yếu";
        }
    }
}