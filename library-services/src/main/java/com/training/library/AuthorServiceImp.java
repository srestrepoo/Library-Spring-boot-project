package com.training.library;

import com.training.library.dtos.Author.AuthorDto;
import com.training.library.dtos.Author.AuthorViewDto;
import com.training.library.dtos.Author.FilterAuthorDto;
import com.training.library.dtos.Book.BookDto;
import com.training.library.entities.Author;
import com.training.library.exceptions.EntityNotFound;
import com.training.library.mappers.AuthorMapper;
import com.training.library.repositories.AuthorRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImp implements IAuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private IBookService bookService;

    @Autowired
    private AuthorMapper authorMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<AuthorViewDto> getAllAuthorsView(FilterAuthorDto filterAuthorDto) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> query = builder.createQuery(Author.class);
        Root<Author> author = query.from(Author.class);
        query.select(author);

        query.where(addAuthorFilters(author, builder, filterAuthorDto));

        return entityManager.createQuery(query).getResultList().stream()
                .map(this::getBooksNumberByAuthor)
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public List<AuthorDto> getAllAuthors(FilterAuthorDto filterAuthorDto) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Author> query = builder.createQuery(Author.class);
        Root<Author> author = query.from(Author.class);
        query.select(author);

        query.where(addAuthorFilters(author, builder, filterAuthorDto));

        return entityManager.createQuery(query)
                .setMaxResults(Optional.ofNullable(filterAuthorDto.getMaxResults()).orElse(20))
                .getResultStream()
                .map(authorMapper::authorToAuthorDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthorDto createAuthor(AuthorDto newAuthorDto) {
        Author newAuthor = authorRepository.save(authorMapper.authorDtoToAuthor(newAuthorDto));
        return authorMapper.authorToAuthorDto(newAuthor);
    }

    @Override
    @Transactional
    public AuthorDto updateAuthor(Integer AuthorId, AuthorDto authorDto) {
        Author authorToUpdate = authorRepository.findById(AuthorId).orElseThrow(EntityNotFound::new);
        authorMapper.updateAuthorFromDto(authorDto, authorToUpdate);
        Author updatedAuthor = authorRepository.save(authorToUpdate);
        return authorMapper.authorToAuthorDto(updatedAuthor);
    }

    @Override
    @Transactional
    public void deleteAuthor(Integer id) {
        if (authorRepository.findById(id).isPresent()) {
            authorRepository.customDelete(id);
        } else {
            throw new EntityNotFound();
        }
    }

    private AuthorViewDto getBooksNumberByAuthor(Author author) {
        AuthorViewDto authorViewDto = authorMapper.authorToAuthorViewDto(author);
        List<BookDto> books = bookService.getBooksByAuthorId(author.getId());
        Long nativeLanguageBooks = books.stream()
                .filter(book -> book.getLanguage().equals(authorViewDto.getNativeLanguage()))
                .count();
        authorViewDto.setBooksNumber(books.size());
        authorViewDto.setNativeLanguageBooks(Math.toIntExact(nativeLanguageBooks));
        authorViewDto.setForeignLanguageBooks(books.size() - Math.toIntExact(nativeLanguageBooks));
        return authorViewDto;
    }

    private Predicate[] addAuthorFilters(Root author, CriteriaBuilder builder, FilterAuthorDto filterAuthorDto) {
        List<Predicate> filters = new ArrayList<>();

        if (!StringUtils.isEmpty(filterAuthorDto.getName())) {
            filters.add(builder.like(author.get("name"), "%" + filterAuthorDto.getName() + "%"));
        }

        if (filterAuthorDto.getNativeLanguage() != null) {
            filters.add(builder.equal(author.get("nativeLanguage"), filterAuthorDto.getNativeLanguage()));
        }

        if (filterAuthorDto.getNationality() != null) {
            filters.add(builder.equal(author.get("nationality"), filterAuthorDto.getNationality()));
        }

        return filters.toArray(new Predicate[]{});
    }
}
