package com.training.library;

import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.AuthorViewDto;
import com.training.library.dtos.BookDto;
import com.training.library.entities.Author;
import com.training.library.mappers.AuthorMapper;
import com.training.library.repositories.AuthorRepository;
import com.training.library.specifications.AuthorSpecification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImp implements IAuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private IBookService bookService;

    @Override
    @Transactional
    public List<AuthorViewDto> getAllAuthors(String name, String nativeLanguage, String nationality) {
        Specification<Author> spec =
                Specification.where((name == null) ? null : AuthorSpecification.containsName(name))
                        .and((StringUtils.isEmpty(nativeLanguage)) ? null : AuthorSpecification.containsProperty("nativeLanguage", nativeLanguage))
                        .and((StringUtils.isEmpty(nationality)) ? null : AuthorSpecification.containsProperty("nationality", nationality));

        return authorRepository.findAll(spec).stream()
                .map(this::getBooksNumberByAuthor)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthorDto createAuthor(AuthorDto newAuthorDto) {
        Author newAuthor = authorRepository.save(AuthorMapper.INSTANCE.authorDtoToAuthor(newAuthorDto));
        return AuthorMapper.INSTANCE.authorToAuthorDto(newAuthor);
    }

    @Override
    @Transactional
    public AuthorDto updateAuthor(AuthorDto authorUpdated) {
        try {
            Author author = authorRepository.findById(authorUpdated.getId()).orElseThrow(NoSuchElementException::new);
            author.setName(authorUpdated.getName());
            author.setNationality(authorUpdated.getNationality());
            author.setNativeLanguage(authorUpdated.getNativeLanguage());
            Author updatedAuthor = authorRepository.save(author);
            return AuthorMapper.INSTANCE.authorToAuthorDto(updatedAuthor);
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
    public void deleteAuthor(Integer id) {
        try {
            authorRepository.findById(id).orElseThrow(NoSuchElementException::new);
            authorRepository.customDelete(id);
        } catch (NoSuchElementException exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND);
        } catch (Exception exception) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    private AuthorViewDto getBooksNumberByAuthor(Author author) {
        AuthorViewDto authorViewDto = AuthorMapper.INSTANCE.authorToAuthorViewDto(author);
        List<BookDto> books = bookService.getBooksByAuthorId(author.getId());
        Long nativeLanguageBooks = books.stream()
                .filter(book -> book.getLanguage().equals(authorViewDto.getNativeLanguage()))
                .count();
        authorViewDto.setBooksNumber(books.size());
        authorViewDto.setNativeLanguageBooks(Math.toIntExact(nativeLanguageBooks));
        authorViewDto.setForeignLanguageBooks(books.size() - Math.toIntExact(nativeLanguageBooks));
        return authorViewDto;
    }
}
