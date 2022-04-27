package com.mall.elite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private int id;
    private int parent_id;
    private String name;

    @ManyToMany
    private Collection<Product> products;

    public Category() {
    }
}
