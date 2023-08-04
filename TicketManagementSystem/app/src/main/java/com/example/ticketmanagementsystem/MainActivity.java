package com.example.ticketmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketmanagementsystem.Adapters.EventAdapter;
import com.example.ticketmanagementsystem.Models.DTOs.EventsDTO;
import com.example.ticketmanagementsystem.Models.Events;
import com.example.ticketmanagementsystem.Service.IEventService;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;

    IEventService eventService;
    private List<Events> eventsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_layout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close){};
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:80")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        eventService = retrofit.create(IEventService.class);

        eventsList = new ArrayList<>();
        Call<List<EventsDTO>> call = eventService.fetchAllEvents();
        try{
            Response<List<EventsDTO>> response = call.execute();
            List<EventsDTO> eventsDTOS = response.body();
            eventsList = eventsDTOS.stream()
                    .map(e -> new Events(R.drawable.lowbudget, e.getEventDescription(), "", 0))
                    .collect(Collectors.toList());

            EventAdapter eventAdapter = new EventAdapter(eventsList, eventsDTOS, this);
            recyclerView.setAdapter(eventAdapter);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.miDashboard) {
            Intent i = new Intent(this, MainActivity.class);
            this.startActivity(i);
            return true;
        } else if (item.getItemId() == R.id.miOrders) {
            Intent i = new Intent(this, OrdersActivity.class);
            this.startActivity(i);
            return true;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(toggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
