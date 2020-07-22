package com.training.library;

import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookViewDto;
import com.training.library.dtos.FilterBookDto;
import com.training.library.enums.BookCategoryEnum;

import java.util.List;

public interface IBookService {

    List<BookViewDto> getAllBooks(FilterBookDto filterBookDto);

    List<BookDto> getBooksByAuthorId(Integer id);

    BookDto createBook(BookDto newBook);

    BookDto updateBook(Integer bookId, BookDto bookDto);

    void deleteBook(Integer id, BookCategoryEnum bookCategory);

}
