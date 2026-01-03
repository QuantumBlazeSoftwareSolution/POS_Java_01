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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author Vihanga
 */
@Entity
@Table(name = "stock")
@NamedQueries({
    @NamedQuery(name = "Stock.findAll", query = "SELECT s FROM Stock s"),
    @NamedQuery(name = "Stock.findByBatchId", query = "SELECT s FROM Stock s WHERE s.batchId = :batchId"),
    @NamedQuery(name = "Stock.findByQty", query = "SELECT s FROM Stock s WHERE s.qty = :qty"),
    @NamedQuery(name = "Stock.findByCostPrice", query = "SELECT s FROM Stock s WHERE s.costPrice = :costPrice"),
    @NamedQuery(name = "Stock.findBySalePrice", query = "SELECT s FROM Stock s WHERE s.salePrice = :salePrice"),
    @NamedQuery(name = "Stock.findByDiscount", query = "SELECT s FROM Stock s WHERE s.discount = :discount"),
    @NamedQuery(name = "Stock.findByReceivedDate", query = "SELECT s FROM Stock s WHERE s.receivedDate = :receivedDate"),
    @NamedQuery(name = "Stock.findByExpireDate", query = "SELECT s FROM Stock s WHERE s.expireDate = :expireDate")})
public class Stock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "batch_id")
    private Integer batchId;
    @Basic(optional = false)
    @Column(name = "qty")
    private double qty;
    @Basic(optional = false)
    @Column(name = "cost_price")
    private double costPrice;
    @Basic(optional = false)
    @Column(name = "sale_price")
    private double salePrice;
    @Basic(optional = false)
    @Column(name = "discount")
    private double discount;
    @Basic(optional = false)
    @Column(name = "received_date")
    @Temporal(TemporalType.DATE)
    private Date receivedDate;
    @Basic(optional = false)
    @Column(name = "expire_date")
    @Temporal(TemporalType.DATE)
    private Date expireDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockBatchId")
    private Collection<StockAdjustment> stockAdjustmentCollection;
    @JoinColumn(name = "grn_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Grn grnId;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product productId;
    @JoinColumn(name = "stock_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private StockStatus stockStatusId;
    @JoinColumn(name = "supplier_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Supplier supplierId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockBatchId")
    private Collection<DamageItem> damageItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockBatchId")
    private Collection<LocationSupplyItem> locationSupplyItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stockBatchId")
    private Collection<InvoiceItem> invoiceItemCollection;

    public Stock() {
    }

    public Stock(Integer batchId) {
        this.batchId = batchId;
    }

    public Stock(Integer batchId, double qty, double costPrice, double salePrice, double discount, Date receivedDate, Date expireDate) {
        this.batchId = batchId;
        this.qty = qty;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.discount = discount;
        this.receivedDate = receivedDate;
        this.expireDate = expireDate;
    }

    public Integer getBatchId() {
        return batchId;
    }

    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

    public double getQty() {
        return qty;
    }

    public void setQty(double qty) {
        this.qty = qty;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public Collection<StockAdjustment> getStockAdjustmentCollection() {
        return stockAdjustmentCollection;
    }

    public void setStockAdjustmentCollection(Collection<StockAdjustment> stockAdjustmentCollection) {
        this.stockAdjustmentCollection = stockAdjustmentCollection;
    }

    public Grn getGrnId() {
        return grnId;
    }

    public void setGrnId(Grn grnId) {
        this.grnId = grnId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    public StockStatus getStockStatusId() {
        return stockStatusId;
    }

    public void setStockStatusId(StockStatus stockStatusId) {
        this.stockStatusId = stockStatusId;
    }

    public Supplier getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Supplier supplierId) {
        this.supplierId = supplierId;
    }

    public Collection<DamageItem> getDamageItemCollection() {
        return damageItemCollection;
    }

    public void setDamageItemCollection(Collection<DamageItem> damageItemCollection) {
        this.damageItemCollection = damageItemCollection;
    }

    public Collection<LocationSupplyItem> getLocationSupplyItemCollection() {
        return locationSupplyItemCollection;
    }

    public void setLocationSupplyItemCollection(Collection<LocationSupplyItem> locationSupplyItemCollection) {
        this.locationSupplyItemCollection = locationSupplyItemCollection;
    }

    public Collection<InvoiceItem> getInvoiceItemCollection() {
        return invoiceItemCollection;
    }

    public void setInvoiceItemCollection(Collection<InvoiceItem> invoiceItemCollection) {
        this.invoiceItemCollection = invoiceItemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (batchId != null ? batchId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stock)) {
            return false;
        }
        Stock other = (Stock) object;
        if ((this.batchId == null && other.batchId != null) || (this.batchId != null && !this.batchId.equals(other.batchId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qb.app.model.entity.Stock[ batchId=" + batchId + " ]";
    }
    
}
