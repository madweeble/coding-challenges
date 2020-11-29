package rigup;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Authors {
    private static final String ADDRESS = "https://jsonmock.hackerrank.com/api/article_users?page=";
    private static final long COUNT = 10;

    public static void main(String[] args) {
        List<String> authors = new ArrayList<>();

        try {
            for (int pageNum = 1; pageNum <= getTotalPages(); pageNum++) {
                authors.addAll(getAuthors(pageNum));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        System.out.println(authors);
    }

    /**
     * Get a list of authors over the threshold
     *
     * @param currPage the JSON returned from the API call
     * @return List of authors
     */
    private static List<String> getAuthors(final int currPage) throws IOException, ParseException {
        List<String> authors = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(readPage(currPage));
        JSONArray jsonData = (JSONArray) jsonObject.get("data");
        for (int i = 0; i < jsonData.size(); i++) {
            JSONObject dataObject = (JSONObject) jsonData.get(i);
            System.out.println(dataObject.get("username") + ": " + dataObject.get("submission_count"));
            if ((long) dataObject.get("submission_count") >= COUNT) {
                authors.add((String) dataObject.get("username"));
            }
        }
        return authors;
    }

    /**
     * Get the total number of pages count from the first page
     *
     * @return the total_count attribute
     * @throws ParseException
     */
    private static int getTotalPages() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(readPage(1));
        long pages = (long) jsonObject.get("total_pages");
        return (int) pages;
    }

    /**
     * Read each page from the API using a GET request
     *
     * @param pageNumber current page number
     * @return the JSON contents as a string
     */
    public static String readPage(final int pageNumber) throws IOException {
        StringBuilder inline = new StringBuilder();

        URL url = new URL(ADDRESS + pageNumber);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            inline.append(scanner.nextLine());
        }
        scanner.close();

        return inline.toString();
    }
}
