package com.training.library.Handlers.CreateOrderHandlers;

import com.training.library.IBookOrderService;
import com.training.library.IRegisterService;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.BookOrder.BookOrderDto;
import com.training.library.dtos.Register.RegisterDto;
import com.training.library.dtos.Register.RegisterViewDto;
import com.training.library.entities.Register;
import com.training.library.mappers.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AddRegistersHandler implements GenericHandler<List<BookDto>> {

    @Autowired
    private RegisterMapper registerMapper;

    @Autowired
    private IBookOrderService bookOrderService;

    @Autowired
    private IRegisterService registerService;

    @Override
    public Object handle(List<BookDto> bookDtoList, MessageHeaders messageHeaders) {
        BookOrderDto bookOrderDto = bookOrderService.createBookOrder(new BookOrderDto());

        List<RegisterDto> registerDtoList = bookDtoList.stream()
                .map(bookDto ->
                        RegisterDto.builder().bookDto(bookDto).bookOrderDto(bookOrderDto).build())
                .collect(Collectors.toList());

        List<RegisterViewDto> registerViewDtoList = registerService.saveRegisters(registerDtoList);

        return registerViewDtoList;
    }
}
