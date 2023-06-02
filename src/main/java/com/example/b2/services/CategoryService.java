package com.example.b2.services;

import com.example.b2.entities.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
}
