package com.example.ticketing.service;

import com.example.ticketing.DTO.TicketDTO;
import com.example.ticketing.repository.TicketLogRepository;
import com.example.ticketing.repository.TicketRepository;
import com.example.ticketing.ticket.Ticket;
import com.example.ticketing.ticket.TicketLog;
import com.example.ticketing.ticket.TicketPerson;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final TicketLogRepository ticketLogRepository;
    private final IdAPIService idAPIService;
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public TicketService(TicketRepository ticketRepository,TicketLogRepository ticketLogRepository,IdAPIService idAPIService,EntityManager entityManager) {
        this.ticketRepository = ticketRepository;
        this.ticketLogRepository=ticketLogRepository;
        this.idAPIService=idAPIService;
        this.entityManager=entityManager;
    }

    public ResponseEntity<Long> createTicketTest(String description, String clientName,String responsibleName){
        PersonDTO clientDTO=idAPIService.getPersonByName(clientName);
        PersonDTO responsibleDTO=idAPIService.getPersonByName(responsibleName);

        System.out.println(clientDTO.getId());
        System.out.println(responsibleDTO.getId());

        Ticket ticket=new Ticket(description);
        TicketPerson client = new TicketPerson(ticket,clientDTO.getId(),true);
        TicketPerson responsible=new TicketPerson(ticket,responsibleDTO.getId(),false);

        ticket.getTicketPeople().add(client);
        ticket.getTicketPeople().add(responsible);

        ticketRepository.save(ticket);

        return new ResponseEntity<>(ticket.getId(),HttpStatus.OK);

    }

    public ResponseEntity<HttpStatus> addToTicketLog(Long id,String logEntry){
        Optional<Ticket> ticket = ticketRepository.findById(id);

        if (ticket.isPresent()) {
            TicketLog ticketLog=new TicketLog(ticket.get(),logEntry);
            ticket.get().getTicketLogs().add(ticketLog);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<Ticket>> findAll() {
        return new ResponseEntity<>(ticketRepository.findAll(), HttpStatus.OK);
    }

//    public ResponseEntity<Ticket> findById(Long id) {
//        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
//        if (optionalTicket.isPresent()) {
//            Ticket ticket=optionalTicket.get();
//            ticket.getTicketLogs().size();
//            ticket.getTicketPeople().size();
//            return new ResponseEntity<>(ticket, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

    public TicketDTO getTicketById(Long ticketId) {
        Ticket ticket = entityManager.find(Ticket.class, ticketId);
        TicketDTO ticketDTO = new TicketDTO(ticket);
        ticketDTO.setTicketLogs(ticket.getTicketLogs().stream().map(TicketLog::getLogEntry).collect(Collectors.toList()));
        ticketDTO.setPersonIds(ticket.getTicketPeople().stream().map(TicketPerson::getPersonId).collect(Collectors.toList()));
        return ticketDTO;
    }

    @Transactional
    public ResponseEntity<HttpStatus> addTicketLog(Long ticketId, String logEntry) {
        Ticket ticket = entityManager.find(Ticket.class, ticketId);

        if (ticket != null) {
            TicketLog ticketLog = new TicketLog(ticket, logEntry);
            ticket.getTicketLogs().add(ticketLog);
            entityManager.persist(ticketLog);
            return new ResponseEntity<>(HttpStatus.OK);
        }else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<HttpStatus> createTicket(Ticket ticket) {
        try {
            ticketRepository.save(ticket);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        try {
            ticketRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            ticketRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
