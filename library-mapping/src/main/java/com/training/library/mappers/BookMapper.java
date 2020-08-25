package com.training.library.mappers;

import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Book.BookViewDto;
import com.training.library.dtos.Book.DetailBookViewDto;
import com.training.library.dtos.Details.DetailsDto;
import com.training.library.entities.Author;
import com.training.library.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "author", source = "newAuthor")
    @Mapping(target = "id", source = "bookDto.id")
    @Mapping(target = "active", source = "bookDto.active", defaultValue = "true")
    Book bookDtoToBook(BookDto bookDto, Author newAuthor);

    @Mapping(source = "author.id", target = "authorId")
    BookDto bookToBookDto(Book book);

    @Mapping(source = "book.author.id", target = "authorId")
    @Mapping(source = "newDetailsDto", target = "detailsDto")
    BookDto bookToCreatedBookDto(Book book, DetailsDto newDetailsDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", source = "newAuthor")
    void updateBookFromDto(@MappingTarget Book book, BookDto bookDto, Author newAuthor);

    @Mapping(source = "detailsDto", target = "details")
    BookViewDto detailBookViewToBookViewDto(DetailBookViewDto detailBookViewDto, DetailsDto detailsDto);

    @Mapping(source = "detailsDto", target = "detailsDto")
    BookDto detailBookViewToBookDto(DetailBookViewDto orderQueryDto, DetailsDto detailsDto);

}
