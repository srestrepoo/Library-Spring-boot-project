package com.training.library;

import com.training.library.dtos.AuthorDto;
import com.training.library.entities.Author;
import com.training.library.mappers.AuthorMapper;
import com.training.library.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImp implements IAuthorService{

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public List<AuthorDto> getAllAuthors() {

        return authorRepository.findAll().stream()
                .map(author -> AuthorMapper.INSTANCE.authorToAuthorDto(author))
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
