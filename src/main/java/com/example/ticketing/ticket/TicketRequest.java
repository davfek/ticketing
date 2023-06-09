package com.example.ticketing.ticket;

public class TicketRequest {
    private String description;
    private String client;
    private String responsible;

    public TicketRequest() {
    }

    public TicketRequest(String description, String client, String responsible) {
        this.description = description;
        this.client = client;
        this.responsible = responsible;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
}
