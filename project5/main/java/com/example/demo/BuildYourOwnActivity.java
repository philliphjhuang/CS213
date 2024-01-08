package com.example.demo;

/**
 * @author Yucong Liu
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.demo.entity.order.Order;
import com.example.demo.entity.order.StoreOrders;
import com.example.demo.entity.pizza.BuildYourOwn;
import com.example.demo.entity.pizza.IResourceImg;
import com.example.demo.entity.pizza.Pizza;
import com.example.demo.pizieenum.Sauce;
import com.example.demo.pizieenum.Size;
import com.example.demo.pizieenum.Topping;

import java.util.ArrayList;
import java.util.List;

public class BuildYourOwnActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    List<Pizza> pizzas=new ArrayList<>();
    Order order=null;
    Pizza pizza;
    String pizza_name;
    Spinner spinner_souce;
    List<String> spinner_souce_data;
    String selectedItem;
    LinearLayout ll_topping;
    TextView tv_price;
    RadioButton rb_size_small;
    RadioButton rb_size_large;
    RadioButton rb_size_modium;
    CheckBox cb_extra_sauce;
    CheckBox cb_extra_chesse;
    Button bt_add_to_order;

    /**
     * Initialize the build your own interface
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_build_your_own);
            Intent intent = getIntent();
            pizza =new BuildYourOwn();
            pizza.setToppings(new ArrayList<>());
            pizza.setSize(Size.SMALL);
            pizza_name = intent.getStringExtra("pizza_name");
            spinner_souce_data = new ArrayList<>();
            for (Sauce sauce: Sauce.values()){
                spinner_souce_data.add(sauce.name());
            }
            inview();
            setData();
        }

    /**
     * Get the int values and set images
     */
    public void inview(){
        tv_price = findViewById(R.id.tv_price);
        ll_topping = findViewById(R.id.ll_topping);
        spinner_souce = findViewById(R.id.spinner_souce);
        rb_size_small = findViewById(R.id.rb_size_small);
        rb_size_large = findViewById(R.id.rb_size_large);
        rb_size_modium = findViewById(R.id.rb_size_modium);
        cb_extra_sauce = findViewById(R.id.cb_extra_sauce);
        cb_extra_chesse = findViewById(R.id.cb_extra_chesse);
        bt_add_to_order=findViewById(R.id.bt_add_to_order);
        bt_add_to_order.setOnClickListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner_souce_data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_souce.setAdapter(adapter);
        spinner_souce.setOnItemSelectedListener(this);
        ll_topping.removeAllViews();
        rb_size_small.setOnCheckedChangeListener(this);
        rb_size_large.setOnCheckedChangeListener(this);
        rb_size_modium.setOnCheckedChangeListener(this);
        cb_extra_sauce.setOnCheckedChangeListener(this);
        cb_extra_chesse.setOnCheckedChangeListener(this);
    }

    /**
     * Change the total price based on buttons selected
     */
    public void setData(){
        LayoutInflater inflater = LayoutInflater.from(this);
        if(pizza!=null){
            if(pizza instanceof IResourceImg) {
                int id = ((IResourceImg) pizza).getResourceImg();
                ((ImageView) findViewById(R.id.iv_img)).setImageResource(id);
            }
            ((TextView) findViewById(R.id.pizzas_name)).setText(pizza_name);
            if(pizza.getToppings()!=null) {
                for (Topping topping : Topping.values()) {
                    View view = inflater.inflate(R.layout.item_topping_check, null);
                    CheckBox cb_topping_name= view.findViewById(R.id.cb_topping_name);
                    cb_topping_name .setText(topping.name());
                    cb_topping_name .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                        /**
                         * Change the topppings based on add and remove buttons
                         * @param compoundButton
                         * @param b
                         */
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if(b){
                                pizza.getToppings().add(topping);
                            } else {
                                pizza.getToppings().remove(topping);
                            }
                            setPrice();
                        }
                    });
                    ll_topping.addView(view);
                }
            }
            if(pizza.getSauce()!=null){
                String defaultSelectedItem = pizza.getSauce().name();
                int defaultSelectedPosition = spinner_souce_data.indexOf(defaultSelectedItem);
                spinner_souce.setSelection(defaultSelectedPosition);
            }
        }
        setPrice();
    }

    /**
     * Set the price according to pizza type
     */
    public void  setPrice(){
        tv_price.setText(String.format("%.2f", pizza.price()));
    }

    /**
     * Set buttons as checked when they are selected
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedItem = (String) parent.getItemAtPosition(position);
    }

    /**
     * Do nothing when nothing is selected
     * @param parent
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * When certain buttons are checked, change the total price based on it
     * @param compoundButton
     * @param b
     */
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if(b){
            switch (compoundButton.getId()){
                case R.id.rb_size_small:
                    pizza.setSize(Size.SMALL);
                    break;
                case R.id.rb_size_large:
                    pizza.setSize(Size.LARGE);
                    break;
                case R.id.rb_size_modium:
                    pizza.setSize(Size.MEDIUM);
                    break;
            }
        }
        switch (compoundButton.getId()){
            case R.id.cb_extra_sauce:
                pizza.setExtraSauce(b);
                break;
            case R.id.cb_extra_chesse:
                pizza.setExtraCheese(b);
                break;
        }
        setPrice();
    }

    /**
     * After clicking on add to order, show a window of confirmation
     * @param view
     */
    @Override
    public void onClick(View view) {
        if(pizza.getToppings().size()<3)
        {
            Toast.makeText(BuildYourOwnActivity.this, "Need at least 3 toppings!", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(pizza.getToppings().size()>7)
        {
            Toast.makeText(BuildYourOwnActivity.this, "Cannot add more than 7 toppings!", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage( R.string.tips_confirm_order)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    /**
                     * When click on order, show messages
                     * @param dialog
                     * @param id
                     */
                    public void onClick(DialogInterface dialog, int id) {
                        if(order==null){
                            order=new Order();
                            order.setI(StoreOrders.getNextOrderNumber());
                            StoreOrders.getStoreOrders().addOrder(order);
                            order.setPizzas(pizzas);
                        }
                        pizzas.add(pizza);
                        Toast.makeText(BuildYourOwnActivity.this, "Order successful!", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}