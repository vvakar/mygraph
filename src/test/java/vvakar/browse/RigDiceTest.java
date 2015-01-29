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
    public void testCanParseLinks() throws Exception {
        List<URL> links =  RigDice.text2Links("\n" +
                        "\t        \t\t\t\t\t<div class='mTB2'>\n" +
                        "\t        \t\t\t\t\t\t<a href='#' class='m' onclick='javascript:displayfullcontent(this);javascript:return false;'>more</a>\n" +
                        "\t        \t\t\t\t\t</div>\n" +
                        "        \t\t\t\t\t</div>\n" +
                        "\t\t\t\t\t \t\t\t\t\t\t<input type=\"checkbox\" class=\"fchk\" chkTyp=\"djt\" chkVal=\"Software+Engineer\" />\n" +
                        "\t\t\t\t\t \t\t\t\t<a href=\"https://www.dice.com/jobs/q-java-l-New+York%2C+NY-djt-Software+Engineer-jobs.html\">Software Engineer</a>\n" +
                        "\t\t\t\t\t \t\t\t\t\t\t<input type=\"checkbox\" class=\"fchk\" chkTyp=\"djt\" chkVal=\"Android+Developer\" />\n" +
                        "\t\t\t\t\t \t\t\t\t<a href=\"https://www.dice.com/jobs/q-java-l-New+York%2C+NY-djt-Android+Developer-jobs.html\">Android Developer</a>\n" +
                        "\t\t\t\t\t \t\t\t\t\t\t<input type=\"checkbox\" class=\"fchk\" chkTyp=\"djt\" chkVal=\"Java+Architect\" />\n" +
                        "\t\t\t\t\t \t\t\t\t<a href=\"https://www.dice.com/jobs/q-java-l-New+York%2C+NY-djt-Java+Architect-jobs.html\">Java Architect</a>\n" +
                        "\t\t\t\t\t \t\t\t</div>\n");

        assertEquals(ImmutableList.of(
                new URL("https://www.dice.com/jobs/q-java-l-New+York%2C+NY-djt-Software+Engineer-jobs.html"),
                new URL("https://www.dice.com/jobs/q-java-l-New+York%2C+NY-djt-Android+Developer-jobs.html"),
                new URL("https://www.dice.com/jobs/q-java-l-New+York%2C+NY-djt-Java+Architect-jobs.html")
                ),links);

    }

    @Test
    public void testExtractSalaryRegex() {
        assertEquals("100k", RigDice.extractSalary("asdfasdf 100k asdfasdf "));
        assertEquals("100k - 200k", RigDice.extractSalary("asdasdf 100k - 200k adfasdf"));
        assertEquals("100100-200300", RigDice.extractSalary("asdasdf 100100-200300 adfasdf"));
        assertEquals(null, RigDice.extractSalary("sad 20000k fds"));
    }

}
