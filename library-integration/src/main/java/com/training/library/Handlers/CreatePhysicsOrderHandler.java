package com.training.library.Handlers;

import com.training.library.IAuthorService;
import com.training.library.IBookService;
import com.training.library.dtos.Author.AuthorDto;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Author.FilterAuthorDto;
import com.training.library.enums.LanguageEnum;
import com.training.library.enums.NationalityEnum;
import com.training.library.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
public class CreatePhysicsOrderHandler implements GenericHandler<BookDto> {

    @Autowired
    private IBookService bookService;

    @Autowired
    private IAuthorService authorService;

    @Override
    @Transactional
    public Message<List<BookDto>> handle(BookDto bookDto, MessageHeaders messageHeaders) {
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
        return MessageBuilder.withPayload(bookDtoList).copyHeaders(messageHeaders).build();
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
