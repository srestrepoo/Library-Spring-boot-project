package com.training.library.controllers;

import com.training.library.IBookService;
import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookViewDto;
import com.training.library.dtos.FilterBookDto;
import com.training.library.enums.Language;
import com.training.library.enums.State;
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
            @RequestParam(required = false) Language language,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) String format,
            @RequestParam(required = false) State state

    ) {

        FilterBookDto filterBookDto = FilterBookDto.builder()
                .bookName(bookName)
                .authorName(authorName)
                .language(language)
                .year(year)
                .editorial(editorial)
                .format(format)
                .state(state).build();

        return new ResponseEntity<>(bookService.getAllBooks(filterBookDto), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.createBook(bookDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Integer id, @RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.updateBook(id, bookDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity createAuthor(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
