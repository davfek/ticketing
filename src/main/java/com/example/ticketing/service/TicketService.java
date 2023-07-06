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


    @Autowired
    public TicketService(TicketRepository ticketRepository, TicketLogRepository ticketLogRepository, IdAPIService idAPIService) {
        this.ticketRepository = ticketRepository;
        this.ticketLogRepository = ticketLogRepository;
        this.idAPIService = idAPIService;
    }

    public Long createTicket(String description, String clientName, String responsibleName) {
        PersonDTO clientDTO = idAPIService.getPersonByName(clientName);
        PersonDTO responsibleDTO = idAPIService.getPersonByName(responsibleName);


        Ticket ticket = new Ticket(description);
        TicketPerson client = new TicketPerson(ticket, clientDTO.getId(), true);
        TicketPerson responsible = new TicketPerson(ticket, responsibleDTO.getId(), false);

        ticket.getTicketPeople().add(client);
        ticket.getTicketPeople().add(responsible);

        ticketRepository.save(ticket);

        return ticket.getId();

    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public TicketDTO getTicketById(Long ticketId) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if (ticket.isPresent()) {
            TicketDTO ticketDTO = new TicketDTO(ticket.get());
            ticketDTO.setTicketLogs(ticket.get().getTicketLogs().stream().map(TicketLog::getLogEntry).collect(Collectors.toList()));
            ArrayList<TicketPersonDTO> ticketPersonDTOS = new ArrayList<>();
            for (TicketPerson s : ticket.get().getTicketPeople()) {
                TicketPersonDTO ticketPersonDTO = new TicketPersonDTO(idAPIService.getPersonById(s.getPersonId()).getName(), s.getPersonId(), s.isClient());
                ticketPersonDTOS.add(ticketPersonDTO);
            }
            ticketDTO.setPersonIds(ticketPersonDTOS);
            return ticketDTO;
        }else return null;
    }

    @Transactional
    public boolean addTicketLog(Long ticketId, String logEntry) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);

        if (ticket.isPresent()) {
            TicketLog ticketLog = new TicketLog(ticket.get(), logEntry);
            ticket.get().getTicketLogs().add(ticketLog);
            ticketLogRepository.save(ticketLog);
            return true;
        } else return false;
    }

    public Ticket updateTicket(Long id, TicketDTO ticketDTO) {
        Optional<Ticket> oldTicket = ticketRepository.findById(id);
        if (oldTicket.isPresent()) {
            oldTicket.get().setDescription(ticketDTO.getDescription());
            updateTicketStatus(oldTicket.get().getId(), ticketDTO.getTicketStatus().toString());
            return ticketRepository.save(oldTicket.get());
        } else {
            return null;
        }
    }

    public Ticket updateTicketStatus(Long id, String newStatus) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        TicketStatus newTicketStatus = TicketStatus.defineStatusFromString(newStatus);

        if (ticket.isPresent() && ticket.get().isNewStatusValid(newTicketStatus)) {
            ticket.get().setTicketStatus(newTicketStatus);
            return ticketRepository.save(ticket.get());
        } else {
            return null;
        }
    }

    public boolean deleteById(Long id) {
        ticketRepository.deleteById(id);
        if (ticketRepository.findById(id).isPresent()) {
            return false;
        } else return true;
    }

    public boolean deleteAll() {
        ticketRepository.deleteAll();
        if (ticketRepository.findById(1L).isPresent()) {
            return false;
        } else return true;
    }


}
