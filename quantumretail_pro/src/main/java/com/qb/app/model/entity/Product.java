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
import java.io.Serializable;
import java.util.Collection;

/**
 *
 * @author Vihanga
 */
@Entity
@Table(name = "product")
@NamedQueries({
    @NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p"),
    @NamedQuery(name = "Product.findById", query = "SELECT p FROM Product p WHERE p.id = :id"),
    @NamedQuery(name = "Product.findByProduct", query = "SELECT p FROM Product p WHERE p.product = :product"),
    @NamedQuery(name = "Product.findByMeasure", query = "SELECT p FROM Product p WHERE p.measure = :measure"),
    @NamedQuery(name = "Product.findByBarCode", query = "SELECT p FROM Product p WHERE p.barCode = :barCode"),
    @NamedQuery(name = "Product.findByCostPrice", query = "SELECT p FROM Product p WHERE p.costPrice = :costPrice"),
    @NamedQuery(name = "Product.findBySalePrice", query = "SELECT p FROM Product p WHERE p.salePrice = :salePrice"),
    @NamedQuery(name = "Product.findByDiscount", query = "SELECT p FROM Product p WHERE p.discount = :discount")})
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "product")
    private String product;
    @Basic(optional = false)
    @Column(name = "measure")
    private float measure;
    @Column(name = "bar_code")
    private String barCode;
    @Basic(optional = false)
    @Column(name = "cost_price")
    private double costPrice;
    @Basic(optional = false)
    @Column(name = "sale_price")
    private double salePrice;
    @Basic(optional = false)
    @Column(name = "discount")
    private double discount;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<ProductDistributeItem> productDistributeItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<ProductHasProductType> productHasProductTypeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "referenceId")
    private Collection<ProductHasProductType> productHasProductTypeCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<StockAdjustmentItem> stockAdjustmentItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<RefundItem> refundItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<SupplierDamageReturnItem> supplierDamageReturnItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<Stock> stockCollection;
    @JoinColumn(name = "category_has_brand_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CategoryHasBrand categoryHasBrandId;
    @JoinColumn(name = "product_status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ProductStatus productStatusId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<LocationSupplyItem> locationSupplyItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<SupplierOrderItem> supplierOrderItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<GrnItem> grnItemCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<InvoiceItem> invoiceItemCollection;

    public Product() {
    }

    public Product(Integer id) {
        this.id = id;
    }

    public Product(Integer id, String product, float measure, double costPrice, double salePrice, double discount) {
        this.id = id;
        this.product = product;
        this.measure = measure;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
        this.discount = discount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public float getMeasure() {
        return measure;
    }

    public void setMeasure(float measure) {
        this.measure = measure;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
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

    public Collection<ProductDistributeItem> getProductDistributeItemCollection() {
        return productDistributeItemCollection;
    }

    public void setProductDistributeItemCollection(Collection<ProductDistributeItem> productDistributeItemCollection) {
        this.productDistributeItemCollection = productDistributeItemCollection;
    }

    public Collection<ProductHasProductType> getProductHasProductTypeCollection() {
        return productHasProductTypeCollection;
    }

    public void setProductHasProductTypeCollection(Collection<ProductHasProductType> productHasProductTypeCollection) {
        this.productHasProductTypeCollection = productHasProductTypeCollection;
    }

    public Collection<ProductHasProductType> getProductHasProductTypeCollection1() {
        return productHasProductTypeCollection1;
    }

    public void setProductHasProductTypeCollection1(Collection<ProductHasProductType> productHasProductTypeCollection1) {
        this.productHasProductTypeCollection1 = productHasProductTypeCollection1;
    }

    public Collection<StockAdjustmentItem> getStockAdjustmentItemCollection() {
        return stockAdjustmentItemCollection;
    }

    public void setStockAdjustmentItemCollection(Collection<StockAdjustmentItem> stockAdjustmentItemCollection) {
        this.stockAdjustmentItemCollection = stockAdjustmentItemCollection;
    }

    public Collection<RefundItem> getRefundItemCollection() {
        return refundItemCollection;
    }

    public void setRefundItemCollection(Collection<RefundItem> refundItemCollection) {
        this.refundItemCollection = refundItemCollection;
    }

    public Collection<SupplierDamageReturnItem> getSupplierDamageReturnItemCollection() {
        return supplierDamageReturnItemCollection;
    }

    public void setSupplierDamageReturnItemCollection(Collection<SupplierDamageReturnItem> supplierDamageReturnItemCollection) {
        this.supplierDamageReturnItemCollection = supplierDamageReturnItemCollection;
    }

    public Collection<Stock> getStockCollection() {
        return stockCollection;
    }

    public void setStockCollection(Collection<Stock> stockCollection) {
        this.stockCollection = stockCollection;
    }

    public CategoryHasBrand getCategoryHasBrandId() {
        return categoryHasBrandId;
    }

    public void setCategoryHasBrandId(CategoryHasBrand categoryHasBrandId) {
        this.categoryHasBrandId = categoryHasBrandId;
    }

    public ProductStatus getProductStatusId() {
        return productStatusId;
    }

    public void setProductStatusId(ProductStatus productStatusId) {
        this.productStatusId = productStatusId;
    }

    public Collection<LocationSupplyItem> getLocationSupplyItemCollection() {
        return locationSupplyItemCollection;
    }

    public void setLocationSupplyItemCollection(Collection<LocationSupplyItem> locationSupplyItemCollection) {
        this.locationSupplyItemCollection = locationSupplyItemCollection;
    }

    public Collection<SupplierOrderItem> getSupplierOrderItemCollection() {
        return supplierOrderItemCollection;
    }

    public void setSupplierOrderItemCollection(Collection<SupplierOrderItem> supplierOrderItemCollection) {
        this.supplierOrderItemCollection = supplierOrderItemCollection;
    }

    public Collection<GrnItem> getGrnItemCollection() {
        return grnItemCollection;
    }

    public void setGrnItemCollection(Collection<GrnItem> grnItemCollection) {
        this.grnItemCollection = grnItemCollection;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Product)) {
            return false;
        }
        Product other = (Product) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.qb.app.model.entity.Product[ id=" + id + " ]";
    }
    
}
