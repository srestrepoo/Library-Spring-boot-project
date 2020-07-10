package com.training.library;

import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookViewDto;
import com.training.library.dtos.FilterBookDto;
import com.training.library.entities.Author;
import com.training.library.entities.Book;
import com.training.library.exceptions.EntityNotFound;
import com.training.library.mappers.BookMapper;
import com.training.library.repositories.AuthorRepository;
import com.training.library.repositories.BookRepository;
import com.training.library.specifications.BookSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImp implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<BookViewDto> getAllBooks(FilterBookDto filterBookDto) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookViewDto> query = builder.createQuery(BookViewDto.class);
        Root<Book> book = query.from(Book.class);
        Join<Book, Author> bookJoinAuthor = book.join("author", JoinType.LEFT);
        query.multiselect(
                book.get("id"),
                bookJoinAuthor.get("name"),
                book.get("title"),
                book.get("editorial"),
                book.get("year"),
                book.get("pages"),
                book.get("language"),
                book.get("format"),
                book.get("state"),
                book.get("price"),
                book.get("currency")
        );
        query.where(addBookFilters(book, bookJoinAuthor, builder, filterBookDto));
        List<BookViewDto> resultList = entityManager.createQuery(query).getResultList();
        return resultList;
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
        Author author = authorRepository.findById(newBook.getAuthorId()).orElseThrow(EntityNotFound::new);
        Book createdBook = BookMapper.INSTANCE.bookDtoToBook(newBook, author);
        bookRepository.save(createdBook);
        return BookMapper.INSTANCE.bookToBookDto(createdBook);
    }

    @Override
    @Transactional
    public BookDto updateBook(Integer bookId, BookDto bookDto) {
        Book bookToUpdate = bookRepository.findById(bookId).orElseThrow(EntityNotFound::new);
        Author author = authorRepository.findById(bookDto.getAuthorId()).orElseThrow(EntityNotFound::new);
        BookMapper.INSTANCE.updateBookFromDto(bookDto, bookToUpdate, author);
        Book updatedBook = bookRepository.save(bookToUpdate);
        return BookMapper.INSTANCE.bookToBookDto(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(Integer id) {
        if (bookRepository.findById(id).isPresent()) {
            bookRepository.customDelete(id);
        } else {
            throw new EntityNotFound();
        }

    }

    private Predicate[] addBookFilters(Root root, Join join, CriteriaBuilder builder, FilterBookDto filterBookDto) {
        List<Predicate> filters = new ArrayList<>();
        if (!StringUtils.isEmpty(filterBookDto.getBookName())) {
            filters.add(builder.like(root.get("title"), "%" + filterBookDto.getBookName() + "%"));
        }

        if (filterBookDto.getLanguage() != null) {
            filters.add(builder.equal(root.get("language"), filterBookDto.getLanguage()));
        }

        if (!StringUtils.isEmpty(filterBookDto.getEditorial())) {
            filters.add(builder.equal(root.get("editorial"), filterBookDto.getEditorial()));
        }

        if (filterBookDto.getYear() != null) {
            filters.add(builder.equal(root.get("year"), filterBookDto.getYear()));
        }

        if (!StringUtils.isEmpty(filterBookDto.getFormat())) {
            filters.add(builder.equal(root.get("format"), filterBookDto.getFormat()));
        }

        if (!StringUtils.isEmpty(filterBookDto.getAuthorName())) {
            filters.add(builder.like(join.get("name"), "%" + filterBookDto.getAuthorName() + "%"));
        }

        if (filterBookDto.getState() != null) {
            filters.add(builder.equal(root.get("state"), filterBookDto.getState()));
        }

        return filters.toArray(new Predicate[]{});
    }
}
