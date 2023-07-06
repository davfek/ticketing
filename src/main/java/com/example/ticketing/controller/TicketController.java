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
        List<Ticket> ticketList=ticketService.findAll();
        if (ticketList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else return new ResponseEntity<>(ticketList,HttpStatus.OK);
    }


    @Secured("ROLE_INTERNAL")
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> fetchById(@PathVariable("id") Long id) {
        TicketDTO ticketDTO= ticketService.getTicketById(id);
        if (ticketDTO!=null){
            return new ResponseEntity<>(ticketDTO,HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @Secured({"ROLE_EXTERNAL"})
    @PostMapping()
    public ResponseEntity<Long> createTicket(@RequestBody TicketRequest ticketRequest) {
        Long ticketId=ticketService.createTicket(ticketRequest.getDescription(), ticketRequest.getClient(), ticketRequest.getResponsible());
        if (ticketId>0){
            return new ResponseEntity<>(ticketId,HttpStatus.CREATED);
        }else return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

    }

    @Secured("ROLE_INTERNAL")
    @PutMapping("/log/{id}")
    public ResponseEntity<HttpStatus> addToTicketLog(@PathVariable("id") Long id,
                                                     @RequestBody String logEntry) {
        boolean isUpdated= ticketService.addTicketLog(id, logEntry);
        if (isUpdated){
            return new ResponseEntity<>(HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured("ROLE_INTERNAL")
    @PutMapping("/id")
    public ResponseEntity<Ticket> updateTicket(@PathVariable("id")Long id,
                                                   @RequestBody TicketDTO ticketDTO){
        Ticket ticket= ticketService.updateTicket(id,ticketDTO);
        if (ticket!=null){
            return new ResponseEntity<>(ticket,HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured("ROLE_INTERNAL")
    @PutMapping("/{id}/{newStatus}")
    public ResponseEntity<Ticket> updateTicketStatus(@PathVariable("id") Long id,
                                                         @PathVariable("newStatus") String newStatus) {
        Ticket ticket= ticketService.updateTicketStatus(id, newStatus);
        if (ticket!=null){
            return new ResponseEntity<>(ticket,HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured("ROLE_INTERNAL")
    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        if (ticketService.deleteById(id)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll() {
        if (ticketService.deleteAll()){
            return new ResponseEntity<>(HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
