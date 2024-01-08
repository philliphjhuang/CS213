package com.example.demo.entity.order;

/**
 * @author Yucong Liu, Phillip Huang
 */

import java.util.ArrayList;
import java.util.List;

public class StoreOrders {
    private static StoreOrders storeOrders;

    private static int nextOrderNumber = 1;
    private List<Order> orders;

    /**
     * Gets store orders
     * @return storeOrders
     */
    public static StoreOrders getStoreOrders() {
        if (storeOrders == null) {
            storeOrders = new StoreOrders();
            storeOrders.orders=new ArrayList<>();
        }
        return storeOrders;
    }

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
    public List<Order> getCurrentOrders() {
        List<Order> newdata=new ArrayList<>();
        if(orders!=null){
            for (Order data: orders) {
                if(!data.getPlace()){
                    newdata.add(data);
                }
            }
        }
        return newdata;
    }

    /**
     * Get a list of old storeOrders
     * @return newdata
     */
    public List<Order> getHistoryOrders() {
        List<Order> newdata=new ArrayList<>();
        if(orders!=null){
            for (Order data:orders) {
                if(data.getPlace()){
                    newdata.add(data);
                }
            }
        }
        return newdata;
    }
}
