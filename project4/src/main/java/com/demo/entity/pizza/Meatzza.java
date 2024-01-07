
package com.demo.entity.pizza;

/**
 * @author Yucong Liu, Phillip Huang
 */

import com.demo.pizieenum.Sauce;
import com.demo.pizieenum.Size;
import com.demo.pizieenum.Topping;

import java.util.ArrayList;

public class Meatzza extends Pizza {

    /**
     * Constructor
     */
    public Meatzza( ) {
        super();
        toppings=new ArrayList<>();
        this.sauce = Sauce.TOMATO;
        toppings.add(Topping.SAUSAGE);
        toppings.add(Topping.PEPPERONI);
        toppings.add(Topping.BEEF);
        toppings.add(Topping.HAM);
    }

    /**
     * Returns the price of pizza based on size, extra sauce, and extra cheese
     * @return double
     */
    @Override
    public double price() {
        double basePrice = 16.99;
        if (this.size == Size.MEDIUM) {
            basePrice += 2;
        } else if (this.size == Size.LARGE) {
            basePrice += 4;
        }
        return basePrice + (extraSauce ? 1 : 0) + (extraCheese ? 1 : 0);
    }
}
