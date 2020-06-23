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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImp implements IAuthorService{

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    IBookService bookService;

    @Override
    public List<AuthorViewDto> getAllAuthors(String name, String nativeLanguage, String nationality) {
        Specification<Author> spec =
                Specification.where((name == null)? null : AuthorSpecification.containsName(name))
                        .and((StringUtils.isEmpty(nativeLanguage))? null : AuthorSpecification.containsProperty("nativeLanguage", nativeLanguage))
                        .and((StringUtils.isEmpty(nationality))? null : AuthorSpecification.containsProperty("nationality", nationality));

        return authorRepository.findAll(spec).stream()
                .map(this::getBooksNumberByAuthor)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto createAuthor(AuthorDto newAuthorDto) {
       Author newAuthor = authorRepository.save(AuthorMapper.INSTANCE.authorDtoToAuthor(newAuthorDto));
       return AuthorMapper.INSTANCE.authorToAuthorDto(newAuthor);
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorUpdated) {
            Author author = authorRepository.findById(authorUpdated.getId()).orElse(null);
            if(author != null){
                author.setName(authorUpdated.getName());
                author.setNationality(authorUpdated.getNationality());
                author.setNativeLanguage(authorUpdated.getNativeLanguage());
                Author updatedAuthor = authorRepository.save(author);
                return AuthorMapper.INSTANCE.authorToAuthorDto(updatedAuthor);
            } else {
                return null;
            }
    }

    @Override
    @Transactional
    public void deleteAuthor(Integer id) {
        authorRepository.customDelete(id);
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
