package com.example.mediaplayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {
    public AnchorPane player;
    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton, pauseButton, nextButton, previousButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;


    private Media media;
    private MediaPlayer mediaPlayer;


    private File directory;
    private File[] files;

    private ArrayList<File> songs;

    private int songNumber;

    private Timer timer;
    private TimerTask task;
    private boolean running;
    @FXML
    private ImageView logoImageView, mainImageView, playlistsImageView;


    public void handleMainImageView() throws IOException {
        FXMLLoader root = new FXMLLoader(HelloApplication.class.getResource("Playlists.fxml"));

        Stage window = (Stage) mainImageView.getScene().getWindow();
        window.setScene(new Scene(root.load(),825, 790));
    }



    @FXML
    private TableColumn<User, String> artist;
    @FXML
    private TextField filterField;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableView<User> table;
    @FXML
    private TableColumn<User, String> time;

    ObservableList<User> list = FXCollections.observableArrayList(
            new User("I Bet You Look Good On The Dance Floor", "Arctic Monkeys", "2:56"),
            new User("why'd you only call me when you're high", "Arctic Monkeys", "2:41"),
            new User("girl crush", "Harry Styles", "4:03"),
            new User("summertime", "My Chemical Romance", "4:06")
    );
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        name.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        artist.setCellValueFactory(new PropertyValueFactory<User, String>("artist"));
        time.setCellValueFactory(new PropertyValueFactory<User, String>("time"));

        table.setItems(list);

        songs = new ArrayList<File>();
        directory = new File("music");
        files = directory.listFiles();

        if(files != null) {
            for(File file: files){
                songs.add(file);
            }
        }
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songs.get(songNumber).getName());

        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });
        songProgressBar.setStyle("-fx-accent:#004cd4");
        File logoFile = new File("images/Component 3.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

        File mainFile = new File("images/Component 4.png");
        Image mainImage = new Image(mainFile.toURI().toString());
        mainImageView.setImage(mainImage);

        File playlistsFile = new File("images/Component 5.png");
        Image playlistsImage = new Image(playlistsFile.toURI().toString());
        playlistsImageView.setImage(playlistsImage);
    }


    public void playMedia(){
        beginTimer();
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        mediaPlayer.play();
    }

    public void pauseMedia(){
        endTimer();
        mediaPlayer.pause();
    }

    public void nextMedia(){
        if(songNumber < songs.size() - 1) {
            songNumber++;
            mediaPlayer.stop();

            if(running){
                endTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        }
        else {
            songNumber = 0;
            mediaPlayer.stop();

            if(running){
                endTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        }
    }

    public void previousMedia() {

        if(songNumber > 0) {

            songNumber--;

            mediaPlayer.stop();

            if(running) {

                endTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());

            playMedia();
        }
        else {

            songNumber = songs.size() - 1;

            mediaPlayer.stop();

            if(running) {

                endTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());

            playMedia();
        }
    }


    public void beginTimer(){
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBar.setProgress(current/end);

                if(current/end == 1){
                    endTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    public void endTimer(){
        running = false;
        timer.cancel();
    }
}