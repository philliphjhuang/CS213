package com.demo.controller;

/**
 * @author Yucong Liu, Phillip Huang
 */

import com.demo.HelloApplication;
import com.demo.entity.order.StoreOrders;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    private StoreOrders storeOrders = new StoreOrders();
    @FXML
    private ImageView SpecialtyPizzas;

    @FXML
    private ImageView image2;

    @FXML
    private ImageView image3;

    @FXML
    private ImageView image4;

    /**
     * Initialize Menu window
     */
    @FXML
    private void initialize() {
//        image3.setImage(new Image());
    }

    /**
     * Specialty pizza
     * @param mouseEvent
     * @throws IOException
     */
    @FXML
    public void SpecialtyPizzasClick(MouseEvent mouseEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("specialtyPizzas.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        SpecialtyPizzasController controller = fxmlLoader.getController();
        controller.receiveData(storeOrders);
        stage.setTitle("Specialty Pizzas");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Build your own pizza
     * @param event
     * @throws IOException
     */
    @FXML
    void buildOwnPizzaClick(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("buildYourOwnController.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        BuildYourOwnController controller = fxmlLoader.getController();
        controller.receiveData(storeOrders);
        stage.setTitle("Build Your Own Pizza");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Current Order
     * @param event
     * @throws IOException
     */
    @FXML
    void currentOrderClick(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("currentOrderController.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
        CurrentOrderController controller = fxmlLoader.getController();
        controller.receiveData(storeOrders);
        stage.setTitle("Your current order");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Store Order
     * @param event
     * @throws IOException
     */
    @FXML
    void storeOrderClick(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pizzaOrderController.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        PizzaOrderController controller = fxmlLoader.getController();
        controller.receiveData(storeOrders);
        stage.setTitle("yourPizza");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


}