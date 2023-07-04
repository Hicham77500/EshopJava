package com.eshop.eshop.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name = "ORDERS")
public class Orders implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDORDER")
    private long idOrder;
    @Column(name = "IDUSER")
    private long idUser;
    @Column(name = "REFORDER")
    private String refOrder;
    @Column(name = "DATEORDER")
    private Date dateOrder;
    @Column(name = "TOTAL")
    private float total;

    public Orders() {
    }

    public Orders(long idOrder, long idUser, String refOrder, Date dateOrder, float total) {
        this.idOrder = idOrder;
        this.idUser = idUser;
        this.refOrder = refOrder;
        this.dateOrder = dateOrder;
        this.total = total;
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getRefOrder() {
        return refOrder;
    }

    public void setRefOrder(String refOrder) {
        this.refOrder = refOrder;
    }

    public Date getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(Date dateOrder) {
        this.dateOrder = dateOrder;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

   

}
