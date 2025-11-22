package com.dileep.Streams.Hard.Model;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private String orderId;
    private String customerId;
    private List<Item> items;
    private LocalDate orderDate;
    // constructors, getters

    public Order() {
    }

    public Order(String orderId, String customerId, List<Item> items, LocalDate orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = items;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", items=" + items +
                ", orderDate=" + orderDate +
                '}';
    }
}
