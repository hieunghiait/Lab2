package com.example.b2.services;

import com.example.b2.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    Page<Book> findAll(Pageable pageable);

    void save(Book book);

    void deleteById(Long id);

    Book findById(Long id);
}
