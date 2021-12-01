package com.example.cscb07projectcode;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.MyViewHolder> {

    private ArrayList<OrderMetaData> ordersList;
    private RecyclerViewClickListener listener;

    // each adapter requires an array list of stores on instantiation
    public OrderRecyclerAdapter(ArrayList<OrderMetaData> ordersList, RecyclerViewClickListener listener){
        this.ordersList = ordersList;
        this.listener=listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView orderIdView;
        private TextView customerNameView;
//        private TextView totalPriceView;
        private TextView orderStatusView;

        public MyViewHolder(final View view){
            // pass the view to superclass
            super(view);
            // locate and store the view for store name and description
            orderIdView = view.findViewById(R.id.input_orderIdView);
            customerNameView = view.findViewById(R.id.input_customerNameView);
//            totalPriceView = view.findViewById(R.id.input_priceView);
            orderStatusView = view.findViewById(R.id.inputStatusView);
            // set view
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public OrderRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate the view with data
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_container, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerAdapter.MyViewHolder holder, int position) {
        // this method changes text in the views of the container

        String orderId = String.valueOf(ordersList.get(position).getOrderId());
        String customerName = ordersList.get(position).getCustomerId();
//        String totalPrice = String.valueOf(ordersList.get(position).computeTotalPrice());
        Log.i("pricerino", "passed through viewholder");
        String orderStatus = ordersList.get(position).getOrderStatus();

        holder.orderIdView.setText(orderId);
        holder.customerNameView.setText(customerName);
        holder.orderStatusView.setText(orderStatus);
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public interface RecyclerViewClickListener {
        void onClick(View v, int position);
    }
}

