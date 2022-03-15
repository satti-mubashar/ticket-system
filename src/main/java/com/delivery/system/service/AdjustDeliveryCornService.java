package com.delivery.system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



@Service
public class AdjustDeliveryCornService {

    private static final Logger logger = LoggerFactory.getLogger(AdjustDeliveryCornService.class);

    @Autowired
    private TicketService ticketService;


     @Scheduled(cron =  "${delivery.corn}")
    public void adjustAllDeliveryWithStatusNotDelivered() {
        logger.info("Call Adjust priority  cron job");
        ticketService.getDeliveriesAndAdjust();
    }
}
