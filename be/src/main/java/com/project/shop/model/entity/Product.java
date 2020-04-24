package com.project.shop.model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String pict;
    private String description;
    private Long price;
    @Enumerated(EnumType.STRING)
    private Category category;
}

