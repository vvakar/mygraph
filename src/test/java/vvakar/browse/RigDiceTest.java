package vvakar.browse;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.*;
/**
 * @author vvakar
 *         Date: 1/27/15
 */
public class RigDiceTest {

    @Test
    public void testextractWords() {
        assertTrue(RigDice.extractWords("java whatever").contains("java"));
        assertTrue(RigDice.extractWords("java c++ whatever").contains("c++"));
        assertTrue(RigDice.extractWords("java c# whatever").contains("c#"));
        assertTrue(RigDice.extractWords("java F# whatever").contains("f#"));
        assertTrue(RigDice.extractWords(null).isEmpty());

    }

    @Test
    public void testExtractSalaryRegex() {
        assertEquals("[100k]", RigDice.extractSalary("asdfasdf 100k asdfasdf "));
        assertEquals("[100k - 200k]", RigDice.extractSalary("asdasdf 100k - 200k adfasdf"));
        assertEquals("[100100-200300]", RigDice.extractSalary("asdasdf 100100-200300 adfasdf"));
        assertEquals("[100,100-200,300]", RigDice.extractSalary("asdasdf 100,100-200,300 adfasdf"));
        assertEquals("[$100,100 - $200,300]", RigDice.extractSalary("asdasdf $100,100 - $200,300 adfasdf"));
        assertEquals(null, RigDice.extractSalary("sad 20000k fds"));
    }

    @Test
    public void testRemoveScriptTags() {
        assertEquals("abc", RigDice.removeScriptTags("a<script> sdfs  sdf sd s f sdf </script>b<script ffs ds> </script>c<script></script>"));
    }
}
