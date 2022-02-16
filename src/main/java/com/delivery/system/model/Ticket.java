package com.delivery.system.model;


import com.delivery.system.enums.DeliveryPriority;
import com.delivery.system.enums.TicketType;
import com.delivery.system.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity(name = "TICKET")
public class Ticket extends DateAudit {

    @Id
    @Column(name = "TICKET_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "TICKET_STATUS")
    @Enumerated(EnumType.STRING)
    private TicketType ticketType;

    @Column(name = "PRIORITY")
    @Enumerated(EnumType.STRING)
    private DeliveryPriority prioirty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DELIVERY_ID")
    @JsonManagedReference
    private Delivery delivery;

    public Ticket() {
    }

    public Ticket(Long id, TicketType ticketType, DeliveryPriority prioirty, Delivery delivery) {
        this.ticketType = ticketType;
        this.prioirty = prioirty;
        this.delivery = delivery;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public DeliveryPriority getPrioirty() {
        return prioirty;
    }

    public void setPrioirty(DeliveryPriority prioirty) {
        this.prioirty = prioirty;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }
}


