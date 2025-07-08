/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.model.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "location_supply_type")
@NamedQueries({
    @NamedQuery(name = "LocationSupplyType.findAll", query = "SELECT l FROM LocationSupplyType l"),
    @NamedQuery(name = "LocationSupplyType.findById", query = "SELECT l FROM LocationSupplyType l WHERE l.id = :id"),
    @NamedQuery(name = "LocationSupplyType.findByType", query = "SELECT l FROM LocationSupplyType l WHERE l.type = :type")})
public class LocationSupplyType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "type")
    private String type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationSupplyTypeId", fetch = FetchType.EAGER)
    private Collection<LocationSupply> locationSupplyCollection;

    public LocationSupplyType() {
    }

    public LocationSupplyType(Integer id) {
        this.id = id;
    }

    public LocationSupplyType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Collection<LocationSupply> getLocationSupplyCollection() {
        return locationSupplyCollection;
    }

    public void setLocationSupplyCollection(Collection<LocationSupply> locationSupplyCollection) {
        this.locationSupplyCollection = locationSupplyCollection;
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
        if (!(object instanceof LocationSupplyType)) {
            return false;
        }
        LocationSupplyType other = (LocationSupplyType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qb.app.model.entity.LocationSupplyType[ id=" + id + " ]";
    }
    
}
