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
import java.util.ArrayList;
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
 */
public class RigDice {
    private static final String DOMAIN = "http://service.dice.com";
    private static final int MAX_PAGES = 1;
    private static final int TOP_N = 100;
    public static void main(String[]asdf) throws Exception {
        String skill = "python";
        URL url = new URL(DOMAIN + "/api/rest/jobsearch/v1/simple.json?areacode=212&skill=" + URLEncoder.encode(skill));


        List<Job> jobsLinks = doc2Links(url);
        Multiset<String> wordCounts = HashMultiset.create();

        for(Job job : jobsLinks) {
            String page = url2String(job.detailUrl);
            String salary = extractSalary(page);
            wordCounts.addAll(extractWords(page));
            if(salary != null) System.out.println(salary + "  " + job);
        }

        for(TopNItem word : getTopN(wordCounts, jobsLinks.size())) System.out.println(word);
    }

    private static TopNItem[] getTopN(Multiset<String> wordCounts, int samples) {
        PriorityQueue<TopNItem> priorityQueue = new PriorityQueue<TopNItem>();
        for(Multiset.Entry<String> entry : wordCounts.entrySet()) {
            int count = entry.getCount();
            if(count == samples) continue; // words that appear in every single item are probably stopwords

            String elem = entry.getElement();
            priorityQueue.add(new TopNItem(elem, count));
            if(priorityQueue.size() > TOP_N) priorityQueue.poll();
        }

        return priorityQueue.toArray(new TopNItem[0]);
    }

    private static Pattern WORDS_PATTERN = Pattern.compile("\\w*", Pattern.CASE_INSENSITIVE);
    static Set<String> extractWords(String text) {
        Set<String> rets = new HashSet<String>();
        if(text == null) return rets;
        Matcher matcher = WORDS_PATTERN.matcher(text);
        while(matcher.find()) rets.add(matcher.group().trim().toLowerCase());
        return rets;
    }

    private static Pattern SALARY_PATTERN = Pattern.compile("(\\d{6}-\\d{6})|(\\$?([1-3][\\d][\\d]k?\\W*)?[1-3][\\d][\\d]k)", Pattern.CASE_INSENSITIVE);
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

    private static List<Job> doc2Links(URL landing) throws Exception {
        final List<Job> jobs = new LinkedList<Job>();
        final JSONFactory factory = JSONFactory.instance();
        URL url = landing;
        String nextUrl = null;

        int i = 0;
        do {
            System.out.println("PROCESSING PAGE " + i);
            JSONReader reader = factory.makeReader(new BufferedReader(new InputStreamReader(url.openStream())));
            JSONDocument doc = reader.build();

            for (Object obj : doc.getList("resultItemList")) {
                JSONDocument job = (JSONDocument) obj;
                String title = job.getString("jobTitle");
                String company = job.getString("company");
                String detailUrl = job.getString("detailUrl");
                jobs.add(new Job(title, company, detailUrl));
            }
            nextUrl = doc.getString("nextUrl");
            url = new URL(DOMAIN + nextUrl);
        } while(++i < MAX_PAGES && nextUrl != null);

        return jobs;
    }


    private static final class Job {
        public final String title, company;
        public final URL detailUrl;
        Job(String title, String company, String detailUrl) throws Exception {
            this.title = title; this.company = company;this.detailUrl = new URL(detailUrl);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Job job = (Job) o;

            if (detailUrl != null ? !detailUrl.equals(job.detailUrl) : job.detailUrl != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return detailUrl != null ? detailUrl.hashCode() : 0;
        }

        @Override
        public String toString() {
            return title + " @ " + company + " " + detailUrl;
        }
    }

    private static Pattern JOBS_PATTERN = Pattern.compile("https://www.dice.com/jobs/.*?\"");
    static List<URL> text2Links(String text) throws Exception {
        List<URL> retVal = new ArrayList<URL>();
        Matcher matcher = JOBS_PATTERN.matcher(text);

        while(matcher.find()) {
            String url = matcher.group();
            retVal.add(new URL(url.substring(0, url.length() - 1))); // would prefer a regex that can skip ending double quote but this works
        }

        return retVal;
    }

    private static class TopNItem implements Comparable<TopNItem>{
        String word;
        int occurrences;

        TopNItem(String elem, int count) { this.word = elem; this.occurrences = count; }
        @Override
        public int compareTo(TopNItem o) {
            return -Integer.compare(occurrences, o.occurrences);
        }

        @Override
        public String toString() {
            return word + " -> " + occurrences;
        }
    };



}
