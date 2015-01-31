package vvakar.browse;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.oracle.javafx.jmx.json.JSONDocument;
import com.oracle.javafx.jmx.json.JSONFactory;
import com.oracle.javafx.jmx.json.JSONReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author vvakar
 *         Date: 1/27/15
 *
 * TODOs:
 *  - match term, not word i.e. "machine learning"
 *  - download full job repo - can we store snapshots?
 *  - build inverted index - keywords to job ads
 *  - keyword density reports by region
 *  - salary reports by region
 *  - ability to persist reports
 *  - WHY?
 *      - to learn more about NLP, string processing, etc
 *      - to incorporate lucene and learn about it, maybe even join the lucene project
 *      - to build some useful analytics data to pitch to recruiters as part of my business
 *      - to build a demoable mobile app that looks like an open-source project
 *
 */
public class RigDice {
    private static final String DOMAIN = "http://service.dice.com";
    private static final int MAX_PAGES = 3;
    private static final int TOP_N_KEYWORDS = 500;
    public static void main(String[]asdf) throws Exception {
        String skill = "java";
        String areaCode = "212";
        URL url = new URL(DOMAIN + "/api/rest/jobsearch/v1/simple.json?areacode=" + areaCode + "&text=" + URLEncoder.encode(skill));
        System.out.println("Processing results for " + url);

        List<? extends JobAd> jobsLinks = extractJobAds(url);
        Multiset<String> wordCounts = HashMultiset.create();

        for(JobAd jobAd : jobsLinks) {
            String page = url2String(jobAd.detailUrl);
            String salary = extractSalary(page);
            wordCounts.addAll(extractWords(removeScriptTags(page)));
            if(salary != null) System.out.println(salary + "  " + jobAd);
        }

        for(TopNItem word : getTopN(wordCounts)) System.out.println(word);
    }

    private static TopNItem[] getTopN(Multiset<String> wordCounts) {
        PriorityQueue<TopNItem> priorityQueue = new PriorityQueue<TopNItem>();
        for(Multiset.Entry<String> entry : wordCounts.entrySet()) {
            int count = entry.getCount();
            String elem = entry.getElement();
            priorityQueue.add(new TopNItem(elem, count));
            if(priorityQueue.size() > TOP_N_KEYWORDS) priorityQueue.poll();
        }

        TopNItem[] topNs = priorityQueue.toArray(new TopNItem[0]);
        Arrays.sort(topNs);
        return topNs;
    }

    private static Pattern REMOVE_SCRIPT_PATTERN = Pattern.compile("<script.*?/script>", Pattern.CASE_INSENSITIVE);
    static String removeScriptTags(String text) {
        return REMOVE_SCRIPT_PATTERN.matcher(text).replaceAll("");
    }

    private static Pattern WORDS_PATTERN = Pattern.compile("\\w*[+#]*", Pattern.CASE_INSENSITIVE);
    static Set<String> extractWords(String text) {
        Set<String> keywords = Util.getKeywords();
        Set<String> rets = new HashSet<String>();
        if(text == null) return rets;
        Matcher matcher = WORDS_PATTERN.matcher(text);
        while(matcher.find()) {
            String found = matcher.group().trim().toLowerCase();
            if(keywords.contains(found))
                rets.add(found);
        }
        return rets;
    }

    private static Pattern SALARY_PATTERN = Pattern.compile("(\\$?\\d{3},?\\d{3}\\s*-\\s*\\$?\\d{3},?\\d{3})|(\\$?([1-3][\\d][\\d]k?\\W*)?[1-3][\\d][\\d]k)", Pattern.CASE_INSENSITIVE);
    static String extractSalary(String text) {
        if(text == null) return null;
        Matcher matcher = SALARY_PATTERN.matcher(text);
        Set<String> rets = new HashSet<String>();
        while(matcher.find()) rets.add(matcher.group().trim());
        if(rets.isEmpty()) return null;
        else return rets.toString();
    }

    private static String url2String(URL url) throws Exception {
        InputStream stream = url.openStream();
        Scanner scanner = null;
        String txt = "";
        try {
            scanner = new Scanner(stream).useDelimiter("\\A");
            if (scanner.hasNext()) txt = scanner.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(scanner != null) scanner.close();
            if(stream != null) stream.close();
        }

        return txt;
    }

    private static List<JobAd> extractJobAds(URL landing) throws Exception {
        final List<JobAd> jobAds = new LinkedList<JobAd>();
        final JSONFactory factory = JSONFactory.instance();
        URL url = landing;
        String nextUrl;

        int i = 0;
        do {
            System.out.println("PROCESSING PAGE " + i);
            JSONReader reader = factory.makeReader(new BufferedReader(new InputStreamReader(url.openStream())));
            JSONDocument doc = reader.build();

            for (Object obj : doc.getList("resultItemList")) {
                JSONDocument job = (JSONDocument) obj;
                String title = job.getString("jobTitle");
                String company = job.getString("company");
                String location = job.getString("location");
                String detailUrl = job.getString("detailUrl");
                jobAds.add(new JobAd(title, company, location, detailUrl));
            }
            nextUrl = doc.getString("nextUrl");
            url = new URL(DOMAIN + nextUrl);
        } while(++i < MAX_PAGES && nextUrl != null);

        return jobAds;
    }


    private static final class JobAd {
        public final String title, company, location;
        public final URL detailUrl;
        JobAd(String title, String company, String location, String detailUrl) throws Exception {
            this.title = title; this.company = company; this.location = location; this.detailUrl = new URL(detailUrl);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            JobAd jobAd = (JobAd) o;

            if (detailUrl != null ? !detailUrl.equals(jobAd.detailUrl) : jobAd.detailUrl != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return detailUrl != null ? detailUrl.hashCode() : 0;
        }

        @Override
        public String toString() {
            return title + " @ " + company + " in " + location + " " + detailUrl;
        }
    }

    private static class TopNItem implements Comparable<TopNItem>{
        String word;
        int occurrences;

        TopNItem(String elem, int count) { this.word = elem; this.occurrences = count; }
        @Override
        public int compareTo(TopNItem o) {
            return Integer.compare(occurrences, o.occurrences);
        }

        @Override
        public String toString() {
            return word + " -> " + occurrences;
        }
    }
}
