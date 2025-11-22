package com.dileep.Streams.Hard.Model;

public class Transaction {
    String id;
    String userId;
    String type;  // e.g., "DEPOSIT", "WITHDRAWAL", "TRANSFER"
    double amount;
    String status; // e.g., "SUCCESS", "FAILED", "PENDING"
    // constructors, getters


    public Transaction() {
    }

    public Transaction(String id,String userId, String type, double amount, String status) {
        this.id = id;
        this.userId=userId;
        this.type = type;
        this.amount = amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", type='" + type + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}
