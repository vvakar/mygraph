package vvakar.strings;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * @author vvakar
 *         Date: 1/4/15
 */
public class RegexTest {
    /**
     * Compare with java's regex.
     */
    private static boolean doRegex(String s, String r) {
        Pattern pattern = Pattern.compile(r);
        return Regex.findRegex(s, r) == pattern.matcher(s).find();
    }

    @Test
    public void test() {

        assertTrue(doRegex("abcdefg", "abc"));


        assertTrue(doRegex("abcdefg", "abc"));
        assertTrue(doRegex("abcdefg", "xyz"));
        assertTrue(doRegex("abcdefg", "def"));

        assertTrue(doRegex("abcdefg", "def."));
        assertTrue(doRegex("abcdefg", "cd..."));
        assertTrue(doRegex("abcdefg", "cd...."));

        assertTrue(doRegex("abcdefg", "d(e|x)"));
    }

    @Test
    public void test2() {
//                                             012345
        assertTrue(doRegex("abcdefg", "d(x|e)"));
        assertTrue(doRegex("abcdefg", "cd(ef|e)."));
        assertTrue(doRegex("abcdefg", "(a|b)cd....."));


        assertTrue(doRegex("abcdefg", "d(e*|x)"));
        assertTrue(doRegex("abcdefg", "d(x*|e)"));
        assertTrue(doRegex("abcdefg", "d(x*|e*)"));
        assertTrue(doRegex("abcdefg", "cd(ef*|e)."));
    }

    @Test
    public void testAsterisk() {
                                             //0123456789a
        assertTrue(doRegex("abcdefg", "(a|b*)cd..."));
        assertTrue(doRegex("abcdefg", "(a|b*)cd...."));

    }

    @Test
    public void testAsteriskWithGroup() {
                                           //  012345678
        assertTrue(doRegex("abcdefg", "ab(xy)*cd"));
        assertTrue(doRegex("abcdefg", "ab(xy)cd"));
    }
}
