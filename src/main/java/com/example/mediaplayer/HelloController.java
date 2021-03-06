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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;


import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * initialize all functions
 * @author Sabina
 * @version 1.3
 */
public class HelloController implements Initializable {
    public AnchorPane player;
    /**
     * declaring variables via FXML
     */
    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    /**
     * declaring buttons via FXML
     */
    @FXML
    private Button playButton, pauseButton, nextButton, previousButton, openButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ListView <String> MusicListView;
    @FXML
    private Slider progressBar;


    /**
     * uploading songs
     */
    private Media media;
    private MediaPlayer mediaPlayer;
    private List<File> fileList;
    //private final FileChooser fileChooser = new FileChooser();
    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private File musicdirectory;

    private int currentSong = 0;
    private boolean isPlaying = false;
    private boolean islistOpen = false;
    @FXML
    private ImageView logoImageView, mainImageView, playlistsImageView, playImageView;


    // начало нового кода
     private Stage stage;
     private Scene scene;
     private Parent root;

    /**
     * @author Kris
     * the button to go to the main page
     */
     public void switchTohelloView(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
     }
    /**
     * the button to go to the playlists page
     */
    public void switchTohelloView0(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view0.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * the button to go to the playlist page
     */
    public void switchToPlaylist1(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("playlist1.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //конец нового кода

    /**
     *  Initialize pictures
     */
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


         try (FileReader reader = new FileReader("config.txt")){
             String path = "";
             int c;
             while ((c = reader.read()) != -1){
                 path += (char)c;
             }
             musicdirectory = new File(path);
             fileList = getMusicList(musicdirectory);
             ShowMusicList();
         }
         catch (IOException e){
         }

    }
    /**
     * move to the previous song
     */
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
    /**
     * start playing the song
     */
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
    /**
     * selecting a folder with mp3 files or mp3 files from a folder
     */
    public List<File> getMusicList(File musicdirectory){
        List<File> musiclist = new ArrayList<File>();
        if (musicdirectory != null){
            musiclist = Arrays.stream(musicdirectory.listFiles(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    if (file.getName().endsWith(".mp3"))
                        return true;
                    return false;
                }
            })).toList();
        }
        return musiclist;
    }
    /**
     * displaying uploaded songs
     */
    public void ShowMusicList() throws FileNotFoundException {
        List<File> fileListOld = fileList;
        if (fileList != null) {
            if (isPlaying && mediaPlayer != null) {
                mediaPlayer.stop();
                isPlaying = false;
                try {
                    playButton.setGraphic(new ImageView(new Image(new FileInputStream("images/Polygon 1.png"))));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if(MusicListView != null) {
                    MusicListView.getItems().clear();
                    MusicListView.getSelectionModel().selectFirst();
                }
            }
            if(MusicListView!= null)
                for (int i = 0; i < fileList.size(); i++) {
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
    /**
     * creating a text file where a list of songs is added
     */
    public void OpenButtonAction() throws FileNotFoundException {

        configureDirectoryChooser(directoryChooser);
        musicdirectory = directoryChooser.showDialog(Globals.primaryStage);
        File configfile = new File("config.txt");
        try (FileWriter fileWriter = new FileWriter(configfile, false)){
            fileWriter.write(musicdirectory.getAbsolutePath());
            fileWriter.flush();
        }
        catch (IOException exception){
        }
        fileList = getMusicList(musicdirectory);
        ShowMusicList();
    }
    /**
     * changing icons and stop-play functions
     */
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
                } catch (FileNotFoundException e) {
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
            } catch (FileNotFoundException e) {
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



    private static void configureDirectoryChooser(
            final DirectoryChooser directoryChooser) {
        directoryChooser.setTitle("View songs");
        directoryChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
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