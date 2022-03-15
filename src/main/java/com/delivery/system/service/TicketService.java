package com.delivery.system.service;

import com.delivery.system.enums.DeliveryPriority;
import com.delivery.system.enums.DeliveryStatus;
import com.delivery.system.enums.TicketType;
import com.delivery.system.model.Delivery;
import com.delivery.system.model.Ticket;
import com.delivery.system.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    private TicketRepository ticketRepository;


    @Autowired
    private DeliveryService deliveryService;

    public Page<Ticket> getAllTicket(Pageable pageable) {
        return ticketRepository.findAll(pageable);
    }


    public List<Delivery> adjustPriority() {

        List<Delivery> deliveries = deliveryService.getAllUnDeliverdDelivery(DeliveryStatus.ORDER_DELIVERED);
        setPriorityOfDeliveries(deliveries);
        return deliveries;
    }

    public Ticket createTicket(DeliveryPriority deliveryPriority, Delivery delivery) {
        Ticket ticket = new Ticket();
        ticket.setPrioirty(deliveryPriority);
        ticket.setDelivery(delivery);
        ticket.setTicketType(TicketType.OPEN);
        return ticket;
    }

    @Async
    public void setPriorityOfDeliveries(List<Delivery> deliveries) {
        for (Delivery delivery : deliveries) {
            Set<Ticket> ticketSet = null;
            switch (delivery.getCustomerType()) {
                case VIP:
                    logger.info("Delivery with VIP customer");
                    delivery.setPrioirty(DeliveryPriority.HIGH);
                    ticketSet = new HashSet<>();
                    ticketSet.add(createTicket(DeliveryPriority.HIGH, delivery));
                    delivery.setTicketSet(ticketSet);
                    continue;
            }

            // (Order received, Order Preparing, Order Pickedup, Order Delivered
            if (new Date().after(Date.from(delivery.getExpectedDeliveryTime()))
                    && !DeliveryStatus.ORDER_DELIVERED.equals(delivery.getDeliveryStatus())) {
                logger.info("Delivery time passes and status is not delivered");
                delivery.setPrioirty(DeliveryPriority.HIGH);
                ticketSet = new HashSet<>();
                ticketSet.add(createTicket(DeliveryPriority.HIGH, delivery));
                delivery.setTicketSet(ticketSet);
                continue;
            }

            switch (delivery.getDeliveryStatus()) {
                case ORDER_DELIVERED:
                    break;
                default:
                    Long minutes = delivery.getMeanTimeToPrepareMins();
                    Date timeToReach = Date.from(delivery.getTimeToReachDistance());
                    final long ONE_MINUTE_IN_MILLIS = 60000;

                    long timeToReachAfterAddingFoodPreparationMinutes = timeToReach.getTime()
                            + (minutes * ONE_MINUTE_IN_MILLIS);
                    logger.info("Delivery estimation is greater than the expected time ");
                    Date estimatedTime = new Date(timeToReachAfterAddingFoodPreparationMinutes);
                    if (estimatedTime.after(Date.from(delivery.getExpectedDeliveryTime()))) {
                        delivery.setPrioirty(DeliveryPriority.HIGH);
                        ticketSet = new HashSet<>();
                        ticketSet.add(createTicket(DeliveryPriority.HIGH, delivery));
                        delivery.setTicketSet(ticketSet);
                        continue;
                    }
                    break;
            }

            delivery.setPrioirty(DeliveryPriority.LOW);
        }
    }

    @Async
    public void getDeliveriesAndAdjust() {
        List<Delivery> deliveries = adjustPriority();
        System.out.println("Saving filter data");
        logger.info("saving filtered deliveries");
        deliveryService.saveAllDeliveries(deliveries);
    }

}
