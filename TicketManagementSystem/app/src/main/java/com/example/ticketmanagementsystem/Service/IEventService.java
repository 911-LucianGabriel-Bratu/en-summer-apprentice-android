package com.example.ticketmanagementsystem.Service;

import com.example.ticketmanagementsystem.Models.DTOs.EventsDTO;
import com.example.ticketmanagementsystem.Models.Events;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IEventService {
    @GET("/api/event")
    Call<List<EventsDTO>> fetchAllEvents();
}
