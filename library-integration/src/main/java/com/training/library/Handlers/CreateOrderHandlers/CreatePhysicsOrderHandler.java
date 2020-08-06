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

@Component
public class    CreatePhysicsOrderHandler implements ICreateOrderHandler {

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

        if(bookDto.getState().equals(StateEnum.ACCEPTABLE)){
            BookDto frenchBookDto;
            Integer frenchAuthorId = findAuthorByNationality(NationalityEnum.FRANCE);
            if(frenchAuthorId != null){
                frenchBookDto = bookDto.toBuilder().id(null).authorId(frenchAuthorId)
                        .language(LanguageEnum.FRENCH).state(StateEnum.EXCELLENT).build();
            } else {
                frenchBookDto = bookDto.toBuilder().id(null).authorId(bookDto.getAuthorId())
                        .language(LanguageEnum.FRENCH).state(StateEnum.EXCELLENT).build();
            }
            bookDtoList.add(bookService.createBook(frenchBookDto));
        }
        return bookDtoList;
    }

    private Integer findAuthorByNationality(NationalityEnum nationality) {
        FilterAuthorDto filterAuthorDto = FilterAuthorDto.builder().nationality(nationality).build();
        List<AuthorDto> authorByNationalityList = authorService.getAllAuthors(filterAuthorDto);
        if(authorByNationalityList.size() > 0){
            return authorByNationalityList.get(0).getId();
        } else{
            return null;
        }
    }

}
