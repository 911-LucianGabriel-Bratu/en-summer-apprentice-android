package com.example.ticketmanagementsystem.Models;

public class Events {
    private int imageResourceID;
    private String description;
    private String ticketCategory;
    private int numberOfTickets;

    public Events(int imageResourceID, String description, String ticketCategory, int numberOfTickets) {
        this.imageResourceID = imageResourceID;
        this.description = description;
        this.ticketCategory = ticketCategory;
        this.numberOfTickets = numberOfTickets;
    }

    public int getImageResourceID() {
        return imageResourceID;
    }

    public void setImageResourceID(int imageResourceID) {
        this.imageResourceID = imageResourceID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTicketCategory() {
        return ticketCategory;
    }

    public void setTicketCategory(String ticketCategory) {
        this.ticketCategory = ticketCategory;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}
