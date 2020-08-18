package com.training.library;


import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Book.FilterBookDto;
import com.training.library.dtos.Register.RegisterDto;
import com.training.library.dtos.Register.RegisterViewDto;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

import java.util.List;

@MessagingGateway(name="OrderGateway")
public interface OrderGateway {

    @Gateway(requestChannel = "inputChannel")
    List<RegisterViewDto> createOrder(FilterBookDto filterBookDto);
}
