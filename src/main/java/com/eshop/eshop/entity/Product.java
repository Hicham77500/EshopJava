package com.eshop.eshop.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    @Column (name = "NAME")
    private String name; 
    @Column (name = "DESCRIPTION")
    private String description;
    @Column (name = "STOCK")
    private long stock;
    @Column (name = "URLIMAGE")
    private String urlImage;
    @Column (name = "PRICE")
    private double price;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idProd")
    private List<OrderLine> listOrderLines = new ArrayList<>();

    public Product() {
    }

    public Product(long idProd, long idCat, String name, String description, long stock, String urlImage, double price,
            List<OrderLine> listOrderLines) {
        this.idProd = idProd;
        this.idCat = idCat;
        this.name = name;
        this.description = description;
        this.stock = stock;
        this.urlImage = urlImage;
        this.price = price;
        this.listOrderLines = listOrderLines;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<OrderLine> getListOrderLines() {
        return listOrderLines;
    }

    public void setListOrderLines(List<OrderLine> listOrderLines) {
        this.listOrderLines = listOrderLines;
    }

   

    
}
