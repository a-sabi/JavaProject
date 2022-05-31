package com.example.mediaplayer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public AnchorPane player;
    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton, pauseButton, nextButton, previousButton, openButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ListView <String> MusicListView;
    @FXML
    private Slider progressBar;
    @FXML
    private TextField searchBar;


    private Media media;
    private MediaPlayer mediaPlayer;
    private List<File> fileList;
    private final FileChooser fileChooser = new FileChooser();

    private int currentSong = 0;
    private boolean isPlaying = false;
    private boolean islistOpen = false;
    @FXML
    private ImageView logoImageView, mainImageView, playlistsImageView, playImageView, zho;


    // начало нового кода
     private Stage stage;
     private Scene scene;
     private Parent root;


     public void switchTohelloView(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
     }

    public void switchTohelloView0(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view0.fxml"));
         stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPlaylist1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("playlist1.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //конец нового кода


    public void initialize(URL arg0, ResourceBundle arg1) {



        File logoFile = new File("images/Component 3.png");
        Image logoImage = new Image(logoFile.toURI().toString());
        logoImageView.setImage(logoImage);

        File mainFile = new File("images/Component 4.png");
        Image mainImage = new Image(mainFile.toURI().toString());
        mainImageView.setImage(mainImage);

        File playlistsFile = new File("images/Component 5.png");
        Image playlistsImage = new Image(playlistsFile.toURI().toString());
        playlistsImageView.setImage(playlistsImage);

        File playFile = new File("images/Polygon 1.png");
        Image playImage = new Image(playFile.toURI().toString());
        playImageView.setImage(playImage);


//        File zhoFIle = new File("images/album.jpg");
//        Image zhoImage = new Image(zhoFile.toURI().toString());
//        zho.setImage(zhoImage);

    }

    public void previousMedia() throws FileNotFoundException {
        if (mediaPlayer != null && currentSong >0){
            mediaPlayer.stop();
            currentSong--;
            media = new Media(fileList.get(currentSong).toURI().toASCIIString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.play();
            isPlaying = true;
            songLabel.setText(fileList.get(currentSong).getName());
            //playButton.setText("Stop");
            try {
                playButton.setGraphic(new ImageView(new Image(new FileInputStream("images/Component 6.png"))));
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            playVisual();
        }
        else {
            mediaPlayer.stop();
            currentSong = fileList.size() -1;
            media = new Media(fileList.get(currentSong).toURI().toASCIIString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.play();
            isPlaying = true;
            songLabel.setText(fileList.get(currentSong).getName());
            try {
                playButton.setGraphic(new ImageView(new Image(new FileInputStream("images/Component 6.png"))));
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            playVisual();
        }
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<javafx.util.Duration>() {
            @Override
            public void changed(ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                progressBar.setValue(newValue.toSeconds());
            }
        }
        );

        progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });

        progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                javafx.util.Duration total = media.getDuration();
                progressBar.setMax(total.toSeconds());
            }
        });
    }

    public void playMedia() throws FileNotFoundException {
        if(mediaPlayer != null) {
            if (!isPlaying) {
                mediaPlayer.play();
                isPlaying = true;
                try {
                    playButton.setGraphic(new ImageView(new Image(new FileInputStream("images/Component 6.png"))));
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                mediaPlayer.pause();
                isPlaying = false;
                try {
                    playButton.setGraphic(new ImageView(new Image(new FileInputStream("images/Polygon 1.png"))));
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        playVisual();
    }

    public void nextMedia() throws FileNotFoundException {
        if(mediaPlayer != null && currentSong < (fileList.size() - 1)) {
            mediaPlayer.stop();
            currentSong++;
            media = new Media(fileList.get(currentSong).toURI().toASCIIString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.play();
            isPlaying = true;
            songLabel.setText(fileList.get(currentSong).getName());
            try {
                playButton.setGraphic(new ImageView(new Image(new FileInputStream("images/Component 6.png"))));
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            playVisual();
        }
        else {
            mediaPlayer.stop();
            currentSong = 0;
            media = new Media(fileList.get(currentSong).toURI().toASCIIString());
            mediaPlayer = new MediaPlayer(media);

            mediaPlayer.play();
            isPlaying = true;
            songLabel.setText(fileList.get(currentSong).getName());
            try {
                playButton.setGraphic(new ImageView(new Image(new FileInputStream("images/Component 6.png"))));
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            playVisual();
        }
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<javafx.util.Duration>() {
            @Override
            public void changed(ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                progressBar.setValue(newValue.toSeconds());
            }
        }
        );

        progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });

        progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                javafx.util.Duration total = media.getDuration();
                progressBar.setMax(total.toSeconds());
            }
        });
    }

    public void OpenButtonAction() throws FileNotFoundException {
        List<File> fileListOld = fileList;
        configureFileChooser(fileChooser);
        fileList = fileChooser.showOpenMultipleDialog(Globals.primaryStage);

        if(fileList != null) {
            if (isPlaying) {
                mediaPlayer.stop();
                isPlaying = false;
                try {
                    playButton.setGraphic(new ImageView(new Image(new FileInputStream("images/Polygon 1.png"))));
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                MusicListView.getItems().clear();
                MusicListView.getSelectionModel().selectFirst();
            }
            for(int i = 0; i < fileList.size(); i++) {
                MusicListView.getItems().add(fileList.get(i).getName());
            }

            Globals.MusicList = fileList.size();
            currentSong = 0;
            if (fileList.get(currentSong).toURI() != null) {
                media = new Media(fileList.get(currentSong).toURI().toASCIIString());


                mediaPlayer = new MediaPlayer(media);
                songLabel.setText(fileList.get(currentSong).getName());

                mediaPlayer.setVolume(0.5);
                volumeSlider.setValue(mediaPlayer.getVolume());

                volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue<? extends Number> ov,
                                        Number old_val, Number new_val) {
                        mediaPlayer.setVolume(volumeSlider.getValue());
                    }
                });
            }
        } else {
            fileList = fileListOld;
        }
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<javafx.util.Duration>() {
            @Override
            public void changed(ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                progressBar.setValue(newValue.toSeconds());
            }
        }
        );

        progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });

        progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                javafx.util.Duration total = media.getDuration();
                progressBar.setMax(total.toSeconds());
            }
        });
    }

    public void MusicListViewClickAction() throws FileNotFoundException {
        if (fileList != null) {
            mediaPlayer.stop();
            currentSong = MusicListView.getSelectionModel().getSelectedIndex();

            media = new Media(fileList.get(currentSong).toURI().toASCIIString());
            if (isPlaying) {
                mediaPlayer.stop();
                isPlaying = false;
                try {
                    playButton.setGraphic(new ImageView(new Image(new FileInputStream("images/Polygon 1.png"))));
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(fileList.get(currentSong).getName());


            mediaPlayer.play();
            playVisual();
            isPlaying = true;
            try {
                playButton.setGraphic(new ImageView(new Image(new FileInputStream("images/Component 6.png"))));
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        mediaPlayer.currentTimeProperty().addListener(new ChangeListener<javafx.util.Duration>() {
            @Override
            public void changed(ObservableValue<? extends javafx.util.Duration> observable, javafx.util.Duration oldValue, javafx.util.Duration newValue) {
                progressBar.setValue(newValue.toSeconds());
            }
        }
        );

        progressBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });

        progressBar.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mediaPlayer.seek(javafx.util.Duration.seconds(progressBar.getValue()));
            }
        });

        mediaPlayer.setOnReady(new Runnable() {
            @Override
            public void run() {
                javafx.util.Duration total = media.getDuration();
                progressBar.setMax(total.toSeconds());
            }
        });
    }

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View songs");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3", "*.mp3")
        );
    }

    private void playVisual(){
        if (mediaPlayer != null){
            songLabel.setText(fileList.get(currentSong).getName());

            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    isPlaying = false;
                    try {
                        playButton.setGraphic(new ImageView(new Image(new FileInputStream("images/Polygon 1.png"))));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }
}