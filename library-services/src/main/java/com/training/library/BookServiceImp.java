package com.training.library;

import com.training.library.DetailsService.DetailsServiceProxy;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Book.BookViewDto;
import com.training.library.dtos.Book.DetailBookViewDto;
import com.training.library.dtos.Book.FilterBookDto;
import com.training.library.dtos.Details.DetailsDto;
import com.training.library.entities.*;
import com.training.library.enums.*;
import com.training.library.exceptions.EntityNotFound;
import com.training.library.mappers.BookMapper;
import com.training.library.mappers.DetailsMapper;
import com.training.library.repositories.*;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImp implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RegisterRepository registerRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private DetailsMapper detailsMapper;

    @Autowired
    private MathDetailsRepository mathDetailsRepository;

    @Autowired
    private HistoryDetailsRepository historyDetailsRepository;

    @Autowired
    private PhysicsDetailsRepository physicsDetailsRepository;

    @Autowired
    private ExternalDetailsRepository externalDetailsRepository;

    @Autowired
    private DetailsServiceProxy detailsServiceProxy;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<BookViewDto> getAllBooksView(FilterBookDto filterBookDto) {

        List<BookViewDto> mathBookList = getMathBooks(filterBookDto).stream()
                .map(resultBook -> {
                    DetailsDto detailsViewDto = detailsMapper.detailBookToMathDetailsDto(resultBook);
                    return bookMapper.detailBookViewToBookViewDto(resultBook, detailsViewDto);
                })
                .collect(Collectors.toList());

        List<BookViewDto> historyBookList = getHistoryBooks(filterBookDto).stream()
                .map(resultBook -> {
                    DetailsDto detailsViewDto = detailsMapper.detailBookToHistoryDetailsDto(resultBook);
                    return bookMapper.detailBookViewToBookViewDto(resultBook, detailsViewDto);
                })
                .collect(Collectors.toList());

        List<BookViewDto> physicsBookList = getPhysicsBooks(filterBookDto).stream()
                .map(resultBook -> {
                    DetailsDto detailsViewDto = detailsMapper.detailBookToPhysicsDetailsDto(resultBook);
                    return bookMapper.detailBookViewToBookViewDto(resultBook, detailsViewDto);
                })
                .collect(Collectors.toList());

        List<List<BookViewDto>> categoriesList = Arrays.asList(
                mathBookList,
                historyBookList,
                physicsBookList
        );

        return categoriesList.stream()
                .flatMap(bookList -> bookList.stream())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<BookDto> getAllBooks(FilterBookDto filterBookDto) {

        List<BookDto> mathBookList = getMathBooks(filterBookDto).stream()
                .map(resultBook -> {
                    DetailsDto detailsViewDto = detailsMapper.detailBookToMathDetailsDto(resultBook);
                    return bookMapper.detailBookViewToBookDto(resultBook, detailsViewDto);
                })
                .collect(Collectors.toList());

        List<BookDto> historyBookList = getHistoryBooks(filterBookDto).stream()
                .map(resultBook -> {
                    DetailsDto detailsViewDto = detailsMapper.detailBookToHistoryDetailsDto(resultBook);
                    return bookMapper.detailBookViewToBookDto(resultBook, detailsViewDto);
                })
                .collect(Collectors.toList());

        List<BookDto> physicsBookList = getPhysicsBooks(filterBookDto).stream()
                .map(resultBook -> {
                    DetailsDto detailsViewDto = detailsMapper.detailBookToPhysicsDetailsDto(resultBook);
                    return bookMapper.detailBookViewToBookDto(resultBook, detailsViewDto);
                })
                .collect(Collectors.toList());

        List<List<BookDto>> categoriesList = Arrays.asList(
                mathBookList,
                historyBookList,
                physicsBookList
        );

        return categoriesList.stream()
                .flatMap(bookList -> bookList.stream())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DetailBookViewDto> getHistoryBooks(FilterBookDto filterBookDto) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DetailBookViewDto> query = builder.createQuery(DetailBookViewDto.class);

        Root<HistoryDetails> historyDetails = query.from(HistoryDetails.class);
        Join<HistoryDetails, Book> book = historyDetails.join(HistoryDetails_.book, JoinType.INNER);
        Join<Book, Author> author = book.join(Book_.author, JoinType.LEFT);

        query.select(
                builder.construct(DetailBookViewDto.class,
                        book.get(Book_.id), author.get(Author_.id), author.get(Author_.name), book.get(Book_.title), book.get(Book_.editorial),
                        book.get(Book_.year), book.get(Book_.pages), book.get(Book_.language), book.get(Book_.format),
                        book.get(Book_.isbn), book.get(Book_.state), book.get(Book_.price), book.get(Book_.currency),
                        builder.nullLiteral(String.class), builder.nullLiteral(String.class), builder.nullLiteral(String.class),
                        historyDetails.get(HistoryDetails_.historicalPeriod), historyDetails.get(HistoryDetails_.censure),
                        historyDetails.get(HistoryDetails_.country))
        );

        query.where(addBookFilters(book, author, builder, filterBookDto));
        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    public List<DetailBookViewDto> getMathBooks(FilterBookDto filterBookDto) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DetailBookViewDto> query = builder.createQuery(DetailBookViewDto.class);

        Root<MathDetails> mathDetails = query.from(MathDetails.class);
        Join<MathDetails, Book> book = mathDetails.join(MathDetails_.book, JoinType.INNER);
        Join<Book, Author> author = book.join(Book_.author, JoinType.LEFT);

        query.select(
                builder.construct(DetailBookViewDto.class,
                        book.get(Book_.id), author.get(Author_.id), author.get(Author_.name), book.get(Book_.title), book.get(Book_.editorial),
                        book.get(Book_.year), book.get(Book_.pages), book.get(Book_.language), book.get(Book_.format),
                        book.get(Book_.isbn), book.get(Book_.state), book.get(Book_.price), book.get(Book_.currency),
                        mathDetails.get(MathDetails_.subcategory), mathDetails.get(MathDetails_.exercise), mathDetails.get(MathDetails_.answer),
                        builder.nullLiteral(String.class), builder.nullLiteral(String.class), builder.nullLiteral(NationalityEnum.class))
        );

        query.where(addBookFilters(book, author, builder, filterBookDto));
        return entityManager.createQuery(query).getResultList();
    }

    @Transactional
    public List<DetailBookViewDto> getPhysicsBooks(FilterBookDto filterBookDto) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DetailBookViewDto> query = builder.createQuery(DetailBookViewDto.class);

        Root<PhysicsDetails> physicsDetails = query.from(PhysicsDetails.class);
        Join<PhysicsDetails, Book> book = physicsDetails.join(PhysicsDetails_.book, JoinType.INNER);
        Join<Book, Author> author = book.join(Book_.author, JoinType.LEFT);

        query.select(
                builder.construct(DetailBookViewDto.class,
                        book.get(Book_.id), author.get(Author_.id), author.get(Author_.name), book.get(Book_.title), book.get(Book_.editorial),
                        book.get(Book_.year), book.get(Book_.pages), book.get(Book_.language), book.get(Book_.format),
                        book.get(Book_.isbn), book.get(Book_.state), book.get(Book_.price), book.get(Book_.currency),
                        physicsDetails.get(PhysicsDetails_.subcategory), physicsDetails.get(PhysicsDetails_.exercise),
                        physicsDetails.get(PhysicsDetails_.answer),
                        builder.nullLiteral(String.class), builder.nullLiteral(String.class), builder.nullLiteral(NationalityEnum.class))
        );

        query.where(addBookFilters(book, author, builder, filterBookDto));
        return entityManager.createQuery(query).getResultList();
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
        DetailsDto createdDetailsDto = detailsServiceProxy.createDetails(newBook.getDetailsDto(), createdBook);

        return bookMapper.bookToCreatedBookDto(createdBook, createdDetailsDto);
    }

    @Override
    @Transactional
    public List<BookDto> createBookCopies(Integer authorId, DetailsDto detailsDto, List<BookDto> newBooksDto) {
        Author author = authorRepository.findById(authorId).orElseThrow(EntityNotFound::new);
        List<Book> createdBooks = bookRepository.saveAll(
                newBooksDto.stream()
                        .map(bookDto -> bookMapper.bookDtoToBook(bookDto, author))
                        .collect(Collectors.toList())
        );

        return createdBooks.stream()
                .map(book -> bookMapper.bookToCreatedBookDto(book,
                        detailsServiceProxy.createDetails(detailsDto, book))
                ).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDto updateBook(Integer bookId, BookDto bookDto) {
        Book bookToUpdate = bookRepository.findById(bookId).orElseThrow(EntityNotFound::new);
        Author author = authorRepository.findById(bookDto.getAuthorId()).orElseThrow(EntityNotFound::new);
        bookMapper.updateBookFromDto(bookToUpdate, bookDto, author);
        Book updatedBook = bookRepository.save(bookToUpdate);

        DetailsDto detailsDto = bookDto.getDetailsDto();
        DetailsDto updatedDetails = detailsServiceProxy.updateDetails(bookId, updatedBook, detailsDto);

        return bookMapper.bookToCreatedBookDto(updatedBook, updatedDetails);
    }

    @Override
    @Transactional
    public void updateStateAndActiveById(Integer id, StateEnum state) {
        bookRepository.updateStateAndActiveById(id, state, !state.equals(StateEnum.DISCARDED));
    }

    @Override
    @Transactional
    public void deleteBook(Integer id) {

        if (physicsDetailsRepository.findById(id).isPresent()) {
            registerRepository.deleteByBookId(id);
            physicsDetailsRepository.deleteById(id);
        } else if (historyDetailsRepository.findById(id).isPresent()) {
            registerRepository.deleteByBookId(id);
            historyDetailsRepository.deleteById(id);
        } else if (mathDetailsRepository.findById(id).isPresent()) {
            registerRepository.deleteByBookId(id);
            mathDetailsRepository.deleteById(id);
        } else if (externalDetailsRepository.findById(id).isPresent()) {
            registerRepository.deleteByBookId(id);
            externalDetailsRepository.deleteById(id);
        } else {
            throw new EntityNotFound();
        }
    }

    private Predicate[] addBookFilters(Join bookJoin, Join authorJoin, CriteriaBuilder builder, FilterBookDto filterBookDto) {
        List<Predicate> filters = new ArrayList<>();
        if (!StringUtils.isEmpty(filterBookDto.getBookName())) {
            filters.add(builder.like(bookJoin.get(Book_.title), "%" + filterBookDto.getBookName() + "%"));
        }

        if (filterBookDto.getLanguage() != null) {
            filters.add(builder.equal(bookJoin.get(Book_.language), filterBookDto.getLanguage()));
        }

        if (!StringUtils.isEmpty(filterBookDto.getEditorial())) {
            filters.add(builder.equal(bookJoin.get(Book_.editorial), filterBookDto.getEditorial()));
        }

        if (filterBookDto.getYear() != null) {
            filters.add(builder.equal(bookJoin.get(Book_.year), filterBookDto.getYear()));
        }

        if (!StringUtils.isEmpty(filterBookDto.getFormat())) {
            filters.add(builder.equal(bookJoin.get(Book_.format), filterBookDto.getFormat()));
        }

        if (!StringUtils.isEmpty(filterBookDto.getAuthorName())) {
            filters.add(builder.like(authorJoin.get(Author_.name), "%" + filterBookDto.getAuthorName() + "%"));
        }

        if (filterBookDto.getState() != null) {
            filters.add(builder.equal(bookJoin.get(Book_.state), filterBookDto.getState()));
        }

        filters.add(builder.equal(bookJoin.get(Book_.active), true));

        return filters.toArray(new Predicate[]{});
    }
}
