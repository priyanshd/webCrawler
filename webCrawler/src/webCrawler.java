import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

class webCrawler {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the URL that you'd like to run the crawler on: ");
        String startingURL = scanner.nextLine();
        crawler(startingURL);

    }

    public static void crawler(String startingURL) {
        ArrayList<String> pendingURLs = new ArrayList<>();
        ArrayList<String> traversedURLs = new ArrayList<>();

        pendingURLs.add(startingURL);

        while (!pendingURLs.isEmpty() && traversedURLs.size() <= 100) {
            String urlString = pendingURLs.remove(0);
            if (!traversedURLs.contains(urlString)) {
                traversedURLs.add(urlString);
                System.out.println("Crawl " + urlString);

                for (String s : getSubURLs(urlString)) {
                    if (!traversedURLs.contains(s)) {
                        pendingURLs.add(s);
                    }
                }
            }
        }
    }

    public static ArrayList<String> getSubURLs(String urlString){

        ArrayList<String> list = new ArrayList<>();

        try {

            URL url = new URL(urlString);
            Scanner scanner = new Scanner(url.openStream());
            int current = 0;
            while (scanner.hasNext()) {

                String line = scanner.nextLine();
                current = line.indexOf("http:", current);
                while (current > 0) {
                    int endIndex = line.indexOf("\"", current);
                    if (endIndex > 0) {
                        list.add(line.substring(current, endIndex));
                        current = line.indexOf("http:", endIndex);
                    } else {
                        current = -1;
                    }
                }
            }
        }

        catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;
    }
}

