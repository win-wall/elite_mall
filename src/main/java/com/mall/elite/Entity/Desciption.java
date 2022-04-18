package com.mall.elite.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
public class Desciption {
    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    private UUID id;
    private String layout;
    private boolean isContinuation;
    private String heading;
    private String copy;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToMany(mappedBy = "description", cascade = CascadeType.ALL)
    private Collection<Image> images;
    public Desciption() {
    }
}
