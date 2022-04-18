package com.mall.elite.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor
public class Kit {
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private int UUID;
    private String name;
    private double price;
    private int total_sold;
    private boolean show_total_sold;
    private int unit_remain;
    private boolean show_unit_remain;

    @OneToMany(mappedBy = "kit", cascade = CascadeType.ALL)
    private Collection<Image> images;

    public Kit() {
    }
}
