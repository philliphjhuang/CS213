package com.demo.controller;

/**
 * @author Yucong Liu, Phillip Huang
 */

import com.demo.entity.order.Order;
import com.demo.entity.order.StoreOrders;
import com.demo.entity.pizza.Pizza;
import com.demo.pizieenum.Sauce;
import com.demo.pizieenum.Size;
import com.demo.pizieenum.Topping;
import com.demo.utils.PizzaMaker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class BuildYourOwnController implements Initializable {

    private Order order;
    private StoreOrders storeOrders;
    @FXML
    private ImageView pizzaPic;
    @FXML
    private ComboBox<String> pizzasTypeSelect;

    @FXML
    private RadioButton tomato;

    @FXML
    private RadioButton alfredo;

    @FXML
    private CheckBox extraSauce;

    @FXML
    private CheckBox extraChesse;

    @FXML
    private ListView<String> topList;

    @FXML
    private TextField price;
    @FXML
    private ListView<String> addToppingEd;

    /**
     *  Actions for pressing the "add order" button. It also sets what text will pop up when successfully or not successfully
     *      added.
     * @param event
     */
    @FXML
    void addOrder(ActionEvent event) {
        if (this.addToppingEd.getItems().size()<3){
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("WARNING");
            alert.setHeaderText("Hint");
            alert.setContentText("Need at least 3 toppings!");
            ButtonType buttonTypeOk = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(buttonTypeOk);
            alert.showAndWait();
        }
        if (this.addToppingEd.getItems().size()>7){
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("WARNING");
            alert.setHeaderText("Hint");
            alert.setContentText("Cannot add more than 7 toppings!");
            ButtonType buttonTypeOk = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(buttonTypeOk);
            alert.showAndWait();
        }
        if (this.addToppingEd.getItems().size()<=7&&this.addToppingEd.getItems().size()>=3){
            if (order==null){
                order=new Order();
                order.setPizzas(new ArrayList<>());
            }
            if (order.getPizzas().size()==0){
                this.order.setI(StoreOrders.getNextOrderNumber());
                this.storeOrders.addOrder(order);
            }
            Pizza pizza = getPizza();
            order.getPizzas().add(pizza);
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Successfully added");
            alert.setHeaderText("hint");
            alert.setContentText("Successfully added");
            ButtonType buttonTypeOk = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(buttonTypeOk);
            alert.showAndWait();
        }
    }

    /**
     * Change the displayed price based on what size is selected
     * @param event
     */
    @FXML
    void handlePizzaSelection(ActionEvent event) {
        price.setText(getPizza().price()+"");
    }

    /**
     * Change the displayed price based
     * @param event
     */
    @FXML
    void isChese(ActionEvent event) {
        price.setText(getPizza().price()+"");
    }

    /**
     * If Alfredo is checked, set tomato sauce as unchecked
     * @param event
     */
    @FXML
    void isAlfredo(ActionEvent event) {
        tomato.setSelected(false);
    }

    /**
     * Change the displayed price
     * @param event
     */
    @FXML
    void isSauce(ActionEvent event) {
        price.setText(getPizza().price()+"");
    }

    /**
     * If tomato button is pressed, set alfredo as unchecked
     * @param event
     */
    @FXML
    void isTomato(ActionEvent event) {
        this.alfredo.setSelected(false);
        price.setText(getPizza().price()+"");
    }

    /**
     * Get the store orders
     * @param storeOrders
     */
    public void receiveData(StoreOrders storeOrders) {
        this.storeOrders=storeOrders;
    }

    /**
     * If remove topping button is pressed, remove the most recently added topping
     * @param event
     */
    @FXML
    public void removeToping(MouseEvent event) {
        String selectedItems = this.addToppingEd.getSelectionModel().getSelectedItem();
        if (selectedItems!=null){
            topList.getItems().add(selectedItems.toString());
            this.addToppingEd.getItems().remove(selectedItems);
            Pizza pizza = getPizza();
            this.price.setText(pizza.price()+"");
        }
    }

    /**
     * Add the selected topping when add topping button is pressed
     * @param event
     */
    @FXML
    public void addToping(MouseEvent event) {
        String selectedItems = this.topList.getSelectionModel().getSelectedItem();
        if (selectedItems!=null){
            addToppingEd.getItems().add(selectedItems);
            this.topList.getItems().remove(selectedItems);
            Pizza pizza = getPizza();
            this.price.setText(pizza.price()+"");

        }
        if (this.addToppingEd.getItems().size()>7){
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("WARNING");
            alert.setHeaderText("Hint");
            alert.setContentText("Cannot add more than 7 toppings!");
            ButtonType buttonTypeOk = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
            alert.getButtonTypes().setAll(buttonTypeOk);
            alert.showAndWait();
            removeToping(event);
        }

    }

    /**
     * Initialize the build your own pizza window
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Topping value : Topping.values()) {
            this.topList.getItems().add(value.toString());
        }
        pizzasTypeSelect.getItems().addAll("SMALL","MEDIUM","LARGE");
        pizzasTypeSelect.getSelectionModel().selectFirst();
        price.setText(getPizza().price()+"");
        tomato.setSelected(true);
        InputStream file = this.getClass().getResourceAsStream("/com/demo/pic/pizzas/BuildYourOwn.png");

        pizzaPic.setImage(new Image(file));
    }

    /**
     * Get the pizza for its selected size, sauce, toppings, extra cheese, and extra sauce
     * @return pizza
     */
    private Pizza getPizza() {
        Pizza pizza = PizzaMaker.createPizza("own");
        for (Size value : Size.values()) {
            if (pizzasTypeSelect.getSelectionModel().getSelectedItem().toString().equals(value.name())){
                pizza.setSize(value);
            }
        }
        if (tomato.isSelected()){
            pizza.setSauce(Sauce.TOMATO);
        }
        if (alfredo.isSelected()){
            pizza.setSauce(Sauce.ALFREDO);
        }
        ArrayList<Topping> topList = new ArrayList<>();
        for (Topping value : Topping.values()) {
            for (String item : addToppingEd.getItems()) {
                if (value.name().equals(item)) {
                    topList.add(value);
                }
            }
        }
        pizza.setToppings(topList);
        pizza.setExtraCheese(extraChesse.isSelected()?extraChesse.isSelected():false);
        pizza.setExtraSauce(extraSauce.isSelected()?extraSauce.isSelected():false);
        return pizza;
    }
}
