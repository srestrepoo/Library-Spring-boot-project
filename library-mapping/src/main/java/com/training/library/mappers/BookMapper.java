package com.training.library.mappers;

import com.training.library.dtos.BookDto;
import com.training.library.dtos.BookViewDto;
import com.training.library.dtos.DetailBookViewDto;
import com.training.library.dtos.Details;
import com.training.library.entities.Author;
import com.training.library.entities.Book;
import com.training.library.entities.HistoryDetails;
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
    @Mapping(source = "newDetails", target = "details")
    BookDto bookToCreatedBookDto(Book book, Details newDetails);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "author", source = "newAuthor")
    void updateBookFromDto(BookDto bookDto, @MappingTarget Book book, Author newAuthor);

    @Mapping(source = "subcategory", target = "details.subcategory")
    @Mapping(source = "exercise", target = "details.exercise")
    @Mapping(source = "answer", target = "details.answer")
    BookViewDto detailBookViewToMathBookViewDto(DetailBookViewDto mathBook);

    @Mapping(source = "historicalPeriod", target = "details.historicalPeriod")
    @Mapping(source = "censure", target = "details.censure")
    @Mapping(source = "country", target = "details.country")
    BookViewDto detailBookViewToHistoryBookViewDto(DetailBookViewDto mathBook);

}
