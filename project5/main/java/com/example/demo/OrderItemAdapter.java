package com.example.demo;

/**
 * @author Yucong Liu, Phillip Huang
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.demo.entity.pizza.IResourceImg;
import com.example.demo.entity.pizza.Pizza;
import com.example.demo.pizieenum.Topping;
import java.util.List;

public class OrderItemAdapter extends BaseAdapter {
    Context mContext;
    List<Pizza> datas;
    LayoutInflater inflater;

    /**
     * Constructor
     * @param mContext
     * @param datas
     */
    public OrderItemAdapter(Context mContext, List<Pizza> datas) {
        this.datas = datas;
        this.mContext=mContext;
        this.inflater = LayoutInflater.from(mContext);
    }

    /**
     * Getter for order size
     * @return
     */
    @Override
    public int getCount() {
        return datas.size();
    }

    /**
     * Getter for item in the list based on position
     * @param position
     * @return pizza Object
     */
    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    /**
     * Get the position of an item in the list
     * @param position
     * @return long
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Set the view for items
     * @param position
     * @param convertView
     * @param parent
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_order_pizzas, parent, false);
        }
        Pizza pizza = datas.get(position);
        ImageView iv_img=view.findViewById(R.id.img_pizzas);
        TextView tv_name=view.findViewById(R.id.tv_name);
        LinearLayout ll_topping=view.findViewById(R.id.ll_topping);
        tv_name.setText(pizza.getSize().name());
        ll_topping.removeAllViews();
        if(pizza instanceof IResourceImg){
           iv_img.setImageResource(((IResourceImg) pizza).getResourceImg());
        }
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if(pizza.getToppings()!=null) {
            for (Topping topping : pizza.getToppings()) {
                View viewsub = inflater.inflate(R.layout.item_topping, null);
                ((TextView) viewsub.findViewById(R.id.tv_topping_name)).setText(topping.name());
              ll_topping.addView(viewsub);
            }
        }
        return view;
    }
}
