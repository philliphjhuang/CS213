package com.demo.entity.pizza;

/**
 * @author Yucong Liu, Phillip Huang
 */

import com.demo.pizieenum.Sauce;
import com.demo.pizieenum.Size;
import com.demo.pizieenum.Topping;

import java.util.ArrayList;

public class Seafood extends Pizza {

    /**
     * Constructor
     */
    public Seafood( ) {
        super();
        this.sauce = Sauce.ALFREDO;
        toppings=new ArrayList<>();
        toppings.add(Topping.SHRIMP);
        toppings.add(Topping.SQUID);
        toppings.add(Topping.CRAB_MEAT);
    }

    /**
     * Returns the price of pizza based on size, extra sauce, and extra cheese
     * @return double
     */
    @Override
    public double price() {
        double basePrice = 17.99;
        if (this.size == Size.MEDIUM) {
            basePrice += 2;
        } else if (this.size == Size.LARGE) {
            basePrice += 4;
        }
        return basePrice + (extraSauce ? 1 : 0) + (extraCheese ? 1 : 0);
    }
}
