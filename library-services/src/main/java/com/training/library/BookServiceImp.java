package com.training.library;

import com.training.library.dtos.BookDto;
import com.training.library.entities.Book;
import com.training.library.mappers.BookMapper;
import com.training.library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImp implements IBookService{

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<BookDto> getAllBooks() {
        return null;
    }

    @Override
    public BookDto createBook(BookDto newBook) {
        Book createdBook = bookRepository.save(BookMapper.INSTANCE.bookDtoToBook(newBook));
        return BookMapper.INSTANCE.bookToBookDto(createdBook);
    }

    @Override
    public BookDto updateBook(BookDto book) {
        return null;
    }

    @Override
    public void deleteBook(Integer id) {

    }
}
