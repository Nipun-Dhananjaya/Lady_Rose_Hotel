package com.lady_rose.model;

public class Employee extends Person{
    private double salary;
    private  String position;

    public Employee(){

    }

    public Employee(String pId, String name, String phoneNumber, String emailAddress, String address, String gender, String nic, String position, double salary){
        super(pId, name, phoneNumber, emailAddress, address, gender, nic);
        this.position=position;
        this.salary=salary;
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
