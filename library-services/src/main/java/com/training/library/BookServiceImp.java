package com.training.library;

import com.training.library.dtos.AuthorViewDto;
import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookViewDto;
import com.training.library.dtos.FilterBookDto;
import com.training.library.entities.Author;
import com.training.library.entities.Book;
import com.training.library.mappers.BookMapper;
import com.training.library.repositories.AuthorRepository;
import com.training.library.repositories.BookRepository;
import com.training.library.specifications.BookSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImp implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    @Transactional
    public List<BookViewDto> getAllBooks(FilterBookDto filterBookDto) {
        Specification<Book> spec =
                Specification.where((filterBookDto.getBookName() == null) ? null : BookSpecification.containsTitle(filterBookDto.getBookName()))
                        .and((StringUtils.isEmpty(filterBookDto.getLanguage())) ? null : BookSpecification.containsProperty("language", filterBookDto.getLanguage()))
                        .and((StringUtils.isEmpty(filterBookDto.getEditorial())) ? null : BookSpecification.containsProperty("editorial", filterBookDto.getEditorial()))
                        .and((filterBookDto.getYear() == null) ? null : BookSpecification.containsProperty("year", filterBookDto.getYear()))
                        .and((StringUtils.isEmpty(filterBookDto.getFormat())) ? null : BookSpecification.containsProperty("format", filterBookDto.getFormat()))
                        .and((StringUtils.isEmpty(filterBookDto.getAuthorName())) ? null : BookSpecification.containsAuthorName(filterBookDto.getAuthorName()));

        return bookRepository.findAll(spec).stream()
                .map(book -> BookMapper.INSTANCE.bookToBookViewDto(book))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<BookDto> getBooksByAuthorId(Integer id) {
        Specification<Book> bookSpec = Specification.where(BookSpecification.containsAuthorId(id));
        return bookRepository.findAll(bookSpec).stream()
                .map(book -> BookMapper.INSTANCE.bookToBookDto(book))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDto createBook(BookDto newBook) {
        try {
            Author author = authorRepository.findById(newBook.getAuthorId()).orElseThrow(NoSuchElementException::new);
            Book createdBook = BookMapper.INSTANCE.bookDtoToBook(newBook);
            createdBook.setAuthor(author);
            bookRepository.save(createdBook);
            return BookMapper.INSTANCE.bookToBookDto(createdBook);
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    @Transactional
    public BookDto updateBook(BookDto bookUpdated) {
        try {
            bookRepository.findById(bookUpdated.getId()).orElseThrow(NoSuchElementException::new);
            Author author = authorRepository.findById(bookUpdated.getAuthorId()).orElseThrow(NoSuchElementException::new);
            Book updatedBook = BookMapper.INSTANCE.bookDtoToBook(bookUpdated);
            updatedBook.setAuthor(author);
            bookRepository.save(updatedBook);
            return BookMapper.INSTANCE.bookToBookDto(updatedBook);
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    @Transactional
    public void deleteBook(Integer id) {
        try {
            bookRepository.findById(id).orElseThrow(NoSuchElementException::new);
            bookRepository.customDelete(id);
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }
}
