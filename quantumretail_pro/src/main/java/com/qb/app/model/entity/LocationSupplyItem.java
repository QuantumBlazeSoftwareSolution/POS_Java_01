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
@Table(name = "location_supply_item")
@NamedQueries({
    @NamedQuery(name = "LocationSupplyItem.findAll", query = "SELECT l FROM LocationSupplyItem l"),
    @NamedQuery(name = "LocationSupplyItem.findById", query = "SELECT l FROM LocationSupplyItem l WHERE l.id = :id"),
    @NamedQuery(name = "LocationSupplyItem.findByQty", query = "SELECT l FROM LocationSupplyItem l WHERE l.qty = :qty")})
public class LocationSupplyItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "qty")
    private double qty;
    @JoinColumn(name = "location_supply_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LocationSupply locationSupplyId;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product productId;
    @JoinColumn(name = "stock_batch_id", referencedColumnName = "batch_id")
    @ManyToOne(optional = false)
    private Stock stockBatchId;

    public LocationSupplyItem() {
    }

    public LocationSupplyItem(Integer id) {
        this.id = id;
    }

    public LocationSupplyItem(Integer id, double qty) {
        this.id = id;
        this.qty = qty;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public LocationSupply getLocationSupplyId() {
        return locationSupplyId;
    }

    public void setLocationSupplyId(LocationSupply locationSupplyId) {
        this.locationSupplyId = locationSupplyId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public Stock getStockBatchId() {
        return stockBatchId;
    }

    public void setStockBatchId(Stock stockBatchId) {
        this.stockBatchId = stockBatchId;
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
        if (!(object instanceof LocationSupplyItem)) {
            return false;
        }
        LocationSupplyItem other = (LocationSupplyItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qb.app.model.entity.LocationSupplyItem[ id=" + id + " ]";
    }
    
}
