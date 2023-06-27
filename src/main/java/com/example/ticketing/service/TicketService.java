package com.example.ticketing.service;

import com.example.ticketing.DTO.TicketDTO;
import com.example.ticketing.DTO.TicketPersonDTO;
import com.example.ticketing.repository.TicketLogRepository;
import com.example.ticketing.repository.TicketRepository;
import com.example.ticketing.ticket.Ticket;
import com.example.ticketing.ticket.TicketLog;
import com.example.ticketing.ticket.TicketPerson;
import com.example.ticketing.ticket.TicketStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.dto.PersonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public TicketService(TicketRepository ticketRepository, TicketLogRepository ticketLogRepository, IdAPIService idAPIService, EntityManager entityManager) {
        this.ticketRepository = ticketRepository;
        this.ticketLogRepository = ticketLogRepository;
        this.idAPIService = idAPIService;
        this.entityManager = entityManager;
    }

    public ResponseEntity<Long> createTicket(String description, String clientName, String responsibleName) {
        PersonDTO clientDTO = idAPIService.getPersonByName(clientName);
        PersonDTO responsibleDTO = idAPIService.getPersonByName(responsibleName);

        System.out.println(clientDTO.getId());
        System.out.println(responsibleDTO.getId());

        Ticket ticket = new Ticket(description);
        TicketPerson client = new TicketPerson(ticket, clientDTO.getId(), true);
        TicketPerson responsible = new TicketPerson(ticket, responsibleDTO.getId(), false);

        ticket.getTicketPeople().add(client);
        ticket.getTicketPeople().add(responsible);

        ticketRepository.save(ticket);

        return new ResponseEntity<>(ticket.getId(), HttpStatus.OK);

    }

    public ResponseEntity<List<Ticket>> findAll() {
        return new ResponseEntity<>(ticketRepository.findAll(), HttpStatus.OK);
    }

    public TicketDTO getTicketById(Long ticketId) {
        Ticket ticket = entityManager.find(Ticket.class, ticketId);
        TicketDTO ticketDTO = new TicketDTO(ticket);
        ticketDTO.setTicketLogs(ticket.getTicketLogs().stream().map(TicketLog::getLogEntry).collect(Collectors.toList()));
        ArrayList<TicketPersonDTO> ticketPersonDTOS = new ArrayList<>();
        for (TicketPerson s : ticket.getTicketPeople()) {
            TicketPersonDTO ticketPersonDTO = new TicketPersonDTO(idAPIService.getPersonById(s.getPersonId()).getName(), s.getPersonId(), s.isClient());
            ticketPersonDTOS.add(ticketPersonDTO);
        }
        ticketDTO.setPersonIds(ticketPersonDTOS);
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
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<HttpStatus> updateTicket(Long id,TicketDTO ticketDTO){
        Optional<Ticket> oldTicket= ticketRepository.findById(id);
        if (oldTicket.isPresent()){
            oldTicket.get().setDescription(ticketDTO.getDescription());
            updateTicketStatus(oldTicket.get().getId(),ticketDTO.getTicketStatus().toString());
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<HttpStatus> updateTicketStatus(Long id, String newStatus) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        TicketStatus newTicketStatus;
        switch (newStatus.toLowerCase()) {
            case "inprogress":
                newTicketStatus=TicketStatus.IN_PROGRESS;
                break;
            case "blocked":
                newTicketStatus=TicketStatus.BLOCKED;
                break;
            case "resolved":
                newTicketStatus=TicketStatus.RESOLVED;
                break;
            default:
                newTicketStatus=TicketStatus.CREATED;
        }
        if (ticket.isPresent() && ticket.get().isNewStatusValid(newTicketStatus)) {
            ticket.get().setTicketStatus(newTicketStatus);
            ticketRepository.save(ticket.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<HttpStatus> deleteById(Long id) {
        try {
            ticketRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> deleteAll() {
        try {
            ticketRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
