package com.lady_rose.dto.TM;

public class RoomBookingTM {
    private String bookingId;
    private String custId;
    private String custName;
    private String roomNo;
    private String roomType;
    private String bookFrom;
    private String duration;
    private String cost;
    private String bookedOn;
    private String availability;


    public RoomBookingTM() {

    }
    public RoomBookingTM(String bookingId, String custId, String custName, String roomNo, String roomType,
                         String bookFrom, String duration, String cost, String bookedOn, String availability) {
        this.bookingId = bookingId;
        this.custId = custId;
        this.custName = custName;
        this.roomNo = roomNo;
        this.roomType = roomType;
        this.bookFrom = bookFrom;
        this.duration = duration;
        this.cost = cost;
        this.bookedOn = bookedOn;
        this.availability = availability;
    }
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getBookFrom() {
        return bookFrom;
    }

    public void setBookFrom(String bookFrom) {
        this.bookFrom = bookFrom;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getBookedOn() {
        return bookedOn;
    }

    public void setBookedOn(String bookedOn) {
        this.bookedOn = bookedOn;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
