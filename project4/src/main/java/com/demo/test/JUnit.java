package com.demo.test;

/**
 * @author Yucong Liu, Phillip Huang
 */

import static org.junit.Assert.*;

import com.demo.entity.pizza.Pizza;
import com.demo.pizieenum.Size;
import com.demo.pizieenum.Topping;
import com.demo.utils.PizzaMaker;
import org.junit.Test;

import java.util.*;

import java.util.Arrays;

public class JUnit {

    /**
     * Test Case #1: a BuildYourOwn pizza
     */
    @Test
    public void test1() {
        Pizza pizza = PizzaMaker.createPizza("BuildYourOwn");
        pizza.setSize(Size.SMALL);
        ArrayList<Topping> list = new ArrayList<>();
        List<Topping> list1 = Arrays.asList(Topping.BEEF, Topping.PEPPERONI, Topping.HAM);
        list.addAll(list1);
        pizza.setToppings(list);
        double expectedPrice = 8.99;
        assertEquals(expectedPrice, pizza.price(), 0.01);
    }

    /**
     * Test Case #2: a BuildYourOwn pizza
     */
    @Test
    public void test2() {
        Pizza pizza = PizzaMaker.createPizza("BuildYourOwn");
        pizza.setSize(Size.SMALL);
        ArrayList<Topping> toppings = new ArrayList<>();
        List<Topping> list1 = Arrays.asList(Topping.BEEF, Topping.PEPPERONI, Topping.HAM,Topping.PINEAPPLE);
        toppings.addAll(list1);
        pizza.setToppings(toppings);
        double expectedPrice = 10.48;
        assertEquals(expectedPrice, pizza.price(), 0.01);
    }

    /**
     * Test Case #3: a seafood pizza
     */
    @Test
    public void test3() {
        Pizza pizza = PizzaMaker.createPizza("Seafood");
        pizza.setSize(Size.SMALL);
        pizza.setExtraCheese(true);
        double expectedPrice = 18.99;
        assertEquals(expectedPrice, pizza.price(), 0.01);
    }

    /**
     * Test Case #4: a supreme pizza
     */
    @Test
    public void test4() {
        Pizza pizza = PizzaMaker.createPizza("Supreme");
        pizza.setSize(Size.LARGE);
        pizza.setExtraSauce(true);
        double expectedPrice = 20.99;
        assertEquals(expectedPrice, pizza.price(), 0.01);
    }

    /**
     * Test Case #5: a meatzza pizza
     */
    @Test
    public void test5() {
        Pizza pizza = PizzaMaker.createPizza("Meatzza");
        pizza.setSize(Size.LARGE);
        double expectedPrice = 20.99;
        assertEquals(expectedPrice, pizza.price(), 0.01);
    }

    /**
     * Test Case #6: a BuildYourOwn pizza with 2 toppings
     */
    @Test
    public void test6() {
        Pizza pizza = PizzaMaker.createPizza("BuildYourOwn");
        pizza.setSize(Size.SMALL);
        ArrayList<Topping> toppings = new ArrayList<>();
        List<Topping> list1 = Arrays.asList(Topping.BEEF, Topping.PEPPERONI);
        toppings.addAll(list1);
        pizza.setToppings(toppings);
        double expectedPrice = 8.99;
        assertEquals(expectedPrice, pizza.price(), 0.01);
    }

    /**
     * Test Case #7: a BuildYourOwn pizza with 7 toppings
     */
    @Test
    public void test7() {
        Pizza pizza = PizzaMaker.createPizza("BuildYourOwn");
        pizza.setSize(Size.SMALL);
        ArrayList<Topping> toppings = new ArrayList<>();
        List<Topping> list1 = Arrays.asList(Topping.BEEF, Topping.PEPPERONI, Topping.PINEAPPLE, Topping.CHICKEN, Topping.HAM,
                Topping.BLACK_OLIVE, Topping.CRAB_MEAT);
        toppings.addAll(list1);
        pizza.setToppings(toppings);
        double expectedPrice = 14.95;
        assertEquals(expectedPrice, pizza.price(), 0.01);
    }

    /**
     * Test Case #8: a pepperoni pizza
     */
    @Test
    public void test8() {
        Pizza pizza = PizzaMaker.createPizza("Pepperoni");
        pizza.setSize(Size.LARGE);
        pizza.setExtraSauce(true);
        double expectedPrice = 15.99;
        assertEquals(expectedPrice, pizza.price(), 0.01);
    }

    /**
     * Test Case #9: a Deluxe pizza
     */
    @Test
    public void test9() {
        Pizza pizza = PizzaMaker.createPizza("Deluxe");
        pizza.setSize(Size.MEDIUM);
        pizza.setExtraSauce(true);
        pizza.setExtraCheese(true);
        double expectedPrice = 18.99;
        assertEquals(expectedPrice, pizza.price(), 0.01);
    }

    /**
     * Test Case #10: a BuildYourOwn pizza with 8 toppings (Invalid)
     */
    @Test
    public void test10() {
        Pizza pizza = PizzaMaker.createPizza("BuildYourOwn");
        pizza.setSize(Size.SMALL);
        ArrayList<Topping> toppings = new ArrayList<>();
        List<Topping> list1 = Arrays.asList(Topping.BEEF, Topping.PEPPERONI, Topping.PINEAPPLE, Topping.CHICKEN, Topping.HAM,
                Topping.BLACK_OLIVE, Topping.CRAB_MEAT, Topping.GREEN_PEPPER);
        toppings.addAll(list1);
        pizza.setToppings(toppings);
        double expectedPrice = 0.0;
        assertEquals(expectedPrice, pizza.price(), 0.01);
    }
}
