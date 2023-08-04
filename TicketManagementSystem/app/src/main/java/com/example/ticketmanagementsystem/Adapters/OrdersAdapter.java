package com.example.ticketmanagementsystem.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketmanagementsystem.Models.DTOs.OrdersDTO;
import com.example.ticketmanagementsystem.Models.Orders;
import com.example.ticketmanagementsystem.R;
import com.example.ticketmanagementsystem.Service.IOrderService;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>{
    private List<Orders> ordersList;

    private List<OrdersDTO> ordersDTOS;

    private Context context;

    IOrderService orderService;

    public OrdersAdapter(List<Orders> ordersList, List<OrdersDTO> ordersDTOS, Context context) {
        this.ordersList = ordersList;
        this.ordersDTOS = ordersDTOS;
        this.context = context;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:80")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        orderService = retrofit.create(IOrderService.class);
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orders_cards_layout, parent, false);
        return new OrdersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Orders orders = ordersList.get(position);
        OrdersDTO ordersDTO = ordersDTOS.get(position);

        holder.customerNameTextView.setText(orders.getCustomerName());

        String[] ticketCategories = context.getResources().getStringArray(R.array.ticket_categories);
        ArrayAdapter<String> ticketCategoryAdapter = new ArrayAdapter<String>(holder.itemView.getContext(),
                android.R.layout.simple_spinner_item, ticketCategories);
        ticketCategoryAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        holder.ticketCategorySpinner.setAdapter(ticketCategoryAdapter);
        if(Objects.equals(orders.getTicketCategory(), "Standard")){
            holder.ticketCategorySpinner.setSelection(0);
        } else if (Objects.equals(orders.getTicketCategory(), "VIP")) {
            holder.ticketCategorySpinner.setSelection(1);
        }

        holder.orderedAtTextView.setText(orders.getOrderedAt().toString());
        holder.numberOfTicketsEditText.setText(orders.getNumberOfTickets() + "");
        holder.totalPriceTextView.setText(orders.getTotalPrice().toString());

        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                int numberOfTickets = Integer.parseInt(holder.numberOfTicketsEditText.getText().toString());
                Spinner spinner = holder.ticketCategorySpinner;
                String ticketType = spinner.getSelectedItem().toString();
                Call<Object> call = orderService.updateOrder(ordersDTO.getOrderID(), ticketType, numberOfTickets);
                try{
                    Response<Object> response = call.execute();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long orderID = ordersDTO.getOrderID();
                Call<Object> call = orderService.cancelOrder(orderID);
                try{
                    Response<Object> response = call.execute();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    static class OrdersViewHolder extends RecyclerView.ViewHolder {
        TextView customerNameTextView;

        Spinner ticketCategorySpinner;

        TextView orderedAtTextView;
        EditText numberOfTicketsEditText;

        TextView totalPriceTextView;

        Button updateButton;

        Button deleteButton;

        OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.textViewCustomerName);
            ticketCategorySpinner = itemView.findViewById(R.id.ticketCategorySpinner);
            orderedAtTextView = itemView.findViewById(R.id.textViewOrderedAt);
            numberOfTicketsEditText = itemView.findViewById(R.id.numberOfTicketsEditText);
            totalPriceTextView = itemView.findViewById(R.id.textViewTotalPrice);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}