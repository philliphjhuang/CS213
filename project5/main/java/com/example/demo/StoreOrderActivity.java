package com.example.demo;

/**
 * @author Yucong Liu, Phillip Huang
 */

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.demo.entity.order.Order;
import com.example.demo.entity.order.StoreOrders;
import java.util.ArrayList;
import java.util.List;

public class StoreOrderActivity extends AppCompatActivity implements  CurrentOrderAdapter.OnItemClickListener  {
    ListView rv_list;
    CurrentOrderAdapter  adapter;
    List<Order> datas;

    /**
     * Initialize the store order interface
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order);
        rv_list=findViewById(R.id.rv_list);
        datas=new ArrayList<>();
        datas.addAll(StoreOrders.getStoreOrders().getHistoryOrders());
        adapter = new CurrentOrderAdapter(this,this, datas,false);
        rv_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * Adding items to order
     * @param order
     */
    public void onItemAdd(Order order) {
        try {
            datas.remove(order);
            adapter.notifyDataSetChanged();
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Cancelling items in the order
     * @param order
     */
    public void onItemCancel(Order order) {
        try {
            datas.remove(order);
            adapter.notifyDataSetChanged();
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}