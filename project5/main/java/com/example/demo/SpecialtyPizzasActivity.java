package com.example.demo;

/**
 * @author Yucong Liu, Phillip Huang
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demo.entity.pizza.Pizza;
import com.example.demo.pizieenum.PizzasType;
import com.example.demo.utils.PizzaMaker;
import java.util.HashMap;

public class SpecialtyPizzasActivity extends AppCompatActivity implements PizzasAdapter.OnItemClickListener {
    RecyclerView rv_list;
    HashMap<String, Pizza> datas;

    /**
     * Initialize the specialty pizzas interface
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_pizzas);
        inView();
    }

    /**
     * Initialize the view
     */
    public void inView(){
        try {
            rv_list=findViewById(R.id.rv_list);
            rv_list.setLayoutManager(new LinearLayoutManager(this));
            rv_list.setItemAnimator(new DefaultItemAnimator());
            datas=new HashMap<>();
            for (PizzasType value : PizzasType.values()) {
                PizzaMaker.createPizza(PizzasType.Deluxe.toString());
                datas.put(value.toString(),PizzaMaker.createPizza(value.toString()));
            }
            PizzasAdapter  adapter = new PizzasAdapter(this,this,datas);
            rv_list.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        catch (Exception ex)
        {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Change text and image based on the pizza type selected
     * @param view
     * @param position
     */
    public void onItemClick(View view, int position) {
       try {
           Pizza pizza = datas.get(PizzasType.values()[position].toString());
           Intent intent = new Intent(this, AddOrderActivity.class);
           intent.putExtra("pizza", pizza);
           intent.putExtra("pizza_name", PizzasType.values()[position].toString());
           startActivity(intent);
       } catch (Exception ex) {
           Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
       }
   }
}