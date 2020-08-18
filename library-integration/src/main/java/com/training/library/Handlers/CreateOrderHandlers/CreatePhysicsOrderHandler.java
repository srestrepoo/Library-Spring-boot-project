package com.training.library.Handlers.CreateOrderHandlers;

import com.training.library.IAuthorService;
import com.training.library.IBookService;
import com.training.library.dtos.Author.AuthorDto;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Author.FilterAuthorDto;
import com.training.library.dtos.Details.PhysicsDetailsDto;
import com.training.library.enums.LanguageEnum;
import com.training.library.enums.NationalityEnum;
import com.training.library.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CreatePhysicsOrderHandler implements ICreateOrderHandler {

    @Autowired
    private IBookService bookService;

    @Autowired
    private IAuthorService authorService;

    @Override
    public Class getBookDetailClass() {
        return PhysicsDetailsDto.class;
    }

    @Override
    public List<BookDto> handleOrder(BookDto bookDto) {
        BookDto newBookDto = bookDto.toBuilder().id(null).state(StateEnum.EXCELLENT).build();
        List<BookDto> bookDtoList = new ArrayList();
        bookDtoList.add(bookService.createBook(newBookDto));

        if (bookDto.getState().equals(StateEnum.ACCEPTABLE)) {

            Integer authorId = Optional.ofNullable(findAuthorByNationality(NationalityEnum.FRANCE))
                    .orElse(bookDto.getAuthorId());
            BookDto frenchBookDto = bookDto.toBuilder().id(null).authorId(authorId)
                    .language(LanguageEnum.FRENCH).state(StateEnum.EXCELLENT).build();

            bookDtoList.add(bookService.createBook(frenchBookDto));
        }
        return bookDtoList;
    }

    private Integer findAuthorByNationality(NationalityEnum nationality) {
        FilterAuthorDto filterAuthorDto = FilterAuthorDto.builder().nationality(nationality).maxResults(1).build();
        List<AuthorDto> authorByNationalityList = authorService.getAllAuthors(filterAuthorDto);
        return (authorByNationalityList.size() > 0)? authorByNationalityList.get(0).getId(): null;
    }

}
