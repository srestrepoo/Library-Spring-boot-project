package com.training.library.Handlers;

import com.training.library.IBookService;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Book.FilterBookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetBooksHandler implements GenericHandler<FilterBookDto> {

    @Autowired
    private IBookService bookService;

    @Override
    public Message<List<BookDto>> handle(FilterBookDto filterBookDto, MessageHeaders messageHeaders) {
        return MessageBuilder.withPayload(bookService.getAllBooks(filterBookDto)).build();
    }
}
