/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.session;

import com.qb.app.model.entity.Employee;

/**
 *
 * @author Vihanga
 */
public class ApplicationSession {

    private static Employee employee;

    public static Employee getEmployee() {
        return employee;
    }

    public static void setEmployee(Employee emp) {
        employee = emp;
    }
}
