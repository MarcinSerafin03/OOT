package controller;


import io.reactivex.rxjava3.schedulers.Schedulers;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import model.Gallery;
import model.Photo;
import org.pdfsam.rxjavafx.schedulers.JavaFxScheduler;
import util.PhotoDownloader;


public class GalleryController {


    private Gallery galleryModel;

    @FXML
    private TextField imageNameField;

    @FXML
    private ImageView imageView;

    @FXML
    private ListView<Photo> imagesListView;

    @FXML
    private TextField searchTextField;

    @FXML
    public void initialize() {
//        imagesListView.getSelectionModel().select(0);
        imagesListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Photo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    ImageView photoIcon = new ImageView(item.getPhotoData());
                    photoIcon.setPreserveRatio(true);
                    photoIcon.setFitHeight(50);
                    setGraphic(photoIcon);
                }
            }
        });

        imagesListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (oldValue != null) {
                    System.out.println("Unbinding " + oldValue.getName());
                    unbindSelectedPhoto(oldValue);
                }
                System.out.println("Binding " + newValue.getName());
                bindSelectedPhoto(newValue);
            }

        });

    }

    public void setModel(Gallery gallery) {
        this.galleryModel = gallery;
//        bindSelectedPhoto(gallery.getPhotos().get(0));
        imagesListView.setItems(gallery.getPhotos());
        imagesListView.getSelectionModel().select(0);
    }

    private void bindSelectedPhoto(Photo selectedPhoto) {
        imageNameField.textProperty().bindBidirectional(selectedPhoto.nameProperty());
        imageView.imageProperty().bind(selectedPhoto.photoDataProperty());
    }

    private void unbindSelectedPhoto(Photo selectedPhoto) {
        imageNameField.textProperty().unbindBidirectional(selectedPhoto.nameProperty());
    }

    @FXML
    public void searchButtonClicked(ActionEvent event) {
        PhotoDownloader photoDownloader = new PhotoDownloader();
        galleryModel.clear();
        setModel(galleryModel);
        photoDownloader.searchForPhotos(searchTextField.getText())
                .subscribeOn(Schedulers.io()).observeOn(JavaFxScheduler.platform()).subscribe(photo -> {
                    galleryModel.addPhoto(photo);
                    setModel(galleryModel);
                });
    }
}

