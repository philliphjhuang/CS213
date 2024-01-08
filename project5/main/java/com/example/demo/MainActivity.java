package com.example.demo;

/**
 * @author Yucong Liu, Phillip Huang
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements   View.OnClickListener  {

    /**
     * Set the four buttons for specialty pizza, current order, store order, and build your own
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findViewById(R.id.rl_specialty_pizzas).setOnClickListener(this);
        this.findViewById(R.id.rl_current_order).setOnClickListener(this);
        this.findViewById(R.id.rl_store_order).setOnClickListener(this);
        this.findViewById(R.id.rl_build_your_own).setOnClickListener(this);
    }

    /**
     * Go to a new interface based on the button clicked
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.rl_specialty_pizzas:
                intent = new Intent(this, SpecialtyPizzasActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_current_order:
                intent = new Intent(this, CurrentOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_store_order:
                intent = new Intent(this, StoreOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_build_your_own:
                intent = new Intent(this, BuildYourOwnActivity.class);
                startActivity(intent);
                break;
        }
    }
}