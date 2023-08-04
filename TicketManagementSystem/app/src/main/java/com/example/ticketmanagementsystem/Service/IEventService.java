package com.example.ticketmanagementsystem.Service;

import com.example.ticketmanagementsystem.Models.DTOs.EventsDTO;
import com.example.ticketmanagementsystem.Models.Events;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IEventService {
    @GET("/api/event")
    Call<List<EventsDTO>> fetchAllEvents();

    @GET("/api/event/{eventID}/ticketCategory/{ticketCategoryType}")
    Call<Long> fetchTicketCategoryID(@Path("eventID") Long eventID, @Path("ticketCategoryType") String ticketCategoryType);
}
