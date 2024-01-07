package com.demo.controller;

/**
 * @author Yucong Liu, Phillip Huang
 */

import com.demo.entity.order.Order;
import com.demo.entity.order.StoreOrders;
import com.demo.entity.pizza.Pizza;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
public class PizzaOrderController {
    private StoreOrders storeOrders;

    @FXML
    private ComboBox<String> number;

    @FXML
    private TextField total;

    @FXML
    private TreeView<String> dataList;

    /**
     * Handles the cancellation of an order
     * @param event
     */
    @FXML
    void cancel(ActionEvent event) {
        TreeItem<String> selectedItem = dataList.getSelectionModel().getSelectedItem();
        if (isOrderItem(selectedItem)) {
            int orderNumber=-1;
            if (selectedItem.getParent().getValue().equals("orderList")) {
                orderNumber = Integer.parseInt(selectedItem.getValue());

            }else{
                orderNumber = Integer.parseInt(selectedItem.getParent().getValue());
            }
            cancelOrder(orderNumber);

            showAlert("Order updated", "Hint", "Cancelled successfully!");
            init();
        }
    }

    /**
     * Selects an order from orderList
     * @param event
     */
    @FXML
    void onSelect(ActionEvent event) {
        initOrderTreeView();
    }

    /**
     * Exports an order as a txt
     * @param event
     */
    @FXML
    void export(ActionEvent event) {
        TreeItem<String> root = dataList.getRoot();
        exportTreeToFile(root, "output.txt");
        showAlert("Export", "Hint", "Exported successfully!");
    }

    /**
     * Gets the store orders
     * @param storeOrders
     */
    public void receiveData(StoreOrders storeOrders) {
        this.storeOrders = storeOrders;
        init();
    }

    /**
     * Test if an item is an order item
     * @param treeItem
     * @return true if yes, false otherwise
     */
    private boolean isOrderItem(TreeItem<String> treeItem) {
        return treeItem != null && treeItem.getParent() != null && treeItem.getParent().getValue().equals("orderList");
    }

    /**
     * Handles the process of cancelling an order based on order number
     * @param orderNumber
     */
    private void cancelOrder(int orderNumber) {
        storeOrders.getOrders().stream()
                .filter(order -> order.getI() == orderNumber)
                .findFirst()
                .ifPresent(order -> order.setPlace(null));
    }

    /**
     * Shows user a message to read and confirm
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

    /**
     * Try and catches error for export process
     * @param root
     * @param filePath
     */
    private void exportTreeToFile(TreeItem<String> root, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            exportTreeItem(root, writer, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper for export to edit a txt file
     * @param item
     * @param writer
     * @param depth
     * @throws IOException
     */
    private void exportTreeItem(TreeItem<String> item, BufferedWriter writer, int depth) throws IOException {
        for (int i = 0; i < depth; i++) {
            writer.write("  ");
        }
        writer.write(item.getValue());
        writer.newLine();

        for (TreeItem<String> child : item.getChildren()) {
            exportTreeItem(child, writer, depth + 1);
        }
    }

    /**
     * Initialize the order list
     */
    private void initOrderTreeView() {
        dataList.setRoot(new TreeItem<>("orderList"));
        dataList.getRoot().setExpanded(true);

        String selectedOrderNumber = number.getSelectionModel().getSelectedItem();
        double totalPizzaPrice = 0;

        for (Order order : storeOrders.getOrders()) {
            if (order.getPlace() != null && order.getPlace()
                    && (selectedOrderNumber.equals("total") || selectedOrderNumber.equals(order.getI() + ""))) {
                TreeItem<String> orderTreeItem = new TreeItem<>(order.getI() + "");
                List<Pizza> pizzas = order.getPizzas();
                DecimalFormat decimalFormat = new DecimalFormat("#.00");

                for (Pizza pizza : pizzas) {
                    orderTreeItem.getChildren().add(new TreeItem<>(pizza.toString()));
                    String formattedPrice = decimalFormat.format(pizza.price());
                    double price = Double.parseDouble(formattedPrice);
                    totalPizzaPrice += price;
                }

                dataList.getRoot().getChildren().add(orderTreeItem);
            }
        }
        ObservableList<TreeItem<String>> children = dataList.getRoot().getChildren();
        children.forEach(child -> child.setExpanded(true));
        double totalValue = totalPizzaPrice * 1.06625;
        String formattedValue = String.format("%.2f", totalValue);
        total.setText(formattedValue);
    }

    /**
     * Initializes the entire store order window
     */
    private void init() {
        dataList.setRoot(new TreeItem<>("orderList"));
        dataList.getRoot().setExpanded(true);
        List<String> orderNumbers = storeOrders.getOrders().stream()
                .filter(order -> order.getPlace() != null && order.getPlace())
                .map(order -> order.getI() + "")
                .collect(Collectors.toList());
        number.getItems().addAll("total");
        number.getItems().addAll(orderNumbers);
        number.getSelectionModel().selectFirst();
        initOrderTreeView();
    }
}