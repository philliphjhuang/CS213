package com.demo.controller;

/**
 * @author Yucong Liu, Phillip Huang
 */

import com.demo.entity.order.Order;
import com.demo.entity.order.StoreOrders;
import com.demo.entity.pizza.Deluxe;
import com.demo.entity.pizza.Pizza;
import com.demo.pizieenum.PizzasType;
import com.demo.pizieenum.Size;
import com.demo.utils.PizzaMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SpecialtyPizzasController implements Initializable {
    private HashMap<String, Image> pizzasPicMap;
    private Order order;
    private StoreOrders storeOrders;

    @FXML
    private ComboBox pizzasTypeSelect;

    @FXML
    private ImageView pizzaPic;

    @FXML
    private ListView topList;

    @FXML
    private RadioButton small;

    @FXML
    private RadioButton large;

    @FXML
    private RadioButton modium;

    @FXML
    private TextField souce;

    @FXML
    private CheckBox extraSauce;

    @FXML
    private CheckBox extraChesse;

    @FXML
    private TextField price;

    /**
     * Initialize the Specialty Pizza window
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pizzasPicMap =new HashMap<>();
        order=new Order();
        for (PizzasType value : PizzasType.values()) {
            InputStream file = this.getClass().getResourceAsStream("/com/demo/pic/pizzas/"+value.toString()+".png");
            pizzasPicMap.put(value.toString(),new Image(file));
            pizzasTypeSelect.getItems().add(value.toString());
        }
        pizzasTypeSelect.getSelectionModel().selectFirst();
        pizzaPic.setImage(pizzasPicMap.get(PizzasType.Deluxe.toString()));
        Deluxe pizza =(Deluxe) PizzaMaker.createPizza(PizzasType.Deluxe.toString());
        topList.getItems().addAll(pizza.getToppings());
        souce.setText(pizza.getSauce().name());
        small.setSelected(true);
        Pizza pizza1 = getPizza();
        price.setText(String.format("%.2f", pizza1.price()));
        souce.setEditable(false);
    }

    /**
     * Handles the images and texts
     * @param actionEvent
     */
    public void handlePizzaSelection(ActionEvent actionEvent) {
        Pizza pizza = getPizza();
        topList.getItems().clear();
        topList.getItems().addAll(pizza.getToppings());
        pizzaPic.setImage(pizzasPicMap.get(pizzasTypeSelect.getSelectionModel().getSelectedItem()));
        souce.setText(pizza.getSauce().name());
        price.setText(String.format("%.2f", pizza.price()));
    }

    /**
     * Adjust price based on extra sauce
     * @param actionEvent
     */
    @FXML
    public void isSauce(ActionEvent actionEvent){
        Pizza pizza = getPizza();
        price.setText(String.format("%.2f", pizza.price()));
    }

    /**
     * Adjust price based on extra cheese
     * @param actionEvent
     */
    @FXML
    public void isCheese(ActionEvent actionEvent){
        Pizza pizza = getPizza();
        price.setText(String.format("%.2f", pizza.price()));
    }

    /**
     * Handles the process of adding a pizza to an order
     * @param event
     */
    @FXML
    public void addOrder(ActionEvent event) {
        if (order.getPizzas().size()==0){
            int orderNumer = StoreOrders.getNextOrderNumber();
            order.setI(orderNumer);
            this.storeOrders.addOrder(order);
        }
        Pizza pizza = getPizza();
        order.getPizzas().add(pizza);
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Order updated");
        alert.setHeaderText("Hint");
        alert.setContentText("Successfully added pizza!");
        ButtonType buttonTypeOk = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonTypeOk);
        alert.showAndWait();
    }

    /**
     * Small button
     * @param actionEvent
     */
    @FXML
    public void isSmall(ActionEvent actionEvent) {
        large.setSelected(false);
        modium.setSelected(false);
        Pizza pizza = getPizza();
        price.setText(String.format("%.2f", pizza.price()));
    }

    /**
     * Medium button
     * @param actionEvent
     */
    @FXML
    public void isModium(ActionEvent actionEvent) {
        small.setSelected(false);
        large.setSelected(false);
        Pizza pizza = getPizza();
        price.setText(String.format("%.2f", pizza.price()));
    }

    /**
     * Large button
     * @param actionEvent
     */
    @FXML
    public void isLarge(ActionEvent actionEvent) {
        modium.setSelected(false);
        small.setSelected(false);
        Pizza pizza = getPizza();
        price.setText(String.format("%.2f", pizza.price()));
    }

    /**
     * Gets pizza object based on size, extra sauce, and extra cheese
     * @return Pizza
     */
    private Pizza getPizza() {
        Pizza pizza = PizzaMaker.createPizza((String) pizzasTypeSelect.getSelectionModel().getSelectedItem());
        boolean selected = small.isSelected();
        boolean modiumed = modium.isSelected();
        boolean largeed = large.isSelected();
        if (selected){
            pizza.setSize(Size.SMALL);
        }
        if (modiumed){
            pizza.setSize(Size.MEDIUM);
        }
        if (largeed){
            pizza.setSize(Size.LARGE);
        }
        pizza.setExtraCheese(extraChesse.isSelected());
        pizza.setExtraSauce(extraSauce.isSelected());
        return pizza;
    }

    /**
     * Gets store orders
     * @param data
     */
    public void receiveData(StoreOrders data) {
        this.storeOrders=data;
    }
}
