package com.example.demo;

/**
 * @author Yucong Liu, Phillip Huang
 */

import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.entity.order.Order;
import com.example.demo.entity.order.StoreOrders;
import com.example.demo.entity.pizza.Pizza;
import java.util.List;

public class CurrentOrderAdapter extends BaseAdapter {
    Context mContext;
    List<Order> datas;
    LayoutInflater inflater;
    boolean isoperate = false;

    /**
     * Constructor
     * @param mContext
     * @param onItemClickListener
     * @param datas
     * @param isoperate
     */
    public CurrentOrderAdapter(Context mContext, OnItemClickListener onItemClickListener, List<Order> datas,boolean isoperate) {
        this.isoperate=isoperate;
        this.datas = datas;
        this.mContext=mContext;
        this.mOnItemClickListener=onItemClickListener;
        this.inflater = LayoutInflater.from(mContext);
    }

    private OnItemClickListener mOnItemClickListener = null;

    /**
     * Getter for the current order size
     * @return
     */
    @Override
    public int getCount() {
        return datas.size();
    }

    /**
     * Getter for the item
     * @param position
     * @return order Object
     */
    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    /**
     * Getter for order ID
     * @param position
     * @return long
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Set the view for current orders
     * @param position
     * @param convertView
     * @param parent
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;Order order = datas.get(position);double price = 0;
        if (view == null) view = inflater.inflate(R.layout.item_order,parent,false);
        for (Pizza pizza:order.getPizzas()){price+=pizza.price();}
        TextView tv_order_id=view.findViewById(R.id.tv_order_id);TextView tv_price=view.findViewById(R.id.tv_price);
        Button bt_remove_pizza=view.findViewById(R.id.bt_remove_pizza);Button bt_place_order=view.findViewById(R.id.bt_place_order);
        ListView lv_order_pizzas=view.findViewById(R.id.lv_order_pizzas);OrderItemAdapter adapter=new OrderItemAdapter(mContext,order.getPizzas());
        lv_order_pizzas.setAdapter(adapter);adapter.notifyDataSetChanged();
        tv_order_id.setText(order.getI()+"");tv_price.setText(String.format("%.2f",price));setListViewHeightBasedOnChildren(lv_order_pizzas,adapter);
        bt_remove_pizza.setOnClickListener(new View.OnClickListener() {
            /**
             * Cancel an order when it is clicked
             * @param view
             */
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(R.string.really_to_remove).setPositiveButton(R.string.confirm,new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id) {
                        StoreOrders.getStoreOrders().getOrders().remove(order);
                        if (mOnItemClickListener != null) mOnItemClickListener.onItemCancel(order);
                        Toast.makeText(mContext, "Remove successful!", Toast.LENGTH_SHORT).show();dialog.cancel();
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            /**
                             * Remove window when clicked button
                             * @param dialog
                             * @param id
                             */
                            public void onClick(DialogInterface dialog, int id) {dialog.cancel();}
                        });
                AlertDialog dialog = builder.create(); dialog.show();
            }
        });
        if(isoperate){
            bt_place_order.setOnClickListener(new View.OnClickListener() {
                /**
                 * Show a window of order placed and have a confirmation button
                 * @param view
                 */
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage(R.string.tips_place_order).setPositiveButton(R.string.confirm,new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id) {
                                    order.setPlace(true);
                                    if(mOnItemClickListener!=null) mOnItemClickListener.onItemAdd(order);
                                    Toast.makeText(mContext, "Order placed successful!",Toast.LENGTH_SHORT).show();dialog.cancel();
                                }
                            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                                /**
                                 * Cancel the window when clicked on button
                                 * @param dialog
                                 * @param id
                                 */
                                public void onClick(DialogInterface dialog, int id) {dialog.cancel();}
                            });
                    AlertDialog dialog = builder.create(); dialog.show();
                }
            });
        } else {
            bt_place_order.setVisibility(View.GONE);
        }
        return view;
    }

    /**
     * Listener for when an item in an order is clicked
     */
    public interface OnItemClickListener {
        void onItemAdd(Order order);

        void onItemCancel(Order order);
    }

    /**
     * Change height of the orderList based on the number of items in the list
     * @param listView
     * @param listAdapter
     */
    public void setListViewHeightBasedOnChildren(ListView listView, Adapter listAdapter) {
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight;
        listView.setLayoutParams(params);
    }
}
