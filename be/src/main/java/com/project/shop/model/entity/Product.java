package com.project.shop.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Товар
 *
 * @author Алексей Климов
 */
@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String imageFileName;
    private String description;
    private Long price;
    private Long amount;
    private Long orderedTimes;
    private Float rating;
    @Enumerated(EnumType.STRING)
    private Category category;
    @OneToMany(mappedBy = "id.product", fetch = FetchType.LAZY)
    private List<Cart> cartPKS;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<OrderedItem> orderedItems;
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Feedback> feedback;
}