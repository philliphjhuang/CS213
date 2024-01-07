package com.demo.utils;

/**
 * @author Yucong Liu, Phillip Huang
 */

import com.demo.entity.pizza.*;
import com.demo.pizieenum.PizzasType;

public class PizzaMaker {

    /**
     * Create a pizza based on pizza type
     * @param pizzaType
     * @return Pizza
     */
    public static Pizza createPizza(String pizzaType) {
        PizzasType deluxe = PizzasType.Deluxe;
        if (PizzasType.Deluxe.toString().equals(pizzaType)){
            return new Deluxe();
        }
        if (PizzasType.Supreme.toString().equals(pizzaType)){
            return new Supreme();
        }
        if (PizzasType.Meatzza.toString().equals(pizzaType)){
            return new Meatzza();
        }
        if (PizzasType.Seafood.toString().equals(pizzaType)){
            return new Seafood();
        }
        if (PizzasType.Pepperoni.toString().equals(pizzaType)){
            return new Pepperoni();
        }
        return new BuildYourOwn();
    }
}
