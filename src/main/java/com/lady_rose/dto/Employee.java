package com.lady_rose.dto;

import com.lady_rose.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Employee extends Person{
    private double salary;
    private  String position;
    private String enteredDate;
    private String resignedDate;
    private String dob;
    public static ArrayList<String> contact = new ArrayList<>();

    public Employee(){

    }

    public Employee(String pId, String name, String nic,String emailAddress, String address,  String phoneNumber,
                    String position,String dob,String enteredDate, String resignedDate,  String salary, String gender) {
        super(pId, name, phoneNumber, emailAddress, address, gender, nic);
        this.position=position;
        this.salary=Double.parseDouble(salary);
        this.enteredDate=enteredDate;
        this.resignedDate=resignedDate;
        this.dob=dob;
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

    public String getResignedDate() {
        return resignedDate;
    }

    public void setResignedDate(String resignedDate) {
        this.resignedDate = resignedDate;
    }

    public String getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(String enteredDate) {
        this.enteredDate = enteredDate;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
