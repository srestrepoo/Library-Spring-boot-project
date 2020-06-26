package com.training.library.mappers;

import com.training.library.dtos.AuthorDto;
import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookViewDto;
import com.training.library.entities.Author;
import com.training.library.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    @Mapping(source = "language", target = "language")
    Book bookDtoToBook(BookDto bookDto);

    @Mapping(source = "author.id", target = "authorId")
    BookDto bookToBookDto(Book book);

    @Mapping(source = "author.name", target = "authorName")
    @Mapping(source = "language", target = "language")
    BookViewDto bookToBookViewDto(Book book);

}
