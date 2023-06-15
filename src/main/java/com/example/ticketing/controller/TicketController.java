package com.example.ticketing.controller;

import com.example.ticketing.DTO.TicketDTO;
import com.example.ticketing.service.TicketService;
import com.example.ticketing.ticket.Ticket;
import com.example.ticketing.ticket.TicketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> findAll() {
        return ticketService.findAll();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Ticket> findById(@PathVariable("id") Long id) {
//        return ticketService.findById(id);
//    }
    @GetMapping("/fetch/{id}")
    public TicketDTO fetchById(@PathVariable("id") Long id) {
        return ticketService.getTicketById(id);
    }


    @PostMapping
    public ResponseEntity<HttpStatus> createTicket(@RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> createTicketTest(@RequestBody TicketRequest ticketRequest){

        return ticketService.createTicketTest(ticketRequest.getDescription(),ticketRequest.getClient(),ticketRequest.getResponsible());

    }



    @PutMapping("/log/{id}")
    public ResponseEntity<HttpStatus> addToTicketLog(@PathVariable("id") Long id,
                                                     @RequestBody String logEntry){
        return ticketService.addTicketLog(id,logEntry);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        return ticketService.deleteById(id);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll(){
        return ticketService.deleteAll();
    }

}
