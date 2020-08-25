package com.training.library.Handlers.CreateOrderHandlers;

import com.training.library.dtos.Book.BookDto;

import java.util.List;

public interface ICreateOrderHandler {

    Class getBookDetailClass();

    List<BookDto> handleOrder(BookDto bookDto);
}
