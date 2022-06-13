package com.example.mediaplayer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Run the program
 * @author Sabina
 * @version 1.3
 */
public class HelloApplication extends Application {

    /**
     * switching FXML pages
     */
    @Override
    //начало
    public void start(Stage stage){
        try{
            Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {  // конец нового кода
            e.printStackTrace();
        }

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {

                Platform.exit();
                System.exit(0);
            }
        });

    }


    public static void main(String[] args) {
        launch();
    }
}