package com.training.library;

import com.training.library.dtos.AuthorViewDto;
import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookViewDto;
import com.training.library.dtos.FilterBookDto;
import com.training.library.entities.Book;
import com.training.library.mappers.BookMapper;
import com.training.library.repositories.BookRepository;
import com.training.library.specifications.BookSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImp implements IBookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<BookViewDto> getAllBooks(FilterBookDto filterBookDto) {
        Specification<Book> spec =
                Specification.where((filterBookDto.getBookName() == null)? null : BookSpecification.containsTitle(filterBookDto.getBookName()))
                        .and((StringUtils.isEmpty(filterBookDto.getLanguage()))? null : BookSpecification.containsProperty("language", filterBookDto.getLanguage()))
                        .and((StringUtils.isEmpty(filterBookDto.getEditorial()))? null : BookSpecification.containsProperty("editorial", filterBookDto.getEditorial()))
                        .and((filterBookDto.getYear() == null)? null : BookSpecification.containsProperty("year", filterBookDto.getYear()))
                        .and((StringUtils.isEmpty(filterBookDto.getFormat()))? null : BookSpecification.containsProperty("format", filterBookDto.getFormat()))
                        .and((StringUtils.isEmpty(filterBookDto.getAuthorName()))? null : BookSpecification.containsAuthorName(filterBookDto.getAuthorName()));


        return bookRepository.findAll(spec).stream()
                .map(book -> BookMapper.INSTANCE.bookToBookViewDto(book))
                .collect(Collectors.toList());
    }

    @Override
    public List<BookDto> getBooksByAuthorId(Integer id) {
        Specification<Book> bookSpec = Specification.where(BookSpecification.containsAuthorId(id));
        return bookRepository.findAll(bookSpec).stream()
                .map(book -> BookMapper.INSTANCE.bookToBookDto(book))
                .collect(Collectors.toList());
    }

    @Override
    public BookDto createBook(BookDto newBook) {
        Book createdBook = bookRepository.save(BookMapper.INSTANCE.bookDtoToBook(newBook));
        return BookMapper.INSTANCE.bookToBookDto(createdBook);
    }

    @Override
    public BookDto updateBook(BookDto bookUpdated) {
        Book bookToUpdate = bookRepository.findById(bookUpdated.getId()).orElse(null);
        if (bookToUpdate != null) {
            Book book = BookMapper.INSTANCE.bookDtoToBook(bookUpdated);
            Book updatedBook = bookRepository.save(book);
            return BookMapper.INSTANCE.bookToBookDto(updatedBook);
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void deleteBook(Integer id) {
        bookRepository.customDelete(id);
    }
}
