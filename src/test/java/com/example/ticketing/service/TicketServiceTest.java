package com.example.ticketing.service;

import com.example.ticketing.DTO.TicketDTO;
import com.example.ticketing.repository.TicketLogRepository;
import com.example.ticketing.repository.TicketRepository;
import com.example.ticketing.ticket.Ticket;
import org.dto.PersonDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketLogRepository ticketLogRepository;

    @Mock
    private IdAPIService idAPIService;

    private TicketService ticketService;

    @Captor
    private ArgumentCaptor<Ticket> ticketArgumentCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        ticketService = new TicketService(ticketRepository, ticketLogRepository, idAPIService);

        doAnswer(invocationOnMock -> {
            Ticket savedTicket = invocationOnMock.getArgument(0);
            return savedTicket;
        }).when(ticketRepository).save(ticketArgumentCaptor.capture());
    }

    @Test
    public void testCreateTicket() {
        String description = "Test description";
        String clientName = "Test client";
        String responsibleName = "Test responsible";

        PersonDTO clientDTO = new PersonDTO("ID1", "TESTCLIENT1", "TESTPHONENUMBER1", "TESTEMAIL1");
        when(idAPIService.getPersonByName(clientName)).thenReturn(clientDTO);

        PersonDTO responsibleDTO = new PersonDTO("ID2", "TESTCLIENT2", "TESTPHONENUMBER2", "TESTEMAIL2");
        when(idAPIService.getPersonByName(responsibleName)).thenReturn(responsibleDTO);

        Long ticketId = ticketService.createTicket(description, clientName, responsibleName);

        Ticket capturedTicket = ticketArgumentCaptor.getValue();

        assertNotNull(capturedTicket);
        assertEquals(description, capturedTicket.getDescription());
        assertEquals(ticketId, capturedTicket.getId());

        verify(ticketRepository, times(1)).save(any());
    }

    @Test
    public void testCreateTicketFail() {
        String description = "Test description";
        String clientName = "Test client";
        String responsibleName = "Test responsible";

        PersonDTO clientDTO = new PersonDTO("ID1", "TESTCLIENT1", "TESTPHONENUMBER1", "TESTEMAIL1");
        when(idAPIService.getPersonByName(clientName)).thenReturn(clientDTO);

        PersonDTO responsibleDTO = new PersonDTO("ID2", "TESTCLIENT2", "TESTPHONENUMBER2", "TESTEMAIL2");
        when(idAPIService.getPersonByName(responsibleName)).thenReturn(responsibleDTO);

        Long ticketId = ticketService.createTicket(description, clientName, responsibleName);

        Ticket capturedTicket = ticketArgumentCaptor.getValue();

        assertNotNull(capturedTicket);
        assertNotEquals("Different description", capturedTicket.getDescription());

        assertEquals(ticketId, capturedTicket.getId());

        verify(ticketRepository, times(1)).save(any());
    }
}

