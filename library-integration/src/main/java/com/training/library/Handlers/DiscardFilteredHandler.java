package com.training.library.Handlers;

import com.training.library.IBookService;
import com.training.library.dtos.Book.BookDto;
import com.training.library.entities.Book;
import com.training.library.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class DiscardFilteredHandler implements GenericHandler<BookDto> {

    @Override
    @Transactional
    public Message<List<BookDto>> handle(BookDto bookDtoList, MessageHeaders messageHeaders) {
        return MessageBuilder.withPayload(Collections.<BookDto>emptyList()).build();
    }
}
