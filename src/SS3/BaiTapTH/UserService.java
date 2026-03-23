package SS3.BaiTapTH;

import java.util.List;

public class UserService {
    public List<User> getVerifiedUsers(List<User> users) {
        return users.stream()
                .filter(User::verified)
                .toList();
    }

    public Tier classifyTier(long months) {
        if (months > 24) {
            return new Gold();
        } else if (months > 12) {
            return new Silver();
        } else {
            return new Bronze();
        }
    }
}
