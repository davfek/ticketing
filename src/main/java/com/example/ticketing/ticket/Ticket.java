package com.example.ticketing.ticket;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table
public class Ticket {

    @Id
    @SequenceGenerator(
            name = "ticket_sequence",
            sequenceName = "ticket_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ticket_sequence"
    )
    private Long id;
    private LocalDateTime created;
    private String description;
    // to be a person
    private String responsible;
    // to be a person
    private String client;
    private List<String> ticketLog;
    private TicketStatus ticketStatus;

    public Ticket() {
    }

    public Ticket(String description, String responsible, String client) {
        this.description = description;
        this.responsible = responsible;
        this.client = client;
        this.created=LocalDateTime.now();
        this.ticketLog=new ArrayList<>();
        this.ticketStatus=TicketStatus.CREATED;

    }
}
