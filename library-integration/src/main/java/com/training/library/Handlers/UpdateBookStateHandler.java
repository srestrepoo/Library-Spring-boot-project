package com.training.library.Handlers;

import com.training.library.IBookService;
import com.training.library.dtos.Book.BookDto;
import com.training.library.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;


@Component
public class UpdateBookStateHandler implements GenericHandler<BookDto> {

    @Autowired
    IBookService bookService;

    @Override
    public Message<BookDto> handle(BookDto bookDto, MessageHeaders messageHeaders) throws MessagingException {

        bookService.updateStateAndActiveById(bookDto.getId(), this.getPreviousState(bookDto.getState()));
        return MessageBuilder.withPayload(bookDto).copyHeaders(messageHeaders).build();
    }

    private StateEnum getPreviousState(StateEnum actualState) {
        return StateEnum.getStateByValue(actualState.getValue() - 1);
    }


}
