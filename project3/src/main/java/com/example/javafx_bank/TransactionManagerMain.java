package com.example.javafx_bank;

/**
 * @author Yucong Liu, Phillip Huang
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TransactionManagerMain extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TransactionManagerMain.class.getResource("TransactionManagerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1250, 850); //850 is width, 750 is height
        primaryStage.setTitle("Project 3 - Transaction Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}