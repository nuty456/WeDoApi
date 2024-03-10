package cz.wedo.api.definitions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for testing Dimensions enum's valueOfLabel method
 */
public class DimensionsTest {

    /**
     * This test validates the correct enum value is returned for a provided label.
     */
    @Test
    public void testValueOfLabelCorrect() {
        Dimensions dimensions = Dimensions.valueOfLabel("label");
        assertEquals(Dimensions.LABEL, dimensions);
    }

    /**
     * This test validates the correct enum value is returned for a provided labela6.
     */
    @Test
    public void testValueOfLabela6Correct() {
        Dimensions dimensions = Dimensions.valueOfLabel("labela6");
        assertEquals(Dimensions.LABELA6, dimensions);
    }

    /**
     * This test verifies that null is returned for a non-existent label.
     */
    @Test
    public void testValueOfLabelNonExisting() {
        Dimensions dimensions = Dimensions.valueOfLabel("invalid_label");
        assertNull(dimensions);
    }

    /**
     * This test verifies that null is returned for a null label.
     */
    @Test
    public void testValueOfLabelNull() {
        Dimensions dimensions = Dimensions.valueOfLabel(null);
        assertNull(dimensions);
    }
}