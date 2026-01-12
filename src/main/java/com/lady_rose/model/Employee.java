package com.lady_rose.model;

import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Employee extends Person{
    private double salary;
    private  String position;
    public static ArrayList<String> contact = new ArrayList<>();

    public Employee(){

    }

    public Employee(String pId, String name, String phoneNumber, String emailAddress, String address, String gender, String nic, String position, double salary){
        super(pId, name, phoneNumber, emailAddress, address, gender, nic);
        this.position=position;
        this.salary=salary;
    }
    public static boolean addEmployee(String ID, String name, String nic, String address, String email, String contacts,
                               LocalDate dob, String gender, String jobRole, String salary, LocalDate serviceStrtDate,
                               LocalDate serviceEndDate){
        try {
            boolean isAffected =CrudUtil.execute("INSERT INTO Employee VALUES(?,?,?,?,?,?,?,?,?,?,?,?);", ID, nic, name,
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
            boolean isAffected =CrudUtil.execute("UPDATE employer SET emp_name=?,emp_nic=?,emp_address=?,emp_email=?,emp_contact=?,emp_dob=?,gender=?,job_role=?,monthly_salary=?,entered_date=?,service_end_date=? WHERE emp_id=?;", name,
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
        if (!contact.contains(tele)){
            contact.add(tele);
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
            result= CrudUtil.execute("SELECT emp_id FROM employer ORDER BY emp_id DESC LIMIT 1");
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

    public String getPosition() {
        return position;
    }

    public double getSalary() {
        return salary;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
