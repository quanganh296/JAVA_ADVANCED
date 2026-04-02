package SS3.BaiTapTH;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {
    private UserService userService;
    private List<User> testUsers;

    @BeforeEach
    void setUp() {
        userService = new UserService();
        testUsers = new ArrayList<>();
        testUsers.add(new User("U001", "john@example.com", "pass123", true, LocalDate.of(2024, 1, 1)));
        testUsers.add(new User("U002", "jane@example.com", "pass456", false, LocalDate.of(2024, 2, 1)));
        testUsers.add(new User("U003", "bob@example.com", "pass789", true, LocalDate.of(2024, 3, 1)));
        testUsers.add(new User("U004", "alice@example.com", "pass000", false, LocalDate.of(2024, 4, 1)));
        testUsers.add(new User("U005", "charlie@example.com", "pass111", true, LocalDate.of(2024, 5, 1)));
    }

    @Test
    void getVerifiedUsersFiltersVerifiedUsers() {
        List<User> verified = userService.getVerifiedUsers(testUsers);
        assertEquals(3, verified.size());
    }

    @Test
    void getVerifiedUsersContainsOnlyTrueVerifiedUsers() {
        List<User> verified = userService.getVerifiedUsers(testUsers);
        for (User user : verified) {
            assertTrue(user.verified());
        }
    }

    @Test
    void getVerifiedUsersExcludesUnverifiedUsers() {
        List<User> verified = userService.getVerifiedUsers(testUsers);
        assertFalse(verified.stream().anyMatch(u -> u.id().equals("U002")));
        assertFalse(verified.stream().anyMatch(u -> u.id().equals("U004")));
    }

    @Test
    void getVerifiedUsersEmptyListWhenNoVerified() {
        List<User> unverifiedUsers = new ArrayList<>();
        unverifiedUsers.add(new User("U001", "john@example.com", "pass123", false, LocalDate.now()));
        unverifiedUsers.add(new User("U002", "jane@example.com", "pass456", false, LocalDate.now()));
        
        List<User> verified = userService.getVerifiedUsers(unverifiedUsers);
        assertTrue(verified.isEmpty());
    }

    @Test
    void classifyTierGoldForMonthsGreaterThan24() {
        Tier tier = userService.classifyTier(25);
        assertInstanceOf(Gold.class, tier);
        assertEquals("Gold", tier.toString());
    }

    @Test
    void classifyTierGoldFor24Months() {
        Tier tier = userService.classifyTier(24);
        assertInstanceOf(Gold.class, tier);
    }

    @Test
    void classifyTierSilverForMonthsGreaterThan12() {
        Tier tier = userService.classifyTier(18);
        assertInstanceOf(Silver.class, tier);
        assertEquals("Silver", tier.toString());
    }

    @Test
    void classifyTierSilverFor12Months() {
        Tier tier = userService.classifyTier(12);
        assertInstanceOf(Bronze.class, tier);
    }

    @Test
    void classifyTierBronzeForMonthsLessThanOrEqual12() {
        Tier tier = userService.classifyTier(12);
        assertInstanceOf(Bronze.class, tier);
        assertEquals("Bronze", tier.toString());
    }

    @Test
    void classifyTierBronzeForZeroMonths() {
        Tier tier = userService.classifyTier(0);
        assertInstanceOf(Bronze.class, tier);
    }

    @Test
    void classifyTierBronzeForOneMonth() {
        Tier tier = userService.classifyTier(1);
        assertInstanceOf(Bronze.class, tier);
    }

    @Test
    void classifyTierSilverForThirteenMonths() {
        Tier tier = userService.classifyTier(13);
        assertInstanceOf(Silver.class, tier);
    }

    @Test
    void classifyTierGoldForHighMonths() {
        Tier tier = userService.classifyTier(100);
        assertInstanceOf(Gold.class, tier);
    }
}

