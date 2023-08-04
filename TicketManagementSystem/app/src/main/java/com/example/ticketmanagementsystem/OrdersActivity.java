package com.example.ticketmanagementsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ticketmanagementsystem.Adapters.OrdersAdapter;
import com.example.ticketmanagementsystem.Models.DTOs.EventsDTO;
import com.example.ticketmanagementsystem.Models.DTOs.OrdersDTO;
import com.example.ticketmanagementsystem.Models.Events;
import com.example.ticketmanagementsystem.Models.Orders;
import com.example.ticketmanagementsystem.Service.IEventService;
import com.example.ticketmanagementsystem.Service.IOrderService;
import com.google.android.material.navigation.NavigationView;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrdersActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    private List<Orders> ordersList;
    IOrderService orderService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_layout);
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
        orderService = retrofit.create(IOrderService.class);

        ordersList = new ArrayList<>();
        Call<List<OrdersDTO>> call = orderService.fetchAllOrders();
        try{
            Response<List<OrdersDTO>> response = call.execute();
            List<OrdersDTO> ordersDTOS = response.body();
            ordersList = ordersDTOS.stream()
                    .map(o -> new Orders(o.getCustomerName(), o.getTicketCategoryDescription(), o.getOrderedAt(), o.getNumberOfTickets(), o.getTotalPrice()))
                    .collect(Collectors.toList());
            OrdersAdapter ordersAdapter = new OrdersAdapter(ordersList, ordersDTOS, this);
            recyclerView.setAdapter(ordersAdapter);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        //populateOrdersListHardcoded(ordersList);

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

    public void populateOrdersListHardcoded(List<Orders> orders){
        Date date = new Date();
        Orders orders1 = new Orders("Marcel", "VIP", date, 10, new BigDecimal(1000));
        orders.add(orders1);
        Orders orders2 = new Orders("Marcel", "Standard", date, 20, new BigDecimal(2000));
        orders.add(orders2);
        Orders orders3 = new Orders("Marcel", "VIP", date, 2, new BigDecimal(200));
        orders.add(orders3);
        Orders orders4 = new Orders("Marcel", "Standard", date, 3, new BigDecimal(300));
        orders.add(orders4);
        Orders orders5 = new Orders("Marcel", "VIP", date, 10, new BigDecimal(1000));
        orders.add(orders5);

    }
}
