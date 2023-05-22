package com.example.ticketing.repository;

import com.example.ticketing.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
//    @Query
//    Optional<Ticket>

}
