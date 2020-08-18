package com.training.library;

import com.training.library.dtos.Author.AuthorDto;
import com.training.library.dtos.Author.AuthorViewDto;
import com.training.library.dtos.Author.FilterAuthorDto;

import java.util.List;

public interface IAuthorService {

    List<AuthorViewDto> getAllAuthorsView(FilterAuthorDto filterAuthorDto);

    List<AuthorDto> getAllAuthors(FilterAuthorDto filterAuthorDto);

    AuthorDto createAuthor(AuthorDto newAuthorDto);

    AuthorDto updateAuthor(Integer authorId, AuthorDto authorUpdated);

    void deleteAuthor(Integer id);

}
