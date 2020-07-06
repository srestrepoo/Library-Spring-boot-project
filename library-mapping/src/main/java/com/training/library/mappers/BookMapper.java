package com.training.library.mappers;

import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookViewDto;
import com.training.library.entities.Author;
import com.training.library.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    @Mapping(target = "author", source = "newAuthor")
    @Mapping(target = "id", source = "bookDto.id")
    Book bookDtoToBook(BookDto bookDto, Author newAuthor);

    @Mapping(source = "author.id", target = "authorId")
    BookDto bookToBookDto(Book book);

    @Mapping(source = "author.name", target = "authorName")
    BookViewDto bookToBookViewDto(Book book);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", source = "newAuthor")
    void updateBookFromDto(BookDto bookDto, @MappingTarget Book book, Author newAuthor);

}
