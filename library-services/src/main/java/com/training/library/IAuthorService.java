package com.training.library;

import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.AuthorViewDto;

import java.util.List;

public interface IAuthorService {

    List<AuthorViewDto> getAllAuthors(String name, String language, String nationality);

    AuthorDto createAuthor(AuthorDto newAuthorDto);

    AuthorDto updateAuthor(AuthorDto authorUpdated);

    void deleteAuthor(Integer id);

}
