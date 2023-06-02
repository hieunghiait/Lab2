package com.example.b2.entities;

import com.example.b2.validator.annotation.ValidCategoryId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Title must be not empty")
    @Size(max = 50,min=1,message = "Title must be less than 50 characters")
    private String title;
    private String author;
    @NotNull(message = "Price is required")
    private Double price;
    private boolean deleted=false;
    @ManyToOne
    @JoinColumn(name="category_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ValidCategoryId
    private Category category;

}
