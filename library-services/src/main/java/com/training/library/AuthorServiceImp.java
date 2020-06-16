package com.training.library;

import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.AuthorViewDto;
import com.training.library.entities.Author;
import com.training.library.mappers.AuthorMapper;
import com.training.library.repositories.AuthorRepository;
import com.training.library.specifications.AuthorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImp implements IAuthorService{

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public List<AuthorViewDto> getAllAuthors(String name, String language) {
        Specification<Author> spec =
                Specification.where((name == null)? null : new AuthorSpecification("name", name))
                    .and((language == null)? null : new AuthorSpecification("language", language));


        return authorRepository.findAll(spec).stream()
                .map(author -> AuthorMapper.INSTANCE.authorToAuthorViewDto(author))
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDto createAuthor(AuthorDto newAuthorDto) {
       Author newAuthor = authorRepository.save(AuthorMapper.INSTANCE.authorDtoToAuthor(newAuthorDto));
       return AuthorMapper.INSTANCE.authorToAuthorDto(newAuthor);
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorUpdated) {
        try {
            Author author = authorRepository.findById(authorUpdated.getId()).orElse(null);
            author.setName(authorUpdated.getName());
            author.setNationality(authorUpdated.getNationality());
            author.setNativeLanguage(authorUpdated.getNativeLanguage());
            Author updatedAuthor = authorRepository.save(author);
            return AuthorMapper.INSTANCE.authorToAuthorDto(updatedAuthor);
        } catch (Exception exception){
            return null;
        }
    }

    @Override
    public void deleteAuthor(Integer id) {
        authorRepository.deleteById(id);
    }
}
