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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;
//    private final LoginService loginService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
//        this.loginService=loginService;
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
//        return loginService.login(loginRequest);
//    }


    //working
    @GetMapping
    public ResponseEntity<List<Ticket>> findAll() {
        return ticketService.findAll();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Ticket> findById(@PathVariable("id") Long id) {
//        return ticketService.findById(id);
//    }


    //updated
    @GetMapping("/{id}")
    public TicketDTO fetchById(@PathVariable("id") Long id) {
        return ticketService.getTicketById(id);
    }


//    @PostMapping
//    public ResponseEntity<HttpStatus> createTicket(@RequestBody Ticket ticket) {
//        return ticketService.createTicket(ticket);
//    }


    //updated
    @PostMapping
    public ResponseEntity<Long> createTicket(@RequestBody TicketRequest ticketRequest){

        return ticketService.createTicket(ticketRequest.getDescription(),ticketRequest.getClient(),ticketRequest.getResponsible());

    }


    //updated and working
    @Secured("ROLE_ADMIN")
    @PutMapping("/log/{id}")
    public ResponseEntity<HttpStatus> addToTicketLog(@PathVariable("id") Long id,
                                                     @RequestBody String logEntry){
        return ticketService.addTicketLog(id,logEntry);
    }

    @PutMapping("/{id}/{newStatus}")
    public ResponseEntity<HttpStatus> updateTicketStatus(@PathVariable("id") Long id,
                                                         @PathVariable("newStatus") String newStatus){
        return ticketService.updateTicketStatus(id,newStatus);
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
