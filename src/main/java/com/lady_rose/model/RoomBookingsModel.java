package com.lady_rose.model;

import com.lady_rose.db.DBConnection;
import com.lady_rose.dto.RoomBookings;
import com.lady_rose.dto.TM.RoomBookingTM;
import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.lady_rose.model.EmployeeModel.stringLength;

public class RoomBookingsModel {
    public static ArrayList<String> deluxeRoomNo = new ArrayList<>();
    public static ArrayList<String> standardRoomNo = new ArrayList<>();

    public static String generateID() {
        ResultSet result=null;
        String[] idParts;
        String id="B-00000";
        try {
            result= CrudUtil.execute("SELECT Booking_ID FROM room_booking ORDER BY Booking_ID DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "B-"+num;
        } catch (SQLException | ClassCastException e) {
            e.printStackTrace();
            return "B-00000";
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

    public static boolean isAdded(String bookingId, String custId, LocalDateTime startDate,LocalDateTime endDate, long duration, String roomNum, LocalDateTime nowDateNTime, String availability) throws SQLException {
        boolean isAffectedToRoomBooking = CrudUtil.execute("INSERT INTO room_booking VALUES(?,?,?,?,?,?,?,?,?);", roomNum, bookingId, nowDateNTime, startDate,endDate,duration,availability,custId,roomNum);
        boolean isAffectedToRoom = CrudUtil.execute("UPDATE room SET status=? WHERE room_No=?;","Booked" ,roomNum);

        return (isAffectedToRoomBooking & isAffectedToRoom) ? true:false;
    }

    public static String searchCustomer(String custId) {
        String name;
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT Name FROM customer WHERE ID=?",custId);
            if (resultSet.next()){
                name= resultSet.getString(1);
                return name;
            }
            else{
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public static void setRoomNumbers(String room_Type) {
        ResultSet roomNoresultSet= null;
        try {
            roomNoresultSet = CrudUtil.execute("SELECT Room_no FROM room WHERE Room_Category=? AND status!=?;",room_Type,"Booked");
            if (room_Type.equals("Deluxe Room")){
                while(roomNoresultSet.next()){
                    if (!deluxeRoomNo.contains(roomNoresultSet.getString(1))){
                        deluxeRoomNo.add(roomNoresultSet.getString(1));
                    }
                }
            }
            else{
                while(roomNoresultSet.next()){
                    if (!standardRoomNo.contains(roomNoresultSet.getString(1))){
                        standardRoomNo.add(roomNoresultSet.getString(1));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String setSleepCount(String roomNo) {
        ResultSet sleepResultSet= null;
        String sleepCount=null;
        try {
            sleepResultSet= CrudUtil.execute("SELECT Bed_Count FROM room WHERE Room_No=?",roomNo);
            if (sleepResultSet.next()){
                sleepCount=sleepResultSet.getString(1);
                return sleepCount;
            }
            else{
                return sleepCount;
            }
        } catch (SQLException e) {
            return sleepCount;
        }
    }

    public static List<RoomBookingTM> getAll() throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT room_booking.Booking_ID,room_booking.cust_id,customer.Name,room_booking.room_no,room.Room_Category, " +
                "room_booking.B_Strat_Date,room_booking.duration,payment.paid_amount,room_booking.Booked_Date," +
                "room_booking.availability FROM room_booking INNER JOIN room ON room_booking.room_no=room.Room_no " +
                "INNER JOIN customer ON customer.ID=room_booking.cust_id INNER JOIN payment ON payment.pay_id=room_booking.pay_Id ORDER BY room_booking.booking_id;");
        List<RoomBookingTM> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new RoomBookingTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10)
            ));
        }
        return data;
    }

    public static List<RoomBookingTM> searchBooking(String bookingId) throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT room_booking.Booking_ID,room_booking.cust_id,customer.Name," +
                "room_booking.room_no,room.Room_Category room_booking.B_Start_Date,room_booking.duration,payment.paid_amount," +
                "room_booking.Booked_Date,room_booking.availability FROM room_booking INNER JOIN room ON " +
                "room_booking.room_no=room.Room_no INNER JOIN customer ON customer.ID=room_booking.cust_id INNER JOIN payment ON " +
                "payment.pay_id=room_booking.pay_Id WHERE booking.booking_id=? AND room_booking.availability!=?;",bookingId,"Cancelled");
        List<RoomBookingTM> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new RoomBookingTM(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10)
            ));
        }
        return data;
    }

    public static String getPaymentId(String bookingId) {
        try {
            ResultSet resultSet=CrudUtil.execute("SELECT payment_id FROM booking WHERE booking_id=?",bookingId);
            String paymentId=null;
            if(resultSet.next()){
                paymentId=resultSet.getString(1);
            }
            return paymentId;
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean updateBooking(String bookingId, LocalDateTime startDate,LocalDateTime endDate, long duration,String availability) throws SQLException {
        boolean isAffectedToBooking = CrudUtil.execute("UPDATE room_booking SET B_Start_Date=?," +
                "B_End_Date=?,duration=?,availability=? WHERE booking_id=?;", startDate, endDate, String.valueOf(duration),availability,bookingId);

        return (isAffectedToBooking) ? true:false;
    }

    public static LocalDateTime getPaidDateTime(String bookingId) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ResultSet resultSet=CrudUtil.execute("SELECT booking_made_date_time FROM room_booking WHERE booking_id=?",bookingId);
            LocalDateTime paidDateTime=null;
            if(resultSet.next()){
                paidDateTime= LocalDateTime.parse(resultSet.getString(1),formatter);
            }
            return paidDateTime;
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean cancelBooking(String bookingId,String status,String roomNum) throws SQLException {
        boolean isAffectedToRoomBooking = CrudUtil.execute("UPDATE room_booking SET availability=? WHERE room_no=? AND booking_id=?;",status, roomNum, bookingId);
        boolean isAffectedToRoom = CrudUtil.execute("UPDATE room SET status=? WHERE Room_No=?;","Available" ,roomNum);

        return (isAffectedToRoomBooking & isAffectedToRoom) ? true:false;
    }

    public static void  updateStatus() throws SQLException {
        try {
            DBConnection.getInstance().getConnection().setAutoCommit(false);
            /*ResultSet result=CrudUtil.execute("SELECT booking.booking_id,booking.booked_date_time,booking.booking_duration FROM booking INNER JOIN room_booking ON room_booking.booking_id=booking.booking_id;");
            ArrayList<UpdateStatus> temp=new ArrayList<>();
            while (result.next()){
                temp.add(new UpdateStatus(
                        result.getString(1),
                        result.getString(2),
                        result.getString(3)
                        ));
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            ArrayList<String> tempIds=new ArrayList<>();
            for (UpdateStatus update:temp) {
                LocalDateTime endDate = LocalDateTime.parse(update.getBookedDate()).plusHours(Long.parseLong(update.getDuration()));
                System.out.println(endDate);
                if (endDate.isAfter(LocalDateTime.now())){
                    tempIds.add(update.getId());
                }
            }*/
            ResultSet resultSet=CrudUtil.execute("SELECT booking.booking_id FROM booking INNER JOIN room_booking ON room_booking.booking_id=booking.booking_id WHERE booking.booked_date_time<? AND room_booking.availability!=?;",LocalDateTime.now(),"Cancelled");
            ArrayList<String> tempIds=new ArrayList<>();
            while (resultSet.next()){
                tempIds.add(resultSet.getString(1));
            }
            boolean isAffected=false;
            for (int i = 0; i < tempIds.size(); i++) {
                isAffected=CrudUtil.execute("UPDATE room_booking SET availability=? WHERE booking_id=?;","End",tempIds.get(i));
            }
            DBConnection.getInstance().getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            DBConnection.getInstance().getConnection().rollback();
            DBConnection.getInstance().getConnection().setAutoCommit(true);
        }
    }

    public static String getBookingId() {
        ResultSet result=null;
        String id="";
        try {
            result= CrudUtil.execute("SELECT booking_id FROM booking ORDER BY booking_id DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            return id;
        } catch (SQLException e) {
            return id;
        }
    }
}
