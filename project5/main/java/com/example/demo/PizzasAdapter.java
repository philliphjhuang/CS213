package com.example.demo;

/**
 * @author Yucong Liu, Phillip Huang
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.demo.entity.pizza.IResourceImg;
import com.example.demo.entity.pizza.Pizza;
import com.example.demo.pizieenum.PizzasType;
import com.example.demo.pizieenum.Topping;
import java.util.HashMap;

public class PizzasAdapter extends RecyclerView.Adapter<PizzasAdapter.ViewHolder> {
    Context mContext;
    HashMap<String, Pizza> datas;

    /**
     * Constructor
     * @param mContext
     * @param onItemClickListener
     * @param datas
     */
    public PizzasAdapter(Context mContext, OnItemClickListener onItemClickListener, HashMap<String,Pizza> datas) {
        this.datas = datas;
        this.mContext=mContext;
        this.mOnItemClickListener=onItemClickListener;
    }

    /**
     * Set up the ViewHolder for pizzas
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param i The view type of the new View.
     *
     * @return viewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizzas, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(view, (int) view.getTag());
                }
            }
        });
        return viewHolder;
    }


    private OnItemClickListener mOnItemClickListener = null;

    /**
     * Setter for when an item is clicked
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * Listener for when an item is clicked
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * ViewHolder
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Pizza pizza = datas.get(PizzasType.values()[position].toString());
        holder.tv_name.setText(PizzasType.values()[position].toString());
        holder.ll_topping.removeAllViews();
        if(pizza instanceof IResourceImg){
            holder.iv_img.setImageResource(((IResourceImg) pizza).getResourceImg());
        }
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if(pizza.getToppings()!=null) {
            for (Topping topping : pizza.getToppings()) {
                View view = inflater.inflate(R.layout.item_topping, null);
                ((TextView) view.findViewById(R.id.tv_topping_name)).setText(topping.name());
                holder.ll_topping.addView(view);
            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            /**
             * Set listener when an item is clicked
             * @param view
             */
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(view, position);
            }
        });
    }

    /**
     * Getter for item size
     * @return
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_img;
        TextView tv_name;
        LinearLayout ll_topping;

        /**
         * Constructor
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_img = itemView.findViewById(R.id.iv_img);
            ll_topping = itemView.findViewById(R.id.ll_topping);
        }
    }
}
