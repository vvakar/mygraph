package vvakar.strings;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @author vvakar
 *         Date: 12/30/14
 */
public class KMPTest {
    @Test
    public void test() {

        assertEquals(0, KMP.findAsciiCaseInsensitive("", ""));
        assertEquals(0, KMP.findAsciiCaseInsensitive("abc", ""));
        assertEquals(-1, KMP.findAsciiCaseInsensitive("", "abc"));
        assertEquals(2, KMP.findAsciiCaseInsensitive("abcdefgh", "cd"));
        assertEquals(-1, KMP.findAsciiCaseInsensitive("abcdefgh", "xyz"));
        assertEquals(10, KMP.findAsciiCaseInsensitive("cdababdcabababcdef", "ababc"));
    }
}
