package com.delivery.system.controller;


import com.delivery.system.model.dto.ResponseWrapper;
import com.delivery.system.service.TicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(value = "*")
public class TicketController {

    private static final Logger logger = Logger.getLogger(TicketController.class);

    @Autowired
    TicketService ticketService;


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/tickets")
    public ResponseEntity<?> getAllTickets(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(
                new ResponseWrapper(true, ticketService.getAllTicket(paging),
                        HttpStatus.OK.value()));
    }
}
