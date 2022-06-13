package com.example.mediaplayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.assertions.api.Assertions;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import javafx.stage.DirectoryChooser;
import org.testfx.framework.junit5.Start;

import java.io.*;
import java.util.List;


/**
 * inheritance of methods from a HelloController class
 * @author morcherna
 * @version 1.3
 */

@ExtendWith(ApplicationExtension.class)
class HelloControllerTest1   {

    /**
     * declaring variables media etc.
     */
    private Media media, media1;
    private MediaPlayer mediaPlayer;
    private List<File> fileList;
    private final FileChooser fileChooser = new FileChooser();

    private final DirectoryChooser directoryChooser = new DirectoryChooser();
    private File musicdirectory;
    private int currentSong = 0;

    private HelloController hc = new HelloController();
    private ObservableList<Media> mediaList = FXCollections.observableArrayList();

    /**
     * launching the media player window
     */
    @Start
    public void start(Stage stage) throws IOException {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * the annotation is performed before each test
     */
    @BeforeEach
    /**
     * adding two songs
     */
    void setUp() {
        try {
            media = new Media(new File("src/main/resources/music/song2.mp3").toURI().toASCIIString());
            media1 = new Media(new File("src/main/resources/music/song1.mp3").toURI().toASCIIString());
            mediaList.addAll(media, media1);
            mediaPlayer = new MediaPlayer(mediaList.remove(0));
        } catch (Exception e){
        }
    }


    @Test
    /**
     * checking to open the song selection window
     */
    void testOpenButtonAction(FxRobot robot) {
        mediaPlayer.play();
        robot.clickOn("#openButton");
        try {
            Assertions.assertThat(directoryChooser.showDialog(Globals.primaryStage).equals(true));
        } catch (Exception e) {
        }
    }



    @Test
    /**
     * checking the operation of the 'play' button
     */
    void testPlayMedia(FxRobot robot) {
        mediaPlayer.play();
        robot.clickOn("#playButton");
        try {
            Assertions.assertThat(mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING));
        } catch (AssertionError ae) {
            System.out.println("Status should be played but is " + mediaPlayer.getStatus());
        }
    }

    @Test
    /**
     * checking the operation of the 'stop' button
     */
    void testPauseMedia(FxRobot robot) {
        mediaPlayer.play();
        robot.doubleClickOn("#playButton");
        try {
            Assertions.assertThat(mediaPlayer.getStatus().equals(MediaPlayer.Status.PAUSED));
        } catch (AssertionError ae) {
            System.out.println("Status should be paused but is " + mediaPlayer.getStatus());
        }
    }

}