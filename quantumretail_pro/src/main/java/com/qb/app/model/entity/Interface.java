/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.model.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Vihanga
 */
@Entity
@Table(name = "interface")
@NamedQueries({
    @NamedQuery(name = "Interface.findAll", query = "SELECT i FROM Interface i"),
    @NamedQuery(name = "Interface.findById", query = "SELECT i FROM Interface i WHERE i.id = :id"),
    @NamedQuery(name = "Interface.findByInterface1", query = "SELECT i FROM Interface i WHERE i.interface1 = :interface1")})
public class Interface implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "interface")
    private String interface1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "interfaceId")
    private Collection<EmployeeRoleHasInterface> employeeRoleHasInterfaceCollection;

    public Interface() {
    }

    public Interface(Integer id) {
        this.id = id;
    }

    public Interface(Integer id, String interface1) {
        this.id = id;
        this.interface1 = interface1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInterface1() {
        return interface1;
    }

    public void setInterface1(String interface1) {
        this.interface1 = interface1;
    }

    public Collection<EmployeeRoleHasInterface> getEmployeeRoleHasInterfaceCollection() {
        return employeeRoleHasInterfaceCollection;
    }

    public void setEmployeeRoleHasInterfaceCollection(Collection<EmployeeRoleHasInterface> employeeRoleHasInterfaceCollection) {
        this.employeeRoleHasInterfaceCollection = employeeRoleHasInterfaceCollection;
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
        if (!(object instanceof Interface)) {
            return false;
        }
        Interface other = (Interface) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qb.app.model.entity.Interface[ id=" + id + " ]";
    }
    
}
