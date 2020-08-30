package com.training.library.mappers;

import com.training.library.dtos.Author.AuthorDto;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.ExternalLibrary.ExternalAuthorDto;
import com.training.library.dtos.ExternalLibrary.ExternalBookDto;
import com.training.library.enums.CurrencyEnum;
import com.training.library.enums.StateEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExternalLibraryMapper {


    @Mapping(target = "name", source = "externalAuthorDto.nombre")
    @Mapping(target = "nationality", source = "externalAuthorDto.pais")
    @Mapping(target = "nativeLanguage", source = "externalAuthorDto.idioma")
    AuthorDto externalAuthorDtoToAuthorDto(ExternalAuthorDto externalAuthorDto);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "title", source = "externalBookDto.titulo")
    @Mapping(target = "isbn", source = "externalBookDto.isbn")
    @Mapping(target = "year", source = "externalBookDto.ano")
    @Mapping(target = "pages", source = "externalBookDto.paginas")
    @Mapping(target = "price", source = "externalBookDto.valor")
    @Mapping(target = "currency", constant = "USD")
    @Mapping(target = "state", constant = "EXCELLENT")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "authorId", source = "authorDto.id")
    @Mapping(target = "language", source = "authorDto.nativeLanguage")
    @Mapping(target = "format", source = "format")
    @Mapping(target = "editorial", source = "format")
    BookDto externalBookDtoToBookDto(ExternalBookDto externalBookDto, AuthorDto authorDto, String format, String Editorial);
}
