package vvakar.browse;

import com.google.common.base.Strings;
import com.google.common.io.LineReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author vvakar
 *         Date: 1/30/15
 */
public class Util {
    public static Set<String> getKeywords() {
        Set<String> set = new HashSet<String>();
        String s = "programming_keywords.txt";
        InputStream in;
        try {
            in = Thread.currentThread().getContextClassLoader().getResource(s).openStream();
            LineReader lineReader = new LineReader(new InputStreamReader(in));
            String line;
            while( (line = lineReader.readLine()) != null)
                if(!Strings.isNullOrEmpty(line.trim()))
                    set.add(line.toLowerCase());
            in.close();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

        return set;
    }

}
