package com.lady_rose.dto;



    public class SpaBookings {
        private String bookingID;
        private String packageNo;
        private String bookedDate;
        private String bookingDate;

        // Constructor
        public SpaBookings(){}
        public SpaBookings(String bookingID, String packageNo, String bookedDate, String bookingDate) {
            this.bookingID = bookingID;
            this.packageNo = packageNo;
            this.bookedDate = bookedDate;
            this.bookingDate = bookingDate;
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
    }


