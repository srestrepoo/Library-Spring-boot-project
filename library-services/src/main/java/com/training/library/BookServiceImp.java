package com.training.library;

import com.training.library.dtos.*;
import com.training.library.entities.*;
import com.training.library.enums.*;
import com.training.library.exceptions.CategoryConflictException;
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
    private BookMapper bookMapper;

    @Autowired
    private DetailsMapper detailsMapper;

    @Autowired
    private MathDetailsRepository mathDetailsRepository;

    @Autowired
    private HistoryDetailsRepository historyDetailsRepository;

    @Autowired
    private PhysicsDetailsRepository physicsDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<BookViewDto> getAllBooks(FilterBookDto filterBookDto) {

        List<List<BookViewDto>> categoriesList = Arrays.asList(
                getMathBooks(filterBookDto),
                getHistoryBooks(filterBookDto),
                getPhysicsBooks(filterBookDto)
        );

        return categoriesList.stream()
                .flatMap(bookList -> bookList.stream())
                .collect(Collectors.toList());
    }

    @Transactional
    public List<BookViewDto> getHistoryBooks(FilterBookDto filterBookDto) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DetailBookViewDto> query = builder.createQuery(DetailBookViewDto.class);

        Root<HistoryDetails> historyDetails = query.from(HistoryDetails.class);
        Join<HistoryDetails, Book> book = historyDetails.join(HistoryDetails_.book, JoinType.INNER);
        Join<Book, Author> author = book.join(Book_.author, JoinType.LEFT);

        query.select(
                builder.construct(DetailBookViewDto.class, book.get(Book_.id), author.get(Author_.name), book.get(Book_.title),
                        book.get(Book_.editorial), book.get(Book_.year), book.get(Book_.pages), book.get(Book_.language),
                        book.get(Book_.format), book.get(Book_.state), book.get(Book_.price), book.get(Book_.currency),
                        builder.nullLiteral(String.class), builder.nullLiteral(String.class), builder.nullLiteral(String.class),
                        historyDetails.get(HistoryDetails_.historicalPeriod), historyDetails.get(HistoryDetails_.censure),
                        historyDetails.get(HistoryDetails_.country))
        );

        query.where(addBookFilters(book, author, builder, filterBookDto));
        List<BookViewDto> resultList = entityManager.createQuery(query).getResultStream()
                .map(resultBook -> {
                    DetailsDto detailsViewDto = detailsMapper.detailBookToHistoryDetailsDto(resultBook);
                    return bookMapper.detailBookViewToBookViewDto(resultBook, detailsViewDto);
                })
                .collect(Collectors.toList());
        return resultList;
    }

    @Transactional
    public List<BookViewDto> getMathBooks(FilterBookDto filterBookDto) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DetailBookViewDto> query = builder.createQuery(DetailBookViewDto.class);

        Root<MathDetails> mathDetails = query.from(MathDetails.class);
        Join<MathDetails, Book> book = mathDetails.join(MathDetails_.book, JoinType.INNER);
        Join<Book, Author> author = book.join(Book_.author, JoinType.LEFT);

        query.select(
                builder.construct(DetailBookViewDto.class, book.get(Book_.id), author.get(Author_.name), book.get(Book_.title),
                        book.get(Book_.editorial), book.get(Book_.year), book.get(Book_.pages), book.get(Book_.language),
                        book.get(Book_.format), book.get(Book_.state), book.get(Book_.price), book.get(Book_.currency),
                        mathDetails.get(MathDetails_.subcategory), mathDetails.get(MathDetails_.exercise), mathDetails.get(MathDetails_.answer),
                        builder.nullLiteral(String.class), builder.nullLiteral(String.class), builder.nullLiteral(Nationality.class))
        );

        query.where(addBookFilters(book, author, builder, filterBookDto));
        List<BookViewDto> resultList = entityManager.createQuery(query).getResultStream()
                .map(resultBook -> {
                            DetailsDto detailsViewDto = detailsMapper.detailBookToMathDetailsDto(resultBook);
                            return bookMapper.detailBookViewToBookViewDto(resultBook, detailsViewDto);
                        }
                )
                .collect(Collectors.toList());
        return resultList;
    }

    @Transactional
    public List<BookViewDto> getPhysicsBooks(FilterBookDto filterBookDto) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DetailBookViewDto> query = builder.createQuery(DetailBookViewDto.class);

        Root<PhysicsDetails> physicsDetails = query.from(PhysicsDetails.class);
        Join<PhysicsDetails, Book> book = physicsDetails.join(PhysicsDetails_.book, JoinType.INNER);
        Join<Book, Author> author = book.join(Book_.author, JoinType.LEFT);

        query.select(
                builder.construct(DetailBookViewDto.class, book.get(Book_.id), author.get(Author_.name), book.get(Book_.title),
                        book.get(Book_.editorial), book.get(Book_.year), book.get(Book_.pages), book.get(Book_.language),
                        book.get(Book_.format), book.get(Book_.state), book.get(Book_.price), book.get(Book_.currency),
                        physicsDetails.get(PhysicsDetails_.subcategory), physicsDetails.get(PhysicsDetails_.exercise),
                        physicsDetails.get(PhysicsDetails_.answer),
                        builder.nullLiteral(String.class), builder.nullLiteral(String.class), builder.nullLiteral(Nationality.class))
        );

        query.where(addBookFilters(book, author, builder, filterBookDto));
        List<BookViewDto> resultList = entityManager.createQuery(query).getResultStream()
                .map(resultBook -> {
                    DetailsDto detailsViewDto = detailsMapper.detailBookToPhysicsDetailsDto(resultBook);
                    return bookMapper.detailBookViewToBookViewDto(resultBook, detailsViewDto);
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
        DetailsDto createdDetailsDto;

        if (HistoryDetailsDto.class.isAssignableFrom(newBook.getDetailsDto().getClass())) {
            HistoryDetails historyDetails = detailsMapper.dtoToHistoryDetails((HistoryDetailsDto) newBook.getDetailsDto(), createdBook);
            HistoryDetails createdDetails = historyDetailsRepository.save(historyDetails);
            createdDetailsDto = detailsMapper.historyDetailsToDto(createdDetails);
        } else if (MathDetailsDto.class.isAssignableFrom(newBook.getDetailsDto().getClass())) {
            MathDetails mathDetails = detailsMapper.dtoToMathDetails((MathDetailsDto) newBook.getDetailsDto(), createdBook);
            MathDetails createdDetails = mathDetailsRepository.save(mathDetails);
            createdDetailsDto = detailsMapper.mathDetailsToDto(createdDetails);
        } else if (PhysicsDetailsDto.class.isAssignableFrom(newBook.getDetailsDto().getClass())) {
            PhysicsDetails physicsDetails = detailsMapper.dtoToPhysicsDetails((PhysicsDetailsDto) newBook.getDetailsDto(), createdBook);
            PhysicsDetails createdDetails = physicsDetailsRepository.save(physicsDetails);
            createdDetailsDto = detailsMapper.physicsDetailsToDto(createdDetails);
        } else {
            throw new CategoryConflictException();
        }

        return bookMapper.bookToCreatedBookDto(createdBook, createdDetailsDto);
    }

    @Override
    @Transactional
    public BookDto updateBook(Integer bookId, BookDto bookDto) {
        Book bookToUpdate = bookRepository.findById(bookId).orElseThrow(EntityNotFound::new);
        Author author = authorRepository.findById(bookDto.getAuthorId()).orElseThrow(EntityNotFound::new);
        bookMapper.updateBookFromDto(bookToUpdate, bookDto, author);
        Book updatedBook = bookRepository.save(bookToUpdate);

        DetailsDto detailsDto = bookDto.getDetailsDto();
        DetailsDto updatedDetails;

        if (HistoryDetailsDto.class.isAssignableFrom(detailsDto.getClass())) {
            HistoryDetails historyDetails = historyDetailsRepository.findById(((HistoryDetailsDto) detailsDto).getId())
                    .orElseThrow(CategoryConflictException::new);
            detailsMapper.updateHistoryDetails(historyDetails, (HistoryDetailsDto) detailsDto, updatedBook);
            historyDetailsRepository.save(historyDetails);
            updatedDetails = detailsMapper.historyDetailsToDto(historyDetails);
        } else if (MathDetailsDto.class.isAssignableFrom(detailsDto.getClass())) {
            MathDetails mathDetails = mathDetailsRepository.findById(((MathDetailsDto) detailsDto).getId())
                    .orElseThrow(EntityNotFound::new);
            detailsMapper.updateMathDetails(mathDetails, (MathDetailsDto) detailsDto, updatedBook);
            mathDetailsRepository.save(mathDetails);
            updatedDetails = detailsMapper.mathDetailsToDto(mathDetails);
        } else if (PhysicsDetailsDto.class.isAssignableFrom(detailsDto.getClass())) {
            PhysicsDetails physicsDetails = physicsDetailsRepository.findById(((PhysicsDetailsDto) detailsDto).getId())
                    .orElseThrow(EntityNotFound::new);
            detailsMapper.updatePhysicsDetails(physicsDetails, (PhysicsDetailsDto) detailsDto, updatedBook);
            physicsDetailsRepository.save(physicsDetails);
            updatedDetails = detailsMapper.physicsDetailsToDto(physicsDetails);
        } else {
            throw new CategoryConflictException();
        }

        return bookMapper.bookToCreatedBookDto(updatedBook, updatedDetails);
    }

    @Override
    @Transactional
    public void deleteBook(Integer id) {
        if (bookRepository.findById(id).isPresent()) {
            physicsDetailsRepository.deleteById(id);
            historyDetailsRepository.deleteById(id);
            mathDetailsRepository.deleteById(id);
        } else {
            throw new EntityNotFound();
        }

    }

    private Predicate[] addBookFilters(Join bookJoin, Join authorJoin, CriteriaBuilder builder, FilterBookDto filterBookDto) {
        List<Predicate> filters = new ArrayList<>();
        if (!StringUtils.isEmpty(filterBookDto.getBookName())) {
            filters.add(builder.like(bookJoin.get("title"), "%" + filterBookDto.getBookName() + "%"));
        }

        if (filterBookDto.getLanguage() != null) {
            filters.add(builder.equal(bookJoin.get("language"), filterBookDto.getLanguage()));
        }

        if (!StringUtils.isEmpty(filterBookDto.getEditorial())) {
            filters.add(builder.equal(bookJoin.get("editorial"), filterBookDto.getEditorial()));
        }

        if (filterBookDto.getYear() != null) {
            filters.add(builder.equal(bookJoin.get("year"), filterBookDto.getYear()));
        }

        if (!StringUtils.isEmpty(filterBookDto.getFormat())) {
            filters.add(builder.equal(bookJoin.get("format"), filterBookDto.getFormat()));
        }

        if (!StringUtils.isEmpty(filterBookDto.getAuthorName())) {
            filters.add(builder.like(authorJoin.get("name"), "%" + filterBookDto.getAuthorName() + "%"));
        }

        if (filterBookDto.getState() != null) {
            filters.add(builder.equal(bookJoin.get("state"), filterBookDto.getState()));
        }

        return filters.toArray(new Predicate[]{});
    }
}
