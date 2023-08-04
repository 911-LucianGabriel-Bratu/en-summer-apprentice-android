package com.example.ticketmanagementsystem.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketmanagementsystem.Models.DTOs.EventsDTO;
import com.example.ticketmanagementsystem.Models.DTOs.OrdersPostDTO;
import com.example.ticketmanagementsystem.Models.Events;
import com.example.ticketmanagementsystem.R;
import com.example.ticketmanagementsystem.Service.IEventService;
import com.example.ticketmanagementsystem.Service.IOrderService;

import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{
    private List<Events> eventList;

    private List<EventsDTO> eventsDTOS;

    private Context context;

    private IOrderService orderService;

    private IEventService eventService;

    public EventAdapter(List<Events> eventList, List<EventsDTO> eventsDTOS, Context context) {
        this.eventList = eventList;
        this.context = context;
        this.eventsDTOS = eventsDTOS;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:80")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        orderService = retrofit.create(IOrderService.class);
        eventService = retrofit.create(IEventService.class);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Events event = eventList.get(position);
        EventsDTO eventsDTO = eventsDTOS.get(position);

        holder.eventImageView.setImageResource(event.getImageResourceID());
        holder.descriptionTextView.setText(event.getDescription());
        String[] ticketCategories = context.getResources().getStringArray(R.array.ticket_categories);
        ArrayAdapter<String> ticketCategoryAdapter = new ArrayAdapter<String>(holder.itemView.getContext(),
                android.R.layout.simple_spinner_item, ticketCategories);
        ticketCategoryAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        holder.ticketCategorySpinner.setAdapter(ticketCategoryAdapter);

        holder.checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                long eventID = eventsDTO.getEventID();
                Spinner spinner = holder.ticketCategorySpinner;
                String ticketType = spinner.getSelectedItem().toString();
                Call<Long> call = eventService.fetchTicketCategoryID(eventID, ticketType);
                try{
                    Response<Long> response = call.execute();
                    Long ticketID = response.body();
                    EditText editText = (EditText) holder.numberOfTicketsEditText;
                    String contents = editText.getText().toString();

                    OrdersPostDTO ordersPostDTO = new OrdersPostDTO(eventID, ticketID, Integer.parseInt(contents));
                    Call<Object> objectCall = orderService.placeOrder(1L, ordersPostDTO);
                    try{
                        Response<Object> objectResponse = objectCall.execute();
                    }
                    catch (Exception ex1){
                        ex1.printStackTrace();
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    static class EventViewHolder extends RecyclerView.ViewHolder {
        ImageView eventImageView;
        TextView descriptionTextView;
        Spinner ticketCategorySpinner;
        EditText numberOfTicketsEditText;
        Button checkoutButton;

        EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventImageView = itemView.findViewById(R.id.image_view);
            descriptionTextView = itemView.findViewById(R.id.textViewEventDescription);
            ticketCategorySpinner = itemView.findViewById(R.id.ticketCategorySpinner);
            numberOfTicketsEditText = itemView.findViewById(R.id.numberOfTicketsEditText);
            checkoutButton = itemView.findViewById(R.id.checkoutButton);
        }
    }
}
