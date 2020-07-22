package com.training.library;

import com.training.library.dtos.*;
import com.training.library.entities.Author;
import com.training.library.entities.Book;
import com.training.library.entities.HistoryDetails;
import com.training.library.entities.MathDetails;
import com.training.library.enums.*;
import com.training.library.exceptions.EntityNotFound;
import com.training.library.mappers.BookMapper;
import com.training.library.mappers.DetailsMapper;
import com.training.library.repositories.AuthorRepository;
import com.training.library.repositories.BookRepository;
import com.training.library.repositories.HistoryDetailsRepository;
import com.training.library.repositories.MathDetailsRepository;
import com.training.library.specifications.BookSpecification;
import org.apache.commons.collections4.ListUtils;
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

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private DetailsMapper detailsMapper;

    @Autowired
    private MathDetailsRepository mathDetailsRepository;

    @Autowired
    private HistoryDetailsRepository historyDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<BookViewDto> getAllBooks(FilterBookDto filterBookDto) {
        List<BookViewDto> mathBooks = getBooksByCategory(filterBookDto, BookCategory.MATH);
        List<BookViewDto> historyBooks = getBooksByCategory(filterBookDto, BookCategory.HISTORY);
        return ListUtils.union(mathBooks, historyBooks);
    }

    @Transactional
    public List<BookViewDto> getBooksByCategory(FilterBookDto filterBookDto, BookCategory bookCategory) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DetailBookViewDto> query = builder.createQuery(DetailBookViewDto.class);
        Root<Book> book = query.from(Book.class);

        Join<Book, MathDetails> bookJoinDetails;
        Join<Book, Author> bookJoinAuthor = book.join("author", JoinType.LEFT);

        Path<Integer> id = book.get("id");
        Path<String> authorName = bookJoinAuthor.get("name");
        Path<String> title = book.get("title");
        Path<String> editorial = book.get("editorial");
        Path<Integer> year = book.get("year");
        Path<Integer> pages = book.get("pages");
        Path<Language> language = book.get("language");
        Path<String> format = book.get("format");
        Path<State> state = book.get("state");
        Path<Integer> price = book.get("price");
        Path<Currency> currency = book.get("currency");

        Expression<String> subcategory = builder.nullLiteral(String.class);
        Expression<String> exercise = builder.nullLiteral(String.class);
        Expression<String> answer = builder.nullLiteral(String.class);
        Expression<String> historicalPeriod = builder.nullLiteral(String.class);
        Expression<String> censure = builder.nullLiteral(String.class);
        Expression<Nationality> country = builder.nullLiteral(Nationality.class);

        if (bookCategory == BookCategory.MATH) {
            bookJoinDetails = book.join("mathDetails", JoinType.INNER);
            subcategory = bookJoinDetails.get("subcategory");
            answer = bookJoinDetails.get("answer");
            exercise = bookJoinDetails.get("exercise");
        } else if (bookCategory == BookCategory.HISTORY) {
            bookJoinDetails = book.join("historyDetails", JoinType.INNER);
            historicalPeriod = bookJoinDetails.get("historicalPeriod");
            censure = bookJoinDetails.get("censure");
            country = bookJoinDetails.get("country");
        }

        query.select(
                builder.construct(DetailBookViewDto.class, id, authorName, title, editorial, year, pages, language,
                        format, state, price, currency, subcategory, exercise, answer, historicalPeriod, censure, country)
        );

        query.where(addBookFilters(book, bookJoinAuthor, builder, filterBookDto));
        List<BookViewDto> resultList = entityManager.createQuery(query).getResultStream()
                .map(detailBookViewDto -> {
                    if (bookCategory == BookCategory.MATH) {
                        return bookMapper.detailBookViewToMathBookViewDto(detailBookViewDto);
                    } else {
                        return bookMapper.detailBookViewToHistoryBookViewDto(detailBookViewDto);
                    }
                })
                .collect(Collectors.toList());
        return resultList;
    }

    @Override
    @Transactional
    public List<BookDto> getBooksByAuthorId(Integer id) {
        Specification<Book> bookSpec = Specification.where(BookSpecification.containsAuthorId(id));
        return bookRepository.findAll(bookSpec).stream()
                .map(bookMapper::bookToBookDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDto createBook(BookDto newBook) {
        Author author = authorRepository.findById(newBook.getAuthorId()).orElseThrow(EntityNotFound::new);
        Book createdBook = bookRepository.save(bookMapper.bookDtoToBook(newBook, author));
        Details createdDetailsDto = null;

        if (newBook.getDetails().getClass() == HistoryDetailsDto.class) {
            HistoryDetails historyDetails = detailsMapper.dtoToHistoryDetails((HistoryDetailsDto) newBook.getDetails(), createdBook);
            HistoryDetails createdDetails = historyDetailsRepository.save(historyDetails);
            createdDetailsDto = detailsMapper.historyDetailsToDto(createdDetails);
        }
        else if (newBook.getDetails().getClass() == MathDetailsDto.class) {
            MathDetails mathDetails = detailsMapper.dtoToMathDetails((MathDetailsDto) newBook.getDetails(), createdBook);
            MathDetails createdDetails = mathDetailsRepository.save(mathDetails);
            createdDetailsDto = detailsMapper.mathDetailsToDto(createdDetails);
        }

        return bookMapper.bookToCreatedBookDto(createdBook, createdDetailsDto);
    }

    @Override
    @Transactional
    public BookDto updateBook(Integer bookId, BookDto bookDto) {
        Book bookToUpdate = bookRepository.findById(bookId).orElseThrow(EntityNotFound::new);
        Author author = authorRepository.findById(bookDto.getAuthorId()).orElseThrow(EntityNotFound::new);
        bookMapper.updateBookFromDto(bookDto, bookToUpdate, author);
        Book updatedBook = bookRepository.save(bookToUpdate);
        return bookMapper.bookToBookDto(updatedBook);
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
