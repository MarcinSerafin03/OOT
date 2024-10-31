package app;

import io.reactivex.rxjava3.schedulers.Schedulers;
import util.PhotoDownloader;
import util.PhotoProcessor;
import util.PhotoSerializer;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhotoCrawler {

    private static final Logger log = Logger.getLogger(PhotoCrawler.class.getName());

    private final PhotoDownloader photoDownloader;

    private final PhotoSerializer photoSerializer;

    private final PhotoProcessor photoProcessor;

    public PhotoCrawler() throws IOException {
        this.photoDownloader = new PhotoDownloader();
        this.photoSerializer = new PhotoSerializer("./photos");
        this.photoProcessor = new PhotoProcessor();
    }

    public void resetLibrary() throws IOException {
        photoSerializer.deleteLibraryContents();
    }

    public void downloadPhotoExamples() {
        try {
            photoDownloader.getPhotoExamples()
                   .subscribe(photoSerializer::savePhoto,
                            throwable -> log.log(Level.WARNING, "Could not download a photo", throwable));
        } catch (IOException e) {
            log.log(Level.WARNING, "Could not download a photo", e);
        }

    }

    public void downloadPhotosForQuery(String query) throws IOException {
        try {
            photoDownloader.searchForPhotos(query)
                    .take(10)
                    .subscribe(photoSerializer::savePhoto, throwable -> log.log(Level.WARNING, "Could not download a photo 3", throwable));
        } catch (InterruptedException e){
            log.log(Level.WARNING, "Could not download a photo 4", e);
        }
    }

    public void downloadPhotosForMultipleQueries(List<String> queries) {
        photoDownloader.searchForPhotos(queries).
                observeOn(Schedulers.io())
                .subscribe(photoSerializer::savePhoto,
                        throwable -> log.log(Level.WARNING, "Could not download a photo 5", throwable));
    }
}
