package com.demo;

/**
 * @author Yucong Liu, Phillip Huang
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    /**
     * Start running the program
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800); //1200 is horizontal length, 800 is vertical length
        stage.setTitle("Project 4");
        stage.setScene(scene);
        stage.show();

//
//        Node divider = scene.lookup(".split-pane-divider");
//        if (divider != null) {
//            divider.setStyle("-fx-background-color: transparent;");
//        }
    }

    /**
     * Main Method
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}