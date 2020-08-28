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

}
