package com.example.demoEmail.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private long id;
    private String name;
    private String description;
    private double price;
    private int quantity;
    private String[] image;
}
