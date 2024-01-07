package com.demo.controller;

/**
 * @author Yucong Liu, Phillip Huang
 */

import com.demo.entity.order.Order;
import com.demo.entity.order.StoreOrders;
import com.demo.entity.pizza.Pizza;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
public class CurrentOrderController implements Initializable {

    @FXML
    private TreeView<String> dataList;

    private StoreOrders storeOrders;

    @FXML
    private TextField number;

    @FXML
    private TextField subTotal;

    @FXML
    private TextField sales;

    @FXML
    private TextField totalPrice;

    /**
     * Handles the place order button
     * @param event
     */
    @FXML
    void placeOrder(MouseEvent event) {
        if (dataList.getSelectionModel() == null || dataList.getSelectionModel().getSelectedItems() == null) {
            return;
        }
        TreeItem<String> selectedItem = dataList.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        if (selectedItem.getParent() == null) {
            return;
        }
        String parentValue = selectedItem.getParent().getValue();



        if(parentValue.equals("Order List:")) {
            for (Order storeOrdersOrder : storeOrders.getOrders()) {
                if (selectedItem.getValue().equals(String.valueOf(storeOrdersOrder.getI()))) {
                    storeOrdersOrder.setPlace(true);
                    showAlert("Order updated", "Hint", "Order successful!");
                    number.setText("");
                    subTotal.setText("");
                    sales.setText("");
                    totalPrice.setText("");
                }
            }
        }
        else {
            for (Order storeOrdersOrder : storeOrders.getOrders()) {
                if (parentValue.equals(String.valueOf(storeOrdersOrder.getI()))) {
                    storeOrdersOrder.setPlace(true);
                    showAlert("Order updated", "Hint", "Order successful!");
                    number.setText("");
                    subTotal.setText("");
                    sales.setText("");
                    totalPrice.setText("");
                }
            }
        }
        this.init();
    }

    /**
     * Handles the remove button
     * @param event
     */
    @FXML
    void reomvePizza(MouseEvent event) {
        if (dataList.getSelectionModel() == null || dataList.getSelectionModel().getSelectedItems() == null) {
            return;
        }
        TreeItem<String> selectedItem = dataList.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        if (dataList.getSelectionModel() == null || dataList.getSelectionModel().getSelectedItems() == null) {
            return;
        }
        if (selectedItem == null) {
            return;
        }
        if (selectedItem.getParent() == null) {
            return;
        }
        String parentValue = selectedItem.getParent().getValue();
        if (parentValue.equals("Order List:")) {
            return;
        }
        for (Order storeOrdersOrder : storeOrders.getOrders()) {
            if (parentValue.equals(storeOrdersOrder.getI() + "")) {
                int selectedIndex = dataList.getSelectionModel().getSelectedIndex();
                List<Order> filteredOrders = storeOrders.getOrders().stream()
                        .filter(order->order.getI()<storeOrdersOrder.getI()&&!order.getPlace()).filter(order -> "medium".equals(order.getSize()) || "large".equals(order.getSize())).collect(Collectors.toList());
                long pizzaCount = filteredOrders.stream().mapToLong(order -> order.getPizzas().size()).sum();
                int filteredOrdersSize = filteredOrders.size();
                long treeItemIndex = filteredOrdersSize + pizzaCount + 1;
                long indexToRemove = selectedIndex - treeItemIndex - 1;
                selectedItem.getParent().getChildren().remove((int) indexToRemove);
                Order orderToRemove = this.storeOrders.getOrders().stream().filter(order->parentValue
                                .equals(String.valueOf(order.getI()))).findFirst().orElse(null);
                if (orderToRemove != null) {
                    orderToRemove.getPizzas().remove((int) indexToRemove);
                    showAlert("Order updated", "Hint", "Pizza removed successfully!");
                }
                this.init();
            }
        }
    }

    /**
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Handles the selected order
     * @param event
     */
    @FXML
    public void onSelect(MouseEvent event) {
        if (dataList.getSelectionModel() == null || dataList.getSelectionModel().getSelectedItems() == null) {
            return;
        }

        TreeItem<String> selectedItem = dataList.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            return;
        }
        if (selectedItem.isExpanded()) {
            return;
        }
        if (selectedItem.getParent() == null) {
            return;
        }
        String parentValue = selectedItem.getParent().getValue();
        for (Order storeOrdersOrder : storeOrders.getOrders()) {
            if (parentValue.equals(storeOrdersOrder.getI() + "")) {
                double total = storeOrdersOrder.getPizzas().stream().mapToDouble(pizza -> pizza.price()).sum();
                DecimalFormat decimalFormat = new DecimalFormat("#.00");
                String formattedTotal = decimalFormat.format(total);
                double tax = total * 0.06625;
                String formattedTax = String.format("%.2f", tax);
                sales.setText(formattedTax);
                subTotal.setText(String.format("%.2f", total));
                totalPrice.setText(String.format("%.2f", tax + total));
            }
        }
    }

    /**
     * Returns store orders
     * @param storeOrders
     */
    public void receiveData(StoreOrders storeOrders) {
        this.storeOrders = storeOrders;
        this.init();
    }

    /**
     * Initializes the current order window
     */
    public void init() {
        dataList.setRoot(new TreeItem<>("Order List:"));
        List<Order> orders = this.storeOrders.getOrders();
        int orderNumber = 0;
        for (Order order : orders) {
            if (order.getPizzas().size() == 0) continue;
            if (order.getPlace() == null) {
                continue;
            }
            if (order.getPlace()) continue;
            orderNumber++;
            TreeItem<String> orderTreeItem = new TreeItem<>(order.getI() + "");
            List<Pizza> pizzas = order.getPizzas();

            for (Pizza pizza : pizzas) {
                orderTreeItem.getChildren().add(new TreeItem<>(pizza.toString()));
            }
            dataList.getRoot().getChildren().add(orderTreeItem);
        }
        dataList.getRoot().setExpanded(true);
        ObservableList<TreeItem<String>> children = dataList.getRoot().getChildren();
        for (TreeItem<String> child : children) {
            child.setExpanded(true);
        }
        number.setText(orderNumber + "");
    }

    /**
     * Pops out a window to have user to read and confirm
     * @param title
     * @param headerText
     * @param contentText
     */
    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        ButtonType buttonTypeOk = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(buttonTypeOk);
        alert.showAndWait();
    }
}
