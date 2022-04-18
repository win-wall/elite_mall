package com.mall.elite.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@Data
public class Image {
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "kit_id")
    private Kit kit;

    @ManyToOne
    @JoinColumn(name = "description_id")
    private Desciption description;

    private String URL;
    private String title;
    private String body;
    private double h;
    private double w;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date create_at;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date update_at;

    public Image() {
    }
}
