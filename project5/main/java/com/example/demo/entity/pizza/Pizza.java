package com.example.demo.entity.pizza;

/**
 * @author Yucong Liu, Phillip Huang
 */

import com.example.demo.pizieenum.Sauce;
import com.example.demo.pizieenum.Size;
import com.example.demo.pizieenum.Topping;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Pizza implements Serializable,IResourceImg {

    protected ArrayList<Topping> toppings;
    protected Size size;
    protected Sauce sauce;
    protected boolean extraSauce;
    protected boolean extraCheese;

    /**
     * Abstract method for calculating the price for each type of pizzas
     * @return double
     */
    public abstract double price();

    /**
     * Getter for a list of toppings
     * @return toppings
     */
    public ArrayList<Topping> getToppings() {
        return toppings;
    }

    /**
     * Setter for toppings
     * @param toppings
     */
    public void setToppings(ArrayList<Topping> toppings) {
        this.toppings = toppings;
    }

    /**
     * Getter for size
     * @return size
     */
    public Size getSize() {
        return size;
    }

    /**
     * Setter for size
     * @param size
     */
    public void setSize(Size size) {
        this.size = size;
    }

    /**
     * Getter for sauce
     * @return sauce
     */
    public Sauce getSauce() {
        return sauce;
    }

    /**
     * Setter for sauce
     * @param sauce
     */
    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    /**
     * Getter for extraSauce
     * @return extraSauce
     */
    public boolean isExtraSauce() {
        return extraSauce;
    }

    /**
     * Setter for extraSauce
     * @param extraSauce
     */
    public void setExtraSauce(boolean extraSauce) {
        this.extraSauce = extraSauce;
    }

    /**
     * Getter for extraCheese
     * @return extraCheese
     */
    public boolean isExtraCheese() {
        return extraCheese;
    }

    /**
     * Setter for extraCheese
     * @param extraCheese
     */
    public void setExtraCheese(boolean extraCheese) {
        this.extraCheese = extraCheese;
    }

    /**
     * Return a pizza as a String for exporting
     * @return String
     */
    public String toString() {
        StringBuffer sb=new StringBuffer();
        sb.append("["+getClass().getSimpleName()+"]");
        for (Topping topping : toppings) {
            sb.append(topping.name().toString()+",");
        }
        sb.append(getSize().name().toString()+",");
        sb.append(getSauce().name()+",");
        if (isExtraCheese()){
            sb.append("extra cheese,");
        }
        if (isExtraSauce()){
            sb.append("extra sauce,");
        }
        sb.append("$");
        sb.append(String.format("%.2f", price()));
        return sb.toString();
    }
}
