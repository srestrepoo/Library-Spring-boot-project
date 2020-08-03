package com.training.library;


import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Book.FilterBookDto;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.List;

@MessagingGateway(name="OrderGateway")
public interface OrderGateway {

    @Gateway(requestChannel = "inputChannel")
    List<BookDto> createOrder(FilterBookDto filterBookDto);
}
