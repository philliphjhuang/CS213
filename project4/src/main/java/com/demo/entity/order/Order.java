package com.demo.entity.order;

/**
 * @author Yucong Liu, Phillip Huang
 */

import com.demo.entity.pizza.Pizza;

import java.util.ArrayList;
import java.util.List;

public class Order {

    private int i;
    private List<Pizza> pizzas;
    private Boolean isPlace=false;

    private Boolean isSize=false;
    /**
     * Constructor
     */
    public Order() {
        this.pizzas = new ArrayList<>();
    }

    /**
     * Getter to check if an order is placed
     * @return true if placed, false otherwise
     */
    public Boolean getPlace() {
        return isPlace;
    }
    /**
     * Getter to check if the size is large, medium or small
     * @return true if placed, false otherwise
     */

    public Boolean getSize() {
        return isSize;
    }
    /**
     * Setter for isPlace
     * @param place
     */
    public void setPlace(Boolean place) {
        isPlace = place;
    }

    public void setSize(Boolean size) {
        isSize = size;
    }


    /**
     * Getter for order number as an int
     * @return i
     */
    public int getI() {
        return i;
    }

    /**
     * Setter for order number
     * @param i
     */
    public void setI(int i) {
        this.i = i;
    }

    /**
     * Getter for all the pizzas in a list
     * @return pizzas
     */
    public List<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * Setter for a list of pizzas
     * @param pizzas
     */
    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }
}
