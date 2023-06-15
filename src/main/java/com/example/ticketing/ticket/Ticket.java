package com.example.ticketing.ticket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime created;
    private String description;
    private TicketStatus ticketStatus;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TicketLog> ticketLogs=new ArrayList<TicketLog>();

    @OneToMany(mappedBy = "ticket",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TicketPerson> ticketPeople=new ArrayList<>();

    public Ticket() {
    }

    public Ticket(String description) {
        this.description = description;
        this.created=LocalDateTime.now();
        this.ticketStatus=TicketStatus.CREATED;

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

    public List<TicketLog> getTicketLogs() {
        return ticketLogs;
    }

    public void setTicketLogs(List<TicketLog> ticketLogs) {
        this.ticketLogs = ticketLogs;
    }

    public List<TicketPerson> getTicketPeople() {
        return ticketPeople;
    }

    public void setTicketPeople(List<TicketPerson> ticketPeople) {
        this.ticketPeople = ticketPeople;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", created=" + created +
                ", description='" + description + '\'' +
                ", ticketStatus=" + ticketStatus +
                ", ticketLogs=" + ticketLogs +
                ", ticketPeople=" + ticketPeople +
                '}';
    }
}
