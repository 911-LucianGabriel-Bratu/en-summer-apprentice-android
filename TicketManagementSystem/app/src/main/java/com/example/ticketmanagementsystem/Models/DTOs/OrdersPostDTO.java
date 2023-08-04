package com.example.ticketmanagementsystem.Models.DTOs;

public class OrdersPostDTO {
    private Long eventID;
    private Long ticketCategoryID;
    private int numberOfTickets;

    public OrdersPostDTO(Long eventID, Long ticketID, int numberOfTickets) {
        this.eventID = eventID;
        this.ticketCategoryID = ticketID;
        this.numberOfTickets = numberOfTickets;
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Long getTicketID() {
        return ticketCategoryID;
    }

    public void setTicketID(Long ticketID) {
        this.ticketCategoryID = ticketID;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}
