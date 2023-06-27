package com.example.ticketing.config;

import com.example.ticketing.ticket.Ticket;
import com.example.ticketing.ticket.TicketStatus;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;

@Configuration
@SpringBootApplication
@EnableWebMvc
public class TicketConfig {

    @Bean
    public Ticket defaultTicket(){
        Ticket ticket=new Ticket();
        ticket.setCreated(LocalDateTime.now());
        ticket.setDescription("Default ticket description");
        ticket.setTicketStatus(TicketStatus.CREATED);

        return ticket;
    }
}
