package com.training.library;

import com.training.library.dtos.BookOrder.BookOrderDto;

public interface IBookOrderService {

    BookOrderDto createBookOrder(BookOrderDto bookOrderDto);

}
