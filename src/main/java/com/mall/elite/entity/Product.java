package com.mall.elite.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Data
public class Product {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String name;
    private UUID id_kit;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date create_at;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date update_at;
    private String brand;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Collection<Image> images;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Collection<Desciption> desciptions;
    @ManyToMany
    private Collection<Category> categories;

    public Product() {
    }
}
