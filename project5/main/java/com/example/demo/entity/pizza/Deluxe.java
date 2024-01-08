package com.example.demo.entity.pizza;

/**
 * @author Yucong Liu, Phillip Huang
 */

import com.example.demo.R;
import com.example.demo.pizieenum.Sauce;
import com.example.demo.pizieenum.Size;
import com.example.demo.pizieenum.Topping;

import java.util.ArrayList;

public class Deluxe extends Pizza {

    /**
     * Returns the int of image
     * @return int
     */
    public int getResourceImg(){
        return R.drawable.deluxe;
    }

    /**
     * Constructor
     */
    public Deluxe() {
        super();
        this.sauce = Sauce.TOMATO;
        toppings=new ArrayList<>();
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.ONION);
        toppings.add(Topping.MUSHROOM);
    }

    /**
     * Returns the price of pizza based on size, extra sauce, and extra cheese
     * @return double
     */
    @Override
    public double price() {
        double basePrice = 14.99;
        if (this.size == Size.MEDIUM) {
            basePrice += 2;
        } else if (this.size == Size.LARGE) {
            basePrice += 4;
        }
        return basePrice + (extraSauce ? 1 : 0) + (extraCheese ? 1 : 0);
    }
}
