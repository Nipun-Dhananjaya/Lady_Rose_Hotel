package com.lady_rose.model;


    public class RoomBookings {
        private String bookingID;
        private String roomNo;
        private String packageNo;
        private String bookedDate;
        private String bookingStartDate;
        private String bookingEndDate;

        // Constructor
        public RoomBookings(){}
        public RoomBookings(String bookingID, String roomNo, String packageNo,
                            String bookedDate, String bookingStartDate, String bookingEndDate) {
            this.bookingID = bookingID;
            this.roomNo = roomNo;
            this.packageNo = packageNo;
            this.bookedDate = bookedDate;
            this.bookingStartDate = bookingStartDate;
            this.bookingEndDate = bookingEndDate;
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
    }


