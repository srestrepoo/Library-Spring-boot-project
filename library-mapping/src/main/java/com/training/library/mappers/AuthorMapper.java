package com.training.library.mappers;

import com.training.library.dtos.Author.AuthorDto;
import com.training.library.dtos.Author.AuthorViewDto;
import com.training.library.entities.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author authorDtoToAuthor(AuthorDto authorDto);

    AuthorDto authorToAuthorDto(Author author);

    AuthorViewDto authorToAuthorViewDto(Author author);

    @Mapping(target = "id", ignore = true)
    void updateAuthorFromDto(AuthorDto authorDto, @MappingTarget Author author);

}
