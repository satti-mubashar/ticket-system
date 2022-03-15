package com.delivery.system.service;

import com.delivery.system.enums.DeliveryStatus;
import com.delivery.system.model.Delivery;
import com.delivery.system.repository.DeliveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryService {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryService.class);

    @Autowired
    private DeliveryRepository deliveryRepository;

    public List<Delivery> getAllUnDeliverdDelivery(DeliveryStatus deliveryStatus){
        logger.info("Get delivereis");
        return deliveryRepository.findAllByDeliveryStatusNot(deliveryStatus);
    }

    public void saveAllDeliveries(List<Delivery> deliveries){
        deliveryRepository.saveAll(deliveries);
    }

}