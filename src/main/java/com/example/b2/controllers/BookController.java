package com.example.b2.controllers;

import com.example.b2.entities.Category;
import com.example.b2.repositories.CategoryRepository;
import com.example.b2.services.BookService;
import com.example.b2.entities.Book;
import com.example.b2.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/books")
public class BookController {
    @Autowired
    BookService bookService;
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public String listBooks(Model model,
                            @RequestParam(value = "page",required=false,defaultValue = "1") int page,
                            @RequestParam(value = "size",required=false,defaultValue = "5") int size
    ) {
        Pageable pageable =  PageRequest.of(page-1, size);
        Page<Book> books = bookService.findAll(pageable);

        model.addAttribute("books", books);
        int totalPages = books.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());


            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "book/list";
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {

        model.addAttribute("categories", categoryService.findAll());

        model.addAttribute("book", new Book());
        return "book/add";
    }

    //
    @PostMapping("/add")
    public String addBook(@Valid  @ModelAttribute("book") Book book, BindingResult result,Model model) {

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
//            model.addAttribute("book", new Book());
            return "book/add";
        }

        bookService.save(book);

        return "redirect:/books";
    }

    //
    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.findById(id);

        if (book != null) {
            model.addAttribute("categories", categoryService.findAll());

            model.addAttribute("book", book);
            return "book/edit";
        } else {
            return "not-found";

        }
    }

    @PostMapping("/edit")
    public String editBook(@Valid @ModelAttribute("book") Book updatedBook, BindingResult result,Model model) {
        System.out.println(result.hasErrors());
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
//            model.addAttribute("book", new Book());
            return "book/edit";
        }
            System.out.println(updatedBook);
            Book book = bookService.findById(updatedBook.getId());
            book.setTitle(updatedBook.getTitle());
            book.setCategory(updatedBook.getCategory());
            book.setPrice(updatedBook.getPrice());
            book.setAuthor(updatedBook.getAuthor());
            bookService.save(book);
            return "redirect:/books";


    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
      Book book=  bookService.findById(id);
        book.setDeleted(true);
        bookService.save(book);
        return "redirect:/books";
    }
}
