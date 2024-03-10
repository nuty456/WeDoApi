package cz.wedo.api.definitions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for the RequestType
 */
public class RequestTypeTest {

    /**
     * Test for the `valueOfLabel` method for case insensitive values in the RequestType class.
     */
    @Test
    public void valueOfLabelCaseInsensitiveTest() {
        assertEquals(RequestType.GET, RequestType.valueOfLabel("get"));
        assertEquals(RequestType.POST, RequestType.valueOfLabel("post"));
        assertEquals(RequestType.PUT, RequestType.valueOfLabel("put"));
        assertEquals(RequestType.DELETE, RequestType.valueOfLabel("delete"));
    }

    /**
     * Test for the `valueOfLabel` method for case sensitive values in the RequestType class.
     * Since the labels are set in lowercase we should get null when passing upper case labels
     */
    @Test
    public void valueOfLabelCaseSensitiveTest() {
        assertNull(RequestType.valueOfLabel("GET"));
        assertNull(RequestType.valueOfLabel("POST"));
        assertNull(RequestType.valueOfLabel("PUT"));
        assertNull(RequestType.valueOfLabel("DELETE"));
    }

    /**
     * Test for the `valueOfLabel` method for non-existent label in the RequestType class.
     *ince there is no label such as 'NonExistent', it should return null
     */
    @Test
    public void valueOfLabelNonExistentLabelTest() {
        assertNull(RequestType.valueOfLabel("NonExistent"));
    }

}