package com.delivery.system.service;

import com.delivery.system.model.Delivery;
import com.delivery.system.repository.DeliveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AdjustDeliveryCornService {

    private static final Logger logger = LoggerFactory.getLogger(AdjustDeliveryCornService.class);

    @Autowired
    private DeliveryService deliveryService;


    //@Scheduled(cron =  "0 * * * * *")
    @Scheduled(cron =  "0 0/1 * * * *")
    public void adjustAllDeliveryWithStatusNotDelivered() {
        logger.info("Call Adjust priority  cron job");
        deliveryService.getDeliveriesAndAdjust();
    }
}
