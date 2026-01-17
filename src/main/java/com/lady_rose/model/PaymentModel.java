package com.lady_rose.model;

import com.lady_rose.dto.RoomBookings;
import com.lady_rose.dto.SpaBookings;
import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentModel {


    public static boolean updateRoomBookingPayment(String bookingId, String amount,
                                                   String paymentDate, String method, String status) {
        try {
            return CrudUtil.execute(
                    "UPDATE RoomBooking SET amount=?, payment_date=?, payment_method=?, payment_status=? WHERE Booking_ID=?",
                    amount, paymentDate, method, status, bookingId
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateSpaBookingPayment(String bookingId, String amount,
                                                  String paymentDate, String method, String status) {
        try {
            return CrudUtil.execute(
                    "UPDATE SpaBooking SET amount=?, payment_date=?, payment_method=?, payment_status=? WHERE Booking_ID=?",
                    amount, paymentDate, method, status, bookingId
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static RoomBookings getRoomBookingById(String bookingId) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM RoomBooking WHERE Booking_ID = ?", bookingId);
        if (rs.next()) {
            return new RoomBookings(
                    rs.getString("booking_id"),
                    rs.getString("customer_id"),
                    rs.getString("room_no"),
                    rs.getString("package_no"),
                    rs.getString("booked_date"),
                    rs.getString("booking_start_date"),
                    rs.getString("booking_end_date"),
                    rs.getString("amount"),
                    rs.getString("payment_method"),
                    rs.getString("payment_status"),
                    rs.getString("payment_date")
            );
        }
        return null;
    }

    public static SpaBookings getSpaBookingById(String bookingId) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT * FROM SpaBooking WHERE Booking_ID = ?", bookingId);
        if (rs.next()) {
            return new SpaBookings(

                    rs.getString("booking_id"),
                    rs.getString("customer_id"),
                    rs.getString("package_no"),
                    rs.getString("booked_date"),
                    rs.getString("booking_date"),
                    rs.getString("amount"),
                    rs.getString("payment_method"),
                    rs.getString("payment_status"),
                    rs.getString("payment_date")
            );
        }
        return null;
    }

    // === NEW: GET ALL FOR TABLE VIEW ===
    public static List<RoomBookings> getAllRoomBookings() throws SQLException {
        List<RoomBookings> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT * FROM RoomBooking");
        while (rs.next()) {
            list.add(new RoomBookings(
                    rs.getString("booking_id"),
                    rs.getString("customer_id"),
                    rs.getString("room_no"),
                    rs.getString("package_no"),
                    rs.getString("booked_date"),
                    rs.getString("booking_start_date"),
                    rs.getString("booking_end_date"),
                    rs.getString("amount"),
                    rs.getString("payment_method"),
                    rs.getString("payment_status"),
                    rs.getString("payment_date")
            ));
        }
        return list;
    }

    public static List<SpaBookings> getAllSpaBookings() throws SQLException {
        List<SpaBookings> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT * FROM SpaBooking");
        while (rs.next()) {
            list.add(new SpaBookings(
                    rs.getString("booking_id"),
                    rs.getString("customer_id"),
                    rs.getString("package_no"),
                    rs.getString("booked_date"),
                    rs.getString("booking_date"),
                    rs.getString("amount"),
                    rs.getString("payment_method"),
                    rs.getString("payment_status"),
                    rs.getString("payment_date")
            ));
        }
        return list;
    }
}