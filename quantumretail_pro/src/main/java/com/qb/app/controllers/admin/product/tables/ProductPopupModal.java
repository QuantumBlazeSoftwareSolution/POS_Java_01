/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.qb.app.controllers.admin.product.tables;

import com.qb.app.model.entity.Product;

/**
 *
 * @author Vihanga
 */
public class ProductPopupModal {

    public Product product;

    public ProductPopupModal() {
    }

    public ProductPopupModal(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

}
