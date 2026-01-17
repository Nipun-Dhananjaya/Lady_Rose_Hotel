package com.lady_rose.dto;



    public class SpaBookings {
        private String bookingID;
        private String customerId;
        private String packageNo;
        private String bookedDate;
        private String bookingDate;

        private String amount;
        private String paymentMethod;
        private String paymentStatus;
        private String paymentDate;

        // Constructor
        public SpaBookings(String bookingId, String customerId, String packageNo, String bookedDate, String bookingDate, String amount, String paymentMethod, String paymentStatus, String paymentDate){}
        public SpaBookings(String bookingID, String packageNo, String bookedDate, String bookingDate,String amount,String paymentMethod, String paymentStatus, String paymentDate) {
            this.bookingID = bookingID;
            this.packageNo = packageNo;
            this.bookedDate = bookedDate;
            this.bookingDate = bookingDate;

            this.amount=amount;
            this.paymentMethod=paymentMethod;
            this.paymentStatus=paymentStatus;
            this.paymentDate=paymentDate;
        }

        // Getters
        public String getBookingID() {
            return bookingID;
        }

        public String getPackageNo() {
            return packageNo;
        }

        public String getBookedDate() {
            return bookedDate;
        }

        public String getBookingDate() {
            return bookingDate;
        }

        public String getAmount() { return amount; }

        public String getPaymentMethod() { return paymentMethod; }

        public String getPaymentStatus() { return paymentStatus; }

        public String getPaymentDate() { return paymentDate; }

        // Setters
        public void setBookingID(String bookingID) {
            this.bookingID = bookingID;
        }

        public void setPackageNo(String packageNo) {
            this.packageNo = packageNo;
        }

        public void setBookedDate(String bookedDate) {
            this.bookedDate = bookedDate;
        }

        public void setBookingDate(String bookingDate) {
            this.bookingDate = bookingDate;
        }

        public void setAmount(String amount) { this.amount = amount; }

        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

        public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

        public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

        public String getCustomerId() { return customerId; }
        public void setCustomerId(String customerId) { this.customerId = customerId; }
    }


