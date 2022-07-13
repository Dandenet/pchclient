package com.example.pchecker.model;

public class Product {

    private Long    id;
    private String  name;
    private String  description;
    private String  price;
    private String  code;

    public Product() {
    }

    public Product(Long id, String name, String description, String price, String code) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
