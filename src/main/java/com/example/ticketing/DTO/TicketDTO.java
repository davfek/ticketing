package com.example.ticketing.DTO;

import com.example.ticketing.ticket.Ticket;
import com.example.ticketing.ticket.TicketStatus;

import java.time.LocalDateTime;
import java.util.List;

public class TicketDTO {
    private Long id;
    private LocalDateTime created;
    private String description;
    private TicketStatus ticketStatus;
    private List<String> ticketLogs;
    private List<TicketPersonDTO> personIds;

    public TicketDTO(Ticket ticket) {
        this.id = ticket.getId();
        this.created = ticket.getCreated();
        this.description = ticket.getDescription();
        this.ticketStatus = ticket.getTicketStatus();
    }

    public TicketDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketStatus getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(TicketStatus ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public List<String> getTicketLogs() {
        return ticketLogs;
    }

    public void setTicketLogs(List<String> ticketLogs) {
        this.ticketLogs = ticketLogs;
    }

    public List<TicketPersonDTO> getPersonIds() {
        return personIds;
    }

    public void setPersonIds(List<TicketPersonDTO> personIds) {
        this.personIds = personIds;
    }
}
