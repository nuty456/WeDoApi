package cz.wedo.api.definitions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * This test class provides tests for the 'API' enumeration. 
 * In particular, tests are provided for the 'valueOfLabel' method, which is used to retrieve the API environment corresponding to the given label.
 */
public class APITest {

    /**
     * This is a test method for 'valueOfLabel'. It ensures the method correctly fetches the enum value for a known label.
     */
    @Test
    public void testValueOfLabel_WhenLabelIsValid() {
        // Given a valid label for an API environment
        String label = "PROD";

        // When 'valueOfLabel' is called with this label
        API result = API.valueOfLabel(label);

        // Then the returned API environment is the one that matches the label
        assertEquals(API.PROD, result);
    }

    /**
     * This is another test method for 'valueOfLabel'. This time, it tests the case when the label is unknown
     * and should therefore return null as per the method specification.
     */
    @Test
    public void testValueOfLabel_WhenLabelIsInvalid() {
        // Given an invalid label for an API environment
        String label = "NOT_EXIST";

        // When 'valueOfLabel' is called with this label
        API result = API.valueOfLabel(label);

        // Then the returned API environment is null, as there's no matching label
        assertNull(result);
    }
}