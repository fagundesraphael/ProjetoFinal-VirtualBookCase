package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "books")
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(readOnly = true)
    private Long id;

    @ApiModelProperty(readOnly = true)
    private String username;

    @NotBlank
    private String title;

    @NotBlank
    private String author;


    private String publishedAt;

    @Min(0)
    private Double price;

    public Book() {}


    public Book(Long id, String username, String title,  String author,  String  publishedAt,  Double price) {
        this.id = id;
        this.username = username;
        this.title = title;
        this.author = author;
        this.publishedAt = publishedAt;
        this.price = price;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String  getPublishedAt() {
        return this.publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }


    public Double getPrice() {
        return this.price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    
}
