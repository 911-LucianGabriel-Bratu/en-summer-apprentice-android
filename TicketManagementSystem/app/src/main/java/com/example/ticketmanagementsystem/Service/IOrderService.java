package com.example.ticketmanagementsystem.Service;

import com.example.ticketmanagementsystem.Models.DTOs.OrdersDTO;
import com.example.ticketmanagementsystem.Models.DTOs.OrdersPostDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface IOrderService {
    @GET("/api/orders/dtos")
    Call<List<OrdersDTO>> fetchAllOrders();

    @POST("/api/orders/customer/{customerID}")
    Call<Object> placeOrder(@Path("customerID") Long customerID, @Body OrdersPostDTO ordersPostDTO);

    @DELETE("/api/orders/{orderID}")
    Call<Object> cancelOrder(@Path("orderID") Long orderID);

    @PUT("/api/orders/{orderID}/ticketCategory/{ticketCategoryType}/amount/{numberOfTickets}")
    Call<Object> updateOrder(@Path("orderID") Long orderID, @Path("ticketCategoryType") String ticketCategoryType, @Path("numberOfTickets") int numberOfTickets);
}
