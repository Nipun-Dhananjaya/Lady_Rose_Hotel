package com.lady_rose.model;

import com.lady_rose.dto.Employee;
import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static int stringLength=5;
    public static boolean addEmployee(String ID, String name, String nic, String address, String email, String contacts,
                                      LocalDate dob, String gender, String jobRole, String salary, LocalDate serviceStrtDate,
                                      LocalDate serviceEndDate){
        try {
            boolean isAffected = CrudUtil.execute("INSERT INTO employee VALUES(?,?,?,?,?,?,?,?,?,?,?,?);", ID, nic, name,
                    gender,address, email, contacts, dob, jobRole, salary, serviceStrtDate, serviceEndDate);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    public static boolean updateEmployer(String ID, String name, String nic, String address, String email, String contacts,
                                         LocalDate dob, String gender, String jobRole, String salary, LocalDate serviceStrtDate,
                                         LocalDate serviceEndDate) {
        try {
            boolean isAffected =CrudUtil.execute("UPDATE employee SET Name=?,NIC=?,Address=?,Email=?,Contact=?,DateOfBirth=?,Gender=?,Job_role=?,Salary=?,Start_Date=?,End_Date=? WHERE ID=?;", name,
                    nic, address, email, contacts, dob, gender, jobRole, salary, serviceStrtDate, serviceEndDate,ID);
            if (isAffected){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
    public static boolean addContact(String tele) {
        if (!Employee.contact.contains(tele)){
            Employee.contact.add(tele);
            return true;
        }
        else{
            return false;
        }
    }
    public static String generateID() {
        ResultSet result=null;
        String[] idParts;
        String id="E-00000";
        try {
            result= CrudUtil.execute("SELECT ID FROM employee ORDER BY ID DESC LIMIT 1");
            if(result.next()) {
                id=result.getString(1);
            }
            idParts=id.split("-");
            System.out.println(idParts.length);
            int number=Integer.parseInt(idParts[1]);
            String num=setNextIdValue(++number);
            return "E-"+num;
        } catch (SQLException e) {
            return "E-00000";
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

    public static List<Employee> searchEmployee(String empId) throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM employee WHERE ID=?;",empId);
        List<Employee> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12)
            ));
        }
        return data;
    }
    public static List<Employee> getAll() throws SQLException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM employee ORDER BY ID;");
        List<Employee> data = new ArrayList<>();

        while (resultSet.next()) {
            data.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    resultSet.getString(12)
            ));
        }
        return data;
    }
}
