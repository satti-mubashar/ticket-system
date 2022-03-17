package com.delivery.system.service;

import com.delivery.system.enums.DeliveryPriority;
import com.delivery.system.enums.DeliveryStatus;
import com.delivery.system.enums.TicketType;
import com.delivery.system.model.Delivery;
import com.delivery.system.model.Ticket;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import com.delivery.system.enums.CustomerType;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryController {


    @InjectMocks
    TicketService ticketService;

    @InjectMocks
    Delivery delivery;

    @Test
    public void testVIPPriority() {
        List<Delivery> deliveriesList = new ArrayList<>();

        deliveriesList.add(new Delivery(100l, CustomerType.NEW, DeliveryStatus.ORDER_DELIVERED, Instant.now(),
                10, Instant.now(), 10l));
        deliveriesList.add(new Delivery(200l, CustomerType.VIP, DeliveryStatus.ORDER_DELIVERED,Instant.now(),
                1, Instant.now(), 2l));
        deliveriesList = ticketService.setPriorityOfDeliveries(deliveriesList);
        Delivery delivery = deliveriesList.get(1);
        System.out.println(delivery.toString());
        Assert.assertNotNull(delivery);
        Assert.assertEquals(delivery.getCustomerType(), CustomerType.VIP);
        Assert.assertEquals(delivery.getPrioirty(), DeliveryPriority.HIGH);
    }

    @Test
    public void testNEWAndDelayedPriority() {
        List<Delivery> deliveriesList = new ArrayList<Delivery>();
        deliveriesList.add(new Delivery(1l, CustomerType.NEW, DeliveryStatus.ORDER_RECIEVED,
                Instant.EPOCH.minusMillis(60000), 10,
                Instant.now(), 10l));
        deliveriesList.add(new Delivery(2l, CustomerType.NEW, DeliveryStatus.ORDER_PREPARING, Instant.now(),
                1, Instant.now(), 2l));

        deliveriesList = ticketService.setPriorityOfDeliveries(deliveriesList);
        Delivery delivery = deliveriesList.get(0);
        Assert.assertNotNull(delivery);
        Assert.assertEquals(delivery.getCustomerType(), CustomerType.NEW);
        Assert.assertEquals(delivery.getPrioirty(), DeliveryPriority.HIGH);
    }

    @Test
    public void testNEWAndExpectedTimeExceedPriority() {
        List<Delivery> deliveriesList = new ArrayList<>();
        Delivery newDelivery = new Delivery(1l, CustomerType.NEW, DeliveryStatus.ORDER_RECIEVED,Instant.now(),
                10, Instant.now(), 10l);
        newDelivery.setMeanTimeToPrepareMins(120l);
        newDelivery.setTimeToReachDistance(Instant.now());
        newDelivery.setExpectedDeliveryTime(new Date(System.currentTimeMillis() + 60000).toInstant());
        deliveriesList.add(newDelivery);
        deliveriesList.add(new Delivery(2l, CustomerType.VIP, DeliveryStatus.ORDER_PREPARING, Instant.now(),
                1, Instant.now(), 2l));

        deliveriesList = ticketService.setPriorityOfDeliveries(deliveriesList);
        Delivery delivery = deliveriesList.get(0);
        Assert.assertNotNull(delivery);
        Assert.assertEquals(delivery.getCustomerType(), CustomerType.NEW);
        Assert.assertEquals(delivery.getPrioirty(), DeliveryPriority.HIGH);
    }
    @Test
    public void testNEWWithLowPriority() {
        // (Order received, Order Preparing, Order Pickedup, Order Delivered
        List<Delivery> deliveriesList = new ArrayList<>();
        Delivery newDelivery = new Delivery(1l, CustomerType.NEW, DeliveryStatus.ORDER_RECIEVED,
                Instant.now(), 10, Instant.now(), 10l);
        newDelivery.setMeanTimeToPrepareMins(120l);
        newDelivery.setTimeToReachDistance(Instant.now());
        newDelivery.setExpectedDeliveryTime(new Date(System.currentTimeMillis() + (1000 * 60000)).toInstant());
        deliveriesList.add(newDelivery);
        deliveriesList.add(new Delivery(2l, CustomerType.VIP, DeliveryStatus.ORDER_PREPARING,
                Instant.now(), 1, Instant.now(), 2l));
        deliveriesList = ticketService.setPriorityOfDeliveries(deliveriesList);
        Delivery delivery = deliveriesList.get(0);
        Assert.assertNotNull(delivery);
        Assert.assertEquals(delivery.getCustomerType(), CustomerType.NEW);
        Assert.assertEquals(delivery.getPrioirty(), DeliveryPriority.LOW);
    }
}
