package SS3.BaiTapTH;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PublicUserTest {

    @Test
    void publicUserContainsIdEmailAndTier() {
        Tier tier = new Gold();
        PublicUser publicUser = new PublicUser("U001", "user@example.com", tier);
        
        assertEquals("U001", publicUser.id());
        assertEquals("user@example.com", publicUser.email());
        assertEquals(tier, publicUser.tier());
    }

    @Test
    void publicUserDoesNotContainPassword() {
        Tier tier = new Silver();
        PublicUser publicUser = new PublicUser("U001", "user@example.com", tier);
        
        assertFalse(publicUser.toString().contains("password"));
    }

    @Test
    void publicUserWithDifferentTiers() {
        PublicUser userGold = new PublicUser("U001", "user1@example.com", new Gold());
        PublicUser userSilver = new PublicUser("U002", "user2@example.com", new Silver());
        PublicUser userBronze = new PublicUser("U003", "user3@example.com", new Bronze());
        
        assertInstanceOf(Gold.class, userGold.tier());
        assertInstanceOf(Silver.class, userSilver.tier());
        assertInstanceOf(Bronze.class, userBronze.tier());
    }

    @Test
    void publicUserIdAndEmailAreAccessible() {
        PublicUser publicUser = new PublicUser("U001", "test@example.com", new Bronze());
        
        assertTrue(publicUser.id().equals("U001"));
        assertTrue(publicUser.email().equals("test@example.com"));
    }
}

