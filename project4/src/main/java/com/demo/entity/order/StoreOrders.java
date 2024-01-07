package com.demo.entity.order;

/**
 * @author Yucong Liu, Phillip Huang
 */

import java.util.ArrayList;
import java.util.List;

public class StoreOrders {
    private static int nextOrderNumber = 1;
    private List<Order> orders;

    /**
     * Constructor
     */
    public StoreOrders() {
        this.orders = new ArrayList<>();
    }

    /**
     * Add an order to orders
     * @param order
     */
    public void addOrder(Order order) {
        nextOrderNumber++;
        orders.add(order);
    }

    /**
     * Gets the next order number
     * @return nextOrderNumber+1
     */
    public static int getNextOrderNumber() {
        return nextOrderNumber++;
    }

    /**
     * Getter for orders
     * @return orders
     */
    public List<Order> getOrders() {
        return orders;
    }

}
