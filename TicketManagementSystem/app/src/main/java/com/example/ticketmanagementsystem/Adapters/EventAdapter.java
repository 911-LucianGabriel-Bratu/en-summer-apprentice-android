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
import com.example.ticketmanagementsystem.Models.Events;
import com.example.ticketmanagementsystem.R;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{
    private List<Events> eventList;

    private Context context;

    public EventAdapter(List<Events> eventList, Context context) {
        this.eventList = eventList;
        this.context = context;
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
