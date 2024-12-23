package app;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class CrawlerApp {

    public static final String SCRAPER_API_KEY = "fef0f2cdd0a7445156cc60f5e45ee5bf";

    private static final List<String> TOPICS = List.of("Doctor who", "Agent Cooper", "Sherlock", "Poirot", "Detective Monk");


    public static void main(String[] args) throws IOException, InterruptedException {
        PhotoCrawler photoCrawler = new PhotoCrawler();
        photoCrawler.resetLibrary();
//        photoCrawler.downloadPhotoExamples();
//        photoCrawler.downloadPhotosForQuery(TOPICS.get(0));
        photoCrawler.downloadPhotosForMultipleQueries(TOPICS);
        Thread.sleep(100_000);
    }
}