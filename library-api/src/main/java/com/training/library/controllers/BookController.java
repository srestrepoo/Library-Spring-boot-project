package com.training.library.controllers;

import com.training.library.IBookService;
import com.training.library.OrderGateway;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Book.BookViewDto;
import com.training.library.dtos.Book.FilterBookDto;
import com.training.library.dtos.Register.RegisterDto;
import com.training.library.dtos.Register.RegisterViewDto;
import com.training.library.enums.LanguageEnum;
import com.training.library.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private OrderGateway orderGateway;

    @GetMapping("/integration")
    public ResponseEntity<List<RegisterViewDto>> integration(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) LanguageEnum language,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) String format,
            @RequestParam(required = false) StateEnum state

    ) {

        FilterBookDto filterBookDto = FilterBookDto.builder()
                .bookName(bookName)
                .authorName(authorName)
                .language(language)
                .year(year)
                .editorial(editorial)
                .format(format)
                .state(state).build();

        List<RegisterViewDto> registerViewDtoList = orderGateway.createOrder(filterBookDto);

        return new ResponseEntity<>(registerViewDtoList, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<BookViewDto>> getAll(
            @RequestParam(required = false) String bookName,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) LanguageEnum language,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String editorial,
            @RequestParam(required = false) String format,
            @RequestParam(required = false) StateEnum state

    ) {

        FilterBookDto filterBookDto = FilterBookDto.builder()
                .bookName(bookName)
                .authorName(authorName)
                .language(language)
                .year(year)
                .editorial(editorial)
                .format(format)
                .state(state).build();

        return new ResponseEntity<>(bookService.getAllBooksView(filterBookDto), HttpStatus.OK);
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
    public ResponseEntity deleteAuthor(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
