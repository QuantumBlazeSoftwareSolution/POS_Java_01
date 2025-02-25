package com.qb.app.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "employee")
public class Employee implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", length = 45, nullable = false)
    private String name;

    @Column(name = "username", length = 45, nullable = false)
    private String username;

    @Column(name = "password", length = 20, nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "employee_role_id")
    private EmployeeRole employeeRole;

    @ManyToOne
    @JoinColumn(name = "employee_status_id")
    private EmployeeStatus employeeStatus;

    @ManyToOne
    @JoinColumn(name = "employee_panel_id")
    private EmployeePanel employeePanel;

    public Employee() {
    }

    public Employee(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public EmployeeRole getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(EmployeeRole employeeRole) {
        this.employeeRole = employeeRole;
    }

    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public EmployeePanel getEmployeePanel() {
        return employeePanel;
    }

    public void setEmployeePanel(EmployeePanel employeePanel) {
        this.employeePanel = employeePanel;
    }
}
