package com.delivery.system.service;

import com.delivery.system.model.Ticket;
import com.delivery.system.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;


    public Page<Ticket> getAllTicket(Pageable pageable){
        return ticketRepository.findAll(pageable);
    }
}
