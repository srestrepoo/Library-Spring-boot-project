package com.training.library;

import com.training.library.dtos.AuthorDto;
import com.training.library.entities.Author;

import java.util.List;

public interface IAuthorService {

    List<AuthorDto> getAllAuthors();

    AuthorDto createAuthor(AuthorDto newAuthorDto);

    AuthorDto updateAuthor(AuthorDto authorUpdated);

    void deleteAuthor(Integer id);

}
