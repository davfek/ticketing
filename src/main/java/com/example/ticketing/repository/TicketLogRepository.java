package com.example.ticketing.repository;

import com.example.ticketing.ticket.TicketLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketLogRepository extends JpaRepository<TicketLog,Long> {

}
