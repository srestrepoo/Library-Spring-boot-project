package com.training.library.Handlers.CreateOrderHandlers;

import com.training.library.IBookService;
import com.training.library.dtos.Book.BookDto;
import com.training.library.dtos.Details.HistoryDetailsDto;
import com.training.library.enums.StateEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateHistoryOrderHandler implements ICreateOrderHandler {

    @Autowired
    private IBookService bookService;

    @Override
    public Class getBookDetailClass() {
        return HistoryDetailsDto.class;
    }

    @Override
    public List<BookDto> handleOrder(BookDto bookDto) {
        List<BookDto> bookDtoList = new ArrayList();
        bookDtoList.add(bookService.createBook(bookDto.toBuilder().id(null).state(StateEnum.EXCELLENT).build()));
        return bookDtoList;
    }

}
