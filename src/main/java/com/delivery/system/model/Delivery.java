package com.delivery.system.model;

import com.delivery.system.enums.CustomerType;
import com.delivery.system.enums.DeliveryPriority;
import com.delivery.system.enums.DeliveryStatus;
import com.delivery.system.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;
import java.util.StringJoiner;

@Entity(name = "DELIVERY")
public class Delivery extends DateAudit {

    @Id
    @Column(name = "DELIVERY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CUSTOMER_TYPE")
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Column(name ="EXPECTED_DELIVERY_TIME" ,nullable = false)
    private Instant expectedDeliveryTime;

    @Column(name = "CURRENT_DISTANCE")
    private int currentDistance;

    @Column(name = "RIDER_RATING")
    private int riderRating;

    @Column(name ="TIME_TO_REACH_DISTANCE" ,nullable = false)
    private Instant timeToReachDistance;

    @Column(name = "MEAN_TIME_TO_PREPARE_MINS")
    private Long meanTimeToPrepareMins;

    @Column(name = "DELIVERY_PRIORITY")
    @Enumerated(EnumType.STRING)
    private DeliveryPriority prioirty;

    @OneToMany(mappedBy = "delivery", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Ticket> ticketSet;

    public Delivery() {
    }

    public Delivery(Long deliveryId, CustomerType customerType, DeliveryStatus deliveryStatus, Instant expectedDeliveryTime,
                    int currentDistance, Instant timeToReachDestination, Long meanTimeToPrepareMins) {
        super();
        this.meanTimeToPrepareMins = meanTimeToPrepareMins;
        this.id = deliveryId;
        this.customerType = customerType;
        this.deliveryStatus = deliveryStatus;
        this.expectedDeliveryTime = expectedDeliveryTime;
        this.currentDistance = currentDistance;
        this.timeToReachDistance = timeToReachDestination;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(CustomerType customerType) {
        this.customerType = customerType;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Instant getExpectedDeliveryTime() {
        return expectedDeliveryTime;
    }

    public void setExpectedDeliveryTime(Instant expectedDeliveryTime) {
        this.expectedDeliveryTime = expectedDeliveryTime;
    }

    public int getCurrentDistance() {
        return currentDistance;
    }

    public void setCurrentDistance(int currentDistance) {
        this.currentDistance = currentDistance;
    }

    public Instant getTimeToReachDistance() {
        return timeToReachDistance;
    }

    public void setTimeToReachDistance(Instant timeToReachDistance) {
        this.timeToReachDistance = timeToReachDistance;
    }

    public Set<Ticket> getTicketSet() {
        return ticketSet;
    }

    public void setTicketSet(Set<Ticket> ticketSet) {
        this.ticketSet = ticketSet;
    }

    public int getRiderRating() {
        return riderRating;
    }

    public void setRiderRating(int riderRating) {
        this.riderRating = riderRating;
    }

    public DeliveryPriority getPrioirty() {
        return prioirty;
    }

    public void setPrioirty(DeliveryPriority prioirty) {
        this.prioirty = prioirty;
    }

    public Long getMeanTimeToPrepareMins() {
        return meanTimeToPrepareMins;
    }

    public void setMeanTimeToPrepareMins(Long meanTimeToPrepareMins) {
        this.meanTimeToPrepareMins = meanTimeToPrepareMins;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Delivery.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("customerType=" + customerType)
                .add("deliveryStatus=" + deliveryStatus)
                .add("expectedDeliveryTime=" + expectedDeliveryTime)
                .add("currentDistance=" + currentDistance)
                .add("riderRating=" + riderRating)
                .add("timeToReachDistance=" + timeToReachDistance)
                .add("meanTimeToPrepareMins=" + meanTimeToPrepareMins)
                .add("prioirty=" + prioirty)
                .add("ticketSet=" + ticketSet)
                .toString();
    }
}
