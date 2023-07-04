package com.eshop.eshop.entity;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ORDERLINE")
public class OrderLine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDORDERLINE")
    private long idOrderLine;
    @Column(name = "IDORDER")
    private long idOrder;
    @Column(name = "IDPROD")
    private long idProd;
    @Column(name = "QUANTITY")
    private long quantity;
    @Column(name = "AMOUNT")
    private float amount;

    public OrderLine() {
    }

    public OrderLine(long idOrderLine, long idOrder, long idProd, long quantity, float amount) {
        this.idOrderLine = idOrderLine;
        this.idOrder = idOrder;
        this.idProd = idProd;
        this.quantity = quantity;
        this.amount = amount;
    }

    public long getIdOrderLine() {
        return idOrderLine;
    }

    public void setIdOrderLine(long idOrderLine) {
        this.idOrderLine = idOrderLine;
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public long getIdProd() {
        return idProd;
    }

    public void setIdProd(long idProd) {
        this.idProd = idProd;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    
}
