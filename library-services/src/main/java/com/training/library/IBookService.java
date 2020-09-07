package com.training.library;

import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Book.BookViewDto;
import com.training.library.dtos.Book.FilterBookDto;
import com.training.library.dtos.Details.DetailsDto;
import com.training.library.enums.StateEnum;

import java.util.List;

public interface IBookService {

    List<BookViewDto> getAllBooksView(FilterBookDto filterBookDto);

    List<BookDto> getAllBooks(FilterBookDto filterBookDto);

    List<BookDto> getBooksByAuthorId(Integer id);

    List<BookDto> getBooksByIsbnList(List<String> isbnList);

    BookDto createBook(BookDto newBook);

    List<BookDto> createBookCopies(Integer authorId, DetailsDto detailsDto, List<BookDto> newBooksDto);

    BookDto updateBook(Integer bookId, BookDto bookDto);

    List<BookDto> updateBooksWithoutDetails(Integer authorId, List<BookDto> booksToUpdateDto);

    void updateStateAndActiveById(Integer id, StateEnum state);

    void deleteBook(Integer id);

    void deleteObsoleteExternalBooks(List<String> isbnList);

}
