package com.training.library;

import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.AuthorViewDto;
import com.training.library.enums.LanguageEnum;
import com.training.library.enums.NationalityEnum;

import java.util.List;

public interface IAuthorService {

    List<AuthorViewDto> getAllAuthors(String name, LanguageEnum language, NationalityEnum nationality);

    AuthorDto createAuthor(AuthorDto newAuthorDto);

    AuthorDto updateAuthor(Integer authorId, AuthorDto authorUpdated);

    void deleteAuthor(Integer id);

}
