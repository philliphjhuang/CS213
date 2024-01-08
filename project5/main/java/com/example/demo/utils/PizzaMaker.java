package com.example.demo.utils;


import com.example.demo.entity.pizza.BBQ;
import com.example.demo.entity.pizza.BuildYourOwn;
import com.example.demo.entity.pizza.Deluxe;
import com.example.demo.entity.pizza.Hawaiian;
import com.example.demo.entity.pizza.Margherita;
import com.example.demo.entity.pizza.Meatzza;
import com.example.demo.entity.pizza.Pepperoni;
import com.example.demo.entity.pizza.Pizza;
import com.example.demo.entity.pizza.Seafood;
import com.example.demo.entity.pizza.Supreme;
import com.example.demo.entity.pizza.Veggie;
import com.example.demo.entity.pizza.White;
import com.example.demo.pizieenum.PizzasType;

/**
 * @author Yucong Liu, Phillip Huang
 */

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
        if (PizzasType.Margherita.toString().equals(pizzaType)){
            return new Margherita();
        }
        if (PizzasType.Hawaiian.toString().equals(pizzaType)){
            return new Hawaiian();
        }
        if (PizzasType.Veggie.toString().equals(pizzaType)){
            return new Veggie();
        }
        if (PizzasType.BBQ.toString().equals(pizzaType)){
            return new BBQ();
        }
        if (PizzasType.White.toString().equals(pizzaType)){
            return new White();
        }
        return new BuildYourOwn();
    }
}
