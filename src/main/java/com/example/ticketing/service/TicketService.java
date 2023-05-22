package com.example.ticketing.service;

import com.example.ticketing.repository.TicketRepository;
import com.example.ticketing.ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public ResponseEntity<List<Ticket>> findAll(){
        return new ResponseEntity<>(ticketRepository.findAll(), HttpStatus.OK);
    }
}
