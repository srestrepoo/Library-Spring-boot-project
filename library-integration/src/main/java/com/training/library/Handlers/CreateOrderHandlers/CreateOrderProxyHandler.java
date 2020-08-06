package com.training.library.Handlers.CreateOrderHandlers;

import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Details.DetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CreateOrderProxyHandler implements GenericHandler<BookDto> {

    @Autowired
    private List<ICreateOrderHandler> listCreateOrderHandlers;

    private Map<Class, ICreateOrderHandler> mapCreateOrderHandler;

    private static ICreateOrderHandler apply(ICreateOrderHandler createOrder) {
        return createOrder;
    }

    @PostConstruct
    public void setupCreateOrderHandlers() {
       this.mapCreateOrderHandler = listCreateOrderHandlers.stream()
               .collect(Collectors.toMap(ICreateOrderHandler::getBookDetailClass, CreateOrderProxyHandler::apply));
    }

    @Override
    @Transactional
    public Message<List<BookDto>> handle(BookDto bookDto, MessageHeaders messageHeaders) {

        List<BookDto> bookDtoList = this.mapCreateOrderHandler.get(bookDto.getDetailsDto().getClass()).handleOrder(bookDto);
        return MessageBuilder.withPayload(bookDtoList).copyHeaders(messageHeaders).build();

    }
}
