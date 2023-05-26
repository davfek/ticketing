package com.example.ticketing.config;

import com.example.ticketing.repository.TicketRepository;
import com.example.ticketing.ticket.Ticket;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TicketConfig {

    @Bean
    CommandLineRunner commandLineRunner(TicketRepository ticketRepository){
        return args ->{
            Ticket ticket=new Ticket("test ticket","645f96c2d4a737420b79d4b7","645f96c2d4a737420b79d4b8");
            ticketRepository.save(ticket);
        };
    }
}
