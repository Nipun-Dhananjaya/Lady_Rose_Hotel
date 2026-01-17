package com.lady_rose.dto;


    public class RoomBookings {
        private String bookingID;
        private String customerId;
        private String roomNo;
        private String packageNo;
        private String bookedDate;
        private String bookingStartDate;
        private String bookingEndDate;

        private String amount;
        private String paymentMethod;   // "Cash" / "Card"
        private String paymentStatus;   // "Paid" / "Pending"
        private String paymentDate;

        // Constructor
        public RoomBookings(String bookingId, String customerId, String roomNo, String packageNo, String bookedDate, String bookingStartDate, String bookingEndDate, String amount, String paymentMethod, String paymentStatus, String paymentDate){}
        public RoomBookings(String bookingID, String roomNo, String packageNo,
                            String bookedDate, String bookingStartDate, String bookingEndDate,String amount,String paymentMethod,String paymentStatus,String paymentDate) {
            this.bookingID = bookingID;
            this.roomNo = roomNo;
            this.packageNo = packageNo;
            this.bookedDate = bookedDate;
            this.bookingStartDate = bookingStartDate;
            this.bookingEndDate = bookingEndDate;
            this.amount=amount;
            this.paymentMethod=paymentMethod;
            this.paymentStatus=paymentStatus;
            this.paymentDate=paymentDate;
        }

        // Getters
        public String getBookingID() {
            return bookingID;
        }

        public String getRoomNo() {
            return roomNo;
        }

        public String getPackageNo() {
            return packageNo;
        }

        public String getBookedDate() {
            return bookedDate;
        }

        public String getBookingStartDate() {
            return bookingStartDate;
        }

        public String getBookingEndDate() {
            return bookingEndDate;
        }

        public String getAmount() { return amount; }

        public String getPaymentMethod() { return paymentMethod; }

        public String getPaymentStatus() { return paymentStatus; }

        public String getPaymentDate() { return paymentDate; }


        // Setters
        public void setBookingID(String bookingID) {
            this.bookingID = bookingID;
        }

        public void setRoom_No(String room_No) {
            this.roomNo = roomNo;
        }

        public void setPackage_No(String package_No) {
            this.packageNo = packageNo;
        }

        public void setBookedDate(String bookedDate) {
            this.bookedDate = bookedDate;
        }

        public void setBookingStartDate(String bookingStartDate) {
            this.bookingStartDate = bookingStartDate;
        }

        public void setBookingEndDate(String bookingEndDate) {
            this.bookingEndDate = bookingEndDate;
        }

        public void setAmount(String amount) { this.amount = amount; }

        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

        public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

        public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

        public String getCustomerId() { return customerId; }
        public void setCustomerId(String customerId) { this.customerId = customerId; }
    }


