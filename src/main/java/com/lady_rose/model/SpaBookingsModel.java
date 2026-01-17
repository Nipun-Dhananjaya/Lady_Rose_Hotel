package com.lady_rose.model;

import com.lady_rose.dto.SpaBooking;
import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SpaDetailsModel {

    public static int stringLength = 5;

    public static boolean addBooking(String bookingId,
                                     LocalDate bookingDate,
                                     LocalDate bookedDate,
                                     String spaPackage) {
        try {
            boolean isAffected = CrudUtil.execute(
                    "INSERT INTO spa_booking VALUES (?,?,?,?)",
                    bookingId, bookingDate, bookedDate, spaPackage
            );
            return isAffected;
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean updateBooking(String bookingId,
                                        LocalDate bookingDate,
                                        LocalDate bookedDate,
                                        String spaPackage) {
        try {
            boolean isAffected = CrudUtil.execute(
                    "UPDATE spa_booking SET booking_date=?, booked_date=?, package=? WHERE booking_id=?",
                    bookingDate, bookedDate, spaPackage, bookingId
            );
            return isAffected;
        } catch (SQLException e) {
            return false;
        }
    }

    public static SpaBooking searchBooking(String bookingId) throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM spa_booking WHERE booking_id=?",
                bookingId
        );

        if (resultSet.next()) {
            return new SpaBooking(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );
        }
        return null;
    }

    public static List<SpaBooking> getAll() throws SQLException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT * FROM spa_booking ORDER BY booking_id"
        );

        List<SpaBooking> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new SpaBooking(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            ));
        }
        return data;
    }

    public static String generateID() {
        ResultSet result = null;
        String id = "B-00000";

        try {
            result = CrudUtil.execute(
                    "SELECT booking_id FROM spa_booking ORDER BY booking_id DESC LIMIT 1"
            );

            if (result.next()) {
                id = result.getString(1);
            }

            String[] idParts = id.split("-");
            int number = Integer.parseInt(idParts[1]);
            String nextId = setNextIdValue(++number);
            return "B-" + nextId;

        } catch (SQLException e) {
            return "B-00000";
        }
    }

    private static String setNextIdValue(int number) {
        String returnVal = "";
        int length = String.valueOf(number).length();

        if (length < stringLength) {
            int difference = stringLength - length;
            for (int i = 0; i < difference; i++) {
                returnVal += "0";
            }
            returnVal += number;
            return returnVal;
        }
        return String.valueOf(number);
    }
}