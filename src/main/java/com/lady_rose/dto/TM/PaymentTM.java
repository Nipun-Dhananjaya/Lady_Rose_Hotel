package com.lady_rose.dto.TM;

public class PaymentTM {
    private String payId;
    private String amount;
    private String paymentMethod;   // "Cash" / "Card"
    private String paymentStatus;   // "Paid" / "Pending"
    private String paymentDate;
    private String custId;
    private String custName;

    public PaymentTM() {
    }
    public PaymentTM(String payId, String amount, String paymentMethod, String paymentStatus, String paymentDate, String custId, String custName) {
        this.payId = payId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.custId = custId;
        this.custName = custName;
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

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }
}
