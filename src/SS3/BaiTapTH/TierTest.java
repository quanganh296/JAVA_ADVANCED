package SS3.BaiTapTH;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TierTest {

    @Test
    void goldTierToStringReturnsGold() {
        Gold gold = new Gold();
        assertEquals("Gold", gold.toString());
    }

    @Test
    void silverTierToStringReturnsSilver() {
        Silver silver = new Silver();
        assertEquals("Silver", silver.toString());
    }

    @Test
    void bronzeTierToStringReturnsBronze() {
        Bronze bronze = new Bronze();
        assertEquals("Bronze", bronze.toString());
    }

    @Test
    void tierNameIsSetInConstructor() {
        Tier gold = new Gold();
        Tier silver = new Silver();
        Tier bronze = new Bronze();
        
        assertNotNull(gold.toString());
        assertNotNull(silver.toString());
        assertNotNull(bronze.toString());
    }

    @Test
    void differentTiersHaveDifferentNames() {
        String goldName = new Gold().toString();
        String silverName = new Silver().toString();
        String bronzeName = new Bronze().toString();
        
        assertNotEquals(goldName, silverName);
        assertNotEquals(silverName, bronzeName);
        assertNotEquals(goldName, bronzeName);
    }
}

