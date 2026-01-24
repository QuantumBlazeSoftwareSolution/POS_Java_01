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
@Table(name = "product_has_barcode")
@NamedQueries({
    @NamedQuery(name = "ProductHasBarcode.findAll", query = "SELECT p FROM ProductHasBarcode p"),
    @NamedQuery(name = "ProductHasBarcode.findById", query = "SELECT p FROM ProductHasBarcode p WHERE p.id = :id"),
    @NamedQuery(name = "ProductHasBarcode.findByBarcode", query = "SELECT p FROM ProductHasBarcode p WHERE p.barcode = :barcode")})
public class ProductHasBarcode implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "barcode")
    private String barcode;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product productId;

    public ProductHasBarcode() {
    }

    public ProductHasBarcode(Integer id) {
        this.id = id;
    }

    public ProductHasBarcode(Integer id, String barcode) {
        this.id = id;
        this.barcode = barcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
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
        if (!(object instanceof ProductHasBarcode)) {
            return false;
        }
        ProductHasBarcode other = (ProductHasBarcode) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qb.app.model.entity.ProductHasBarcode[ id=" + id + " ]";
    }
    
}
