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
@Table(name = "CATEGORY")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column (name = "IDCAT")
    private long idCat ;
    @Column (name = "NAME")
    private String name ; 
    @Column (name = "DESCRIPTION")
    private String description;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "idCat")
    private List<Product> listProducts = new ArrayList<>();
    
    public Category() {
    }

    public Category(long idCat, String name, String description, List<Product> listProducts) {
        this.idCat = idCat;
        this.name = name;
        this.description = description;
        this.listProducts = listProducts;
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

    public List<Product> getListProducts() {
        return listProducts;
    }

    public void setListProducts(List<Product> listProducts) {
        this.listProducts = listProducts;
    }

   
  
}
