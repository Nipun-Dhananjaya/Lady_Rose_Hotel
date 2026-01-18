package com.lady_rose.model;

import com.lady_rose.dto.Payment;
import com.lady_rose.dto.RoomBookings;
import com.lady_rose.dto.TM.PaymentTM;
import com.lady_rose.dto.TM.RoomBookingTM;
import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.lady_rose.model.EmployeeModel.stringLength;

public class PaymentModel {
    public static String generateID() {
        ResultSet result=null;
        String[] idParts;
        String id="P-00000";
        try {
            result= CrudUtil.execute("SELECT pay_id FROM payment ORDER BY pay_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "P-"+num;
        } catch (SQLException | ClassCastException e) {
            e.printStackTrace();
            return "P-00000";
        }
    }

    private static String setNextIdValue(int number) {
        String returnVal="";
        int length=String.valueOf(number).length();
        if(length<stringLength){
            int difference=stringLength-length;
            for (int i = 0; i < difference; i++) {
                returnVal+="0";
            }
            returnVal+=String.valueOf(number);
            return returnVal;
        }
        return String.valueOf(number);
    }
    public static boolean updateRoomBookingPayment(String bookingId,String payId, String amount,
                                                   String paymentDate, String method, String status) {
        try {
            boolean isAffectedPayment=CrudUtil.execute(
                    "INSERT INTO payment VALUES(?,?,?,?,?);",payId,amount, paymentDate, status, method
            );
            boolean isAffectedRoom=CrudUtil.execute(
                    "UPDATE room_booking SET pay_Id=? WHERE Booking_ID=?",
                    payId, bookingId
            );
            return (isAffectedPayment & isAffectedRoom);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /*public static boolean updateSpaBookingPayment(String bookingId, String amount,
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
    }*/

    public static PaymentTM getRoomBookingById(String bookingId) throws SQLException {
        ResultSet rs = CrudUtil.execute("SELECT payment.pay_id,payment.paid_amount,payment.paid_time,payment.status,payment.method,customer.ID,customer.Name FROM payment INNER JOIN room_booking ON room_booking.pay_Id=payment.pay_id INNER JOIN customer ON room_booking.cust_id=customer.ID WHERE room_booking.Booking_ID=?", bookingId);
        if (rs.next()) {
            return new PaymentTM(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
            );
        }
        return null;
    }

    /*public static SpaBookings getSpaBookingById(String bookingId) throws SQLException {
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
    }*/

    // === NEW: GET ALL FOR TABLE VIEW ===
    public static List<PaymentTM> getAllRoomBookings() throws SQLException {
        List<PaymentTM> list = new ArrayList<>();
        ResultSet rs = CrudUtil.execute("SELECT payment.pay_id,payment.paid_amount,payment.paid_time,payment.status,payment.method,customer.ID,customer.Name FROM payment INNER JOIN room_booking ON room_booking.pay_Id=payment.pay_id INNER JOIN customer ON room_booking.cust_id=customer.ID;");
        while (rs.next()) {
            list.add(new PaymentTM(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7)
            ));
        }
        return list;
    }

    /*public static List<SpaBookings> getAllSpaBookings() throws SQLException {
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
    }*/
}