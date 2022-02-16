package com.delivery.system.controller;


import com.delivery.system.service.DeliveryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/deliver")
@CrossOrigin(value = "*")
public class DeliveryController {

    private static final Logger logger = Logger.getLogger(DeliveryController.class);

    @Autowired
    private DeliveryService deliveryService;
}
