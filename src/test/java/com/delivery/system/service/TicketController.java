package com.delivery.system.service;


import com.delivery.system.enums.DeliveryPriority;
import com.delivery.system.enums.TicketType;
import com.delivery.system.model.Delivery;
import com.delivery.system.model.Ticket;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TicketController {

    @Mock
    TicketService ticketService;

    @InjectMocks
    Delivery delivery;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void getAllTickets( ) {
        Pageable paging = PageRequest.of(0, 5);
        List<Ticket> ticketList = new ArrayList<>();

        Ticket ticket1 = new Ticket(1l, TicketType.OPEN, DeliveryPriority.LOW,delivery);
        Ticket ticket2 = new Ticket(2l, TicketType.OPEN, DeliveryPriority.HIGH,delivery);
        Ticket ticket3 = new Ticket(3l, TicketType.OPEN, DeliveryPriority.LOW,delivery);
        Ticket ticket4 = new Ticket(4l, TicketType.OPEN, DeliveryPriority.HIGH,delivery);

        ticketList.add(ticket1);
        ticketList.add(ticket4);
        ticketList.add(ticket3);
        ticketList.add(ticket2);
        Page<Ticket> pageResult = new PageImpl<>(ticketList, paging, ticketList.size());
        Mockito.when(ticketService.getAllTicket(paging)).thenReturn(pageResult);

    }

}
