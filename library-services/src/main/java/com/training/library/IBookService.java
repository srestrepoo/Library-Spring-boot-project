package com.training.library;

import com.training.library.dtos.BookDto;

import java.util.List;

public interface IBookService {

    List<BookDto> getAllBooks();

    BookDto createBook(BookDto newBook);

    BookDto updateBook(BookDto book);

    void deleteBook(Integer id);

}
