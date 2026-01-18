package com.lady_rose.dto;

public class Payment {
    private String payId;
    private String amount;
    private String paymentMethod;   // "Cash" / "Card"
    private String paymentStatus;   // "Paid" / "Pending"
    private String paymentDate;

    public Payment() {

    }
    public Payment(String payId, String amount, String paymentMethod, String paymentStatus, String paymentDate) {
        this.payId = payId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public String getAmount() { return amount; }

    public String getPaymentMethod() { return paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }

    public String getPaymentDate() { return paymentDate; }

    public void setAmount(String amount) { this.amount = amount; }

    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }
}
