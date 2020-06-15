package com.training.library.mappers;

import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.BookDto;
import com.training.library.entities.Author;
import com.training.library.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    @Mapping(source = "author", target = "author")
    Book bookDtoToBook(BookDto bookDto);

    @Mapping(source = "author", target = "author")
    BookDto bookToBookDto(Book book);

    Author authorDtoToAuthor(AuthorDto authorDto);

    AuthorDto authorToAuthorDto(Author author);



}
