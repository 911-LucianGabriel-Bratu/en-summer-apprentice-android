package com.example.ticketmanagementsystem.Service;

import com.example.ticketmanagementsystem.Models.DTOs.OrdersDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IOrderService {
    @GET("/api/orders/dtos")
    Call<List<OrdersDTO>> fetchAllOrders();
}
