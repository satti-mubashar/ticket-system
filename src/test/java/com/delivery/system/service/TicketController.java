package com.delivery.system.service;


import com.delivery.system.enums.CustomerType;
import com.delivery.system.enums.DeliveryPriority;
import com.delivery.system.enums.DeliveryStatus;
import com.delivery.system.enums.TicketType;
import com.delivery.system.model.Delivery;
import com.delivery.system.model.Ticket;
import com.delivery.system.repository.DeliveryRepository;
import io.swagger.models.auth.In;
import org.junit.Assert;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TicketController {

    @InjectMocks
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
        when(ticketService.getAllTicket(paging)).thenReturn(pageResult);
    }
    @Test
    public void testAdjustPriorityVIP() {
        List<Delivery> deliveriesList = new ArrayList<>();

        deliveriesList.add(new Delivery(100l, CustomerType.NEW, DeliveryStatus.ORDER_DELIVERED, Instant.now(), 10, Instant.now(), 10l));
        deliveriesList.add(new Delivery(200l, CustomerType.VIP, DeliveryStatus.ORDER_DELIVERED,Instant.now(), 1, Instant.now(), 2l));
        deliveriesList = ticketService.setPriorityOfDeliveries(deliveriesList);
        Delivery delivery = deliveriesList.get(1);
        System.out.println(delivery.toString());
        Assert.assertNotNull(delivery);
        Assert.assertEquals(delivery.getCustomerType(), CustomerType.VIP);
        Assert.assertEquals(delivery.getPrioirty(), DeliveryPriority.HIGH);
    }

    @Test
    public void testAdjustPriorityNEWButDelayed() {
        List<Delivery> deliveriesList = new ArrayList<Delivery>();
        deliveriesList.add(new Delivery(1l, CustomerType.NEW, DeliveryStatus.ORDER_RECIEVED,Instant.EPOCH.minusMillis(60000), 10,
               Instant.now(), 10l));
        deliveriesList.add(new Delivery(2l, CustomerType.NEW, DeliveryStatus.ORDER_PREPARING, Instant.now(), 1, Instant.now(), 2l));

        deliveriesList = ticketService.setPriorityOfDeliveries(deliveriesList);
        Delivery delivery = deliveriesList.get(0);
        Assert.assertNotNull(delivery);
        Assert.assertEquals(delivery.getCustomerType(), CustomerType.NEW);
        Assert.assertEquals(delivery.getPrioirty(), DeliveryPriority.HIGH);
    }

    @Test
    public void testAdjustPriorityNEWButExceedExpectedTime() {
        List<Delivery> deliveriesList = new ArrayList<>();
        Delivery newDelivery = new Delivery(1l, CustomerType.NEW, DeliveryStatus.ORDER_RECIEVED,Instant.now(), 10, Instant.now(), 10l);
        newDelivery.setMeanTimeToPrepareMins(120l);
        newDelivery.setTimeToReachDistance(Instant.now());
        newDelivery.setExpectedDeliveryTime(new Date(System.currentTimeMillis() + 60000).toInstant());
        deliveriesList.add(newDelivery);
        deliveriesList.add(new Delivery(2l, CustomerType.VIP, DeliveryStatus.ORDER_PREPARING, Instant.now(), 1, Instant.now(), 2l));

        deliveriesList = ticketService.setPriorityOfDeliveries(deliveriesList);
        Delivery delivery = deliveriesList.get(0);
        Assert.assertNotNull(delivery);
        Assert.assertEquals(delivery.getCustomerType(), CustomerType.NEW);
        Assert.assertEquals(delivery.getPrioirty(), DeliveryPriority.HIGH);
    }
    @Test
    public void testAdjustPriorityNEWButLowPriority() {
        // (Order received, Order Preparing, Order Pickedup, Order Delivered
        List<Delivery> deliveriesList = new ArrayList<>();
        Delivery newDelivery = new Delivery(1l, CustomerType.NEW, DeliveryStatus.ORDER_RECIEVED, Instant.now(), 10, Instant.now(), 10l);
        newDelivery.setMeanTimeToPrepareMins(120l);
        newDelivery.setTimeToReachDistance(Instant.now());
        newDelivery.setExpectedDeliveryTime(new Date(System.currentTimeMillis() + (1000 * 60000)).toInstant());
        deliveriesList.add(newDelivery);
        deliveriesList.add(new Delivery(2l, CustomerType.VIP, DeliveryStatus.ORDER_PREPARING, Instant.now(), 1, Instant.now(), 2l));
        deliveriesList = ticketService.setPriorityOfDeliveries(deliveriesList);
        Delivery delivery = deliveriesList.get(0);
        Assert.assertNotNull(delivery);
        Assert.assertEquals(delivery.getCustomerType(), CustomerType.NEW);
        Assert.assertEquals(delivery.getPrioirty(), DeliveryPriority.LOW);
    }
}
