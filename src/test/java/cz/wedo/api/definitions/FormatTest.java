package cz.wedo.api.definitions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * The FormatTest class represents test class for Format Enum
 * It contains tests for `valueOfLabel` method
 */
public class FormatTest {
    /**
     *	Method: testValueOfLabelPDF
     * 	Tests whether valueOfLabel returns correct enum for "pdf" label
     * 	Expected result is Format.PDF
     */
    @Test
    public void testValueOfLabelPDF(){
        Format actual = Format.valueOfLabel("pdf");
        assertEquals(Format.PDF, actual, "valueOfLabel method does not return correct Format for 'pdf'");
    }

    /**
     *	Method: testValueOfLabelZPL
     * 	Tests whether valueOfLabel returns correct enum for "zpl" label
     * 	Expected result is Format.ZPL
     */
    @Test
    public void testValueOfLabelZPL(){
        Format actual = Format.valueOfLabel("zpl");
        assertEquals(Format.ZPL, actual, "valueOfLabel method does not return correct Format for 'zpl'");
    }

    /**
     *	Method: testValueOfLabelInvalid
     * 	Tests whether valueOfLabel returns null for invalid label
     * 	Expected result is null
     */
    @Test
    public void testValueOfLabelInvalid(){
        Format actual = Format.valueOfLabel("invalid");
        assertNull(actual, "valueOfLabel method does not return null for invalid label");
    }
}