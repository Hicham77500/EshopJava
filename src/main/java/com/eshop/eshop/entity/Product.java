package com.eshop.eshop.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PRODUCT")
public class Product implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "IDPROD")
    private long idProd;
    @Column (name = "IDCAT")
    private long idCat;
    @Column (name = "TITLE")
    private String title; 
    @Column (name = "DESCRIPTION")
    private String description;
    @Column (name = "STOCK")
    private long stock;
    @Column (name = "URLIMAGE")
    private String urlImage;
    @Column (name = "PRICE")
    private double price;
    
    public Product() {
    }

    public Product(long idProd, long idCat, String title, String description, long stock, String urlImage,
            double price) {
        this.idProd = idProd;
        this.idCat = idCat;
        this.title = title;
        this.description = description;
        this.stock = stock;
        this.urlImage = urlImage;
        this.price = price;
    }

    public long getIdProd() {
        return idProd;
    }

    public void setIdProd(long idProd) {
        this.idProd = idProd;
    }

    public long getIdCat() {
        return idCat;
    }

    public void setIdCat(long idCat) {
        this.idCat = idCat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

   

    
}
