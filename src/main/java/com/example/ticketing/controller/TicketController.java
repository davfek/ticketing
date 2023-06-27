package com.example.ticketing.controller;

import com.example.ticketing.DTO.TicketDTO;
import com.example.ticketing.service.LoginService;
import com.example.ticketing.service.TicketService;
import com.example.ticketing.ticket.Ticket;
import com.example.ticketing.ticket.TicketRequest;
import com.example.ticketing.user.AuthenticationResult;
import com.example.ticketing.user.LoginRequest;
import com.example.ticketing.user.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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


    @Secured("ROLE_INTERNAL")
    //working
    @GetMapping
    public ResponseEntity<List<Ticket>> findAll() {
        return ticketService.findAll();
    }


    @Secured("ROLE_INTERNAL")
    @GetMapping("/{id}")
    public TicketDTO fetchById(@PathVariable("id") Long id) {
        return ticketService.getTicketById(id);
    }


    @Secured({"ROLE_EXTERNAL"})
    @PostMapping()
    public ResponseEntity<Long> createTicket(@RequestBody TicketRequest ticketRequest) {

        return ticketService.createTicket(ticketRequest.getDescription(), ticketRequest.getClient(), ticketRequest.getResponsible());

    }

    @Secured("ROLE_INTERNAL")
    @PutMapping("/log/{id}")
    public ResponseEntity<HttpStatus> addToTicketLog(@PathVariable("id") Long id,
                                                     @RequestBody String logEntry) {
        return ticketService.addTicketLog(id, logEntry);
    }

    @Secured("ROLE_INTERNAL")
    @PutMapping("/id")
    public ResponseEntity<HttpStatus> updateTicket(@PathVariable("id")Long id,
                                                   @RequestBody TicketDTO ticketDTO){
        return ticketService.updateTicket(id,ticketDTO);
    }

    @Secured("ROLE_INTERNAL")
    @PutMapping("/{id}/{newStatus}")
    public ResponseEntity<HttpStatus> updateTicketStatus(@PathVariable("id") Long id,
                                                         @PathVariable("newStatus") String newStatus) {
        return ticketService.updateTicketStatus(id, newStatus);
    }

    @Secured("ROLE_INTERNAL")
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        return ticketService.deleteById(id);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll() {
        return ticketService.deleteAll();
    }

}
