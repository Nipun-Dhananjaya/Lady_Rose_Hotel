package com.lady_rose.model;

import com.lady_rose.dto.RoomBookings;
import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomBookingsModel {

    // Save booking
    public static boolean saveBooking(String bookingId,
                                      LocalDate bookedDate,
                                      LocalDate startDate,
                                      LocalDate endDate) {
        try {
            return CrudUtil.execute(
                    "INSERT INTO room_bookings VALUES (?,?,?,?)",
                    bookingId,
                    bookedDate,
                    startDate,
                    endDate
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update booking
    public static boolean updateBooking(String bookingId,
                                        LocalDate bookedDate,
                                        LocalDate startDate,
                                        LocalDate endDate) {
        try {
            return CrudUtil.execute(
                    "UPDATE room_bookings SET booked_date=?, start_date=?, end_date=? WHERE booking_id=?",
                    bookedDate,
                    startDate,
                    endDate,
                    bookingId
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all bookings
    public static List<RoomBookings> getAll() throws SQLException {
        ResultSet resultSet =
                CrudUtil.execute("SELECT * FROM room_bookings ORDER BY booking_id");

        List<RoomBookings> list = new ArrayList<>();

        while (resultSet.next()) {
            list.add(new RoomBookings(
                    resultSet.getString("booking_id"),
                    resultSet.getDate("booked_date").toLocalDate(),
                    resultSet.getDate("start_date").toLocalDate(),
                    resultSet.getDate("end_date").toLocalDate()
            ));
        }
        return list;
    }

    // Search booking by ID
    public static RoomBookings search(String bookingId) throws SQLException {
        ResultSet resultSet =
                CrudUtil.execute(
                        "SELECT * FROM room_bookings WHERE booking_id=?",
                        bookingId
                );

        if (resultSet.next()) {
            return new RoomBookings(
                    resultSet.getString("booking_id"),
                    resultSet.getDate("booked_date").toLocalDate(),
                    resultSet.getDate("start_date").toLocalDate(),
                    resultSet.getDate("end_date").toLocalDate()
            );
        }
        return null;
    }
}
