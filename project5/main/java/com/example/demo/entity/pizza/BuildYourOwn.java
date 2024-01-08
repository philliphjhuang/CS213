package com.example.demo.entity.pizza;


import com.example.demo.R;
import com.example.demo.pizieenum.Size;

/**
 * @author Yucong Liu, Phillip Huang
 */

public class BuildYourOwn extends Pizza {
    public int getResourceImg(){
        return R.drawable.build_your_own;
    }

    /**
     * Constructor
     */
    public BuildYourOwn( ) {

    }

    /**
     * Returns the price of pizza based on size and toppings
     * @return double
     */
    @Override
    public double price() {
        double basePrice = 8.99;
        if (this.size == Size.MEDIUM) {
            basePrice += 2;
        } else if (this.size == Size.LARGE) {
            basePrice += 4;
        }
        if (getToppings().size()>3&&getToppings().size()<=7){
            basePrice+=(getToppings().size()-3)*1.49;
        }
        if (getToppings().size()>7){
            return  0;
        }
        else
        {
            return basePrice + (extraSauce ? 1 : 0) + (extraCheese ? 1 : 0);
        }

    }
}
