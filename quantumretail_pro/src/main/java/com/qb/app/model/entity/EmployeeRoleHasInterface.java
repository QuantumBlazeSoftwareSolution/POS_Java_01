/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.model.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 *
 * @author Vihanga
 */
@Entity
@Table(name = "employee_role_has_interface")
@NamedQueries({
    @NamedQuery(name = "EmployeeRoleHasInterface.findAll", query = "SELECT e FROM EmployeeRoleHasInterface e"),
    @NamedQuery(name = "EmployeeRoleHasInterface.findById", query = "SELECT e FROM EmployeeRoleHasInterface e WHERE e.id = :id")})
public class EmployeeRoleHasInterface implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "employee_role_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EmployeeRole employeeRoleId;
    @JoinColumn(name = "interface_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Interface interfaceId;

    public EmployeeRoleHasInterface() {
    }

    public EmployeeRoleHasInterface(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EmployeeRole getEmployeeRoleId() {
        return employeeRoleId;
    }

    public void setEmployeeRoleId(EmployeeRole employeeRoleId) {
        this.employeeRoleId = employeeRoleId;
    }

    public Interface getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(Interface interfaceId) {
        this.interfaceId = interfaceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmployeeRoleHasInterface)) {
            return false;
        }
        EmployeeRoleHasInterface other = (EmployeeRoleHasInterface) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qb.app.model.entity.EmployeeRoleHasInterface[ id=" + id + " ]";
    }
    
}
