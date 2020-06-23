package com.training.library.controllers;

import com.training.library.IBookService;
import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookViewDto;
import com.training.library.dtos.FilterBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    IBookService bookService;

    @GetMapping()
    public ResponseEntity<List<BookViewDto>> getAll(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) String format
            ) {

        FilterBookDto filterBookDto = FilterBookDto.builder()
                .bookName(bookName)
                .authorName(authorName)
                .language(language)
                .year(year)
                .editorial(editorial)
                .format(format).build();

        return new ResponseEntity<>(bookService.getAllBooks(filterBookDto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.createBook(bookDto), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.updateBook(bookDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity createAuthor(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
