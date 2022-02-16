package com.delivery.system.repository;

import com.delivery.system.enums.DeliveryStatus;
import com.delivery.system.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery , Long> {

    List<Delivery> findAllByDeliveryStatusNot(DeliveryStatus deliveryStatus);
}
