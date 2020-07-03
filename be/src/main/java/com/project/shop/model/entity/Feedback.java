package com.project.shop.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Сущность, представляющая отзыв к товару
 *
 * @author Алексей Климов
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User author;

    @ManyToOne
    private Product product;

    private String text;

    private int rating;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

}