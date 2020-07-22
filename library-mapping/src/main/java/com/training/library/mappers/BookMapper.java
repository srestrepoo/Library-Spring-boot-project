package com.training.library.mappers;

import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookViewDto;
import com.training.library.dtos.DetailBookViewDto;
import com.training.library.dtos.DetailsDto;
import com.training.library.entities.Author;
import com.training.library.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "author", source = "newAuthor")
    @Mapping(target = "id", source = "bookDto.id")
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

}
